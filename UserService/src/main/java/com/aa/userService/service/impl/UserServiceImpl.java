package com.aa.userservice.service.impl;

import com.aa.userservice.dto.request.LoginRequestDto;
import com.aa.userservice.dto.request.VerifyOtpRequestDto;
import com.aa.userservice.dto.response.LoginResponseDto;
import com.aa.userservice.dto.response.ResponseDto;
import com.aa.userservice.entity.ConfigurationEntity;
import com.aa.userservice.entity.OtpHistory;
import com.aa.userservice.entity.UserEntity;
import com.aa.userservice.entity.UserOtp;
import com.aa.userservice.enums.ConfigurationName;
import com.aa.userservice.enums.Status;
import com.aa.userservice.enums.UserStatus;
import com.aa.userservice.enums.UserType;
import com.aa.userservice.repository.*;
import com.aa.userservice.service.EmailService;
import com.aa.userservice.service.UserService;
import com.aa.userservice.utils.OtpGenerator;
import jakarta.mail.MessagingException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.aa.userservice.utils.BaseService.success;


@Service
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    private final UserOtpRepository userOtpRepository;

    private final OtpHistoryRepository otpHistoryRepository;

    private final PasswordHistoryRepository passwordHistoryRepository;

    private final ConfigurationRepository configurationRepository;

    public UserServiceImpl(UserEntityRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder, UserOtpRepository userOtpRepository, OtpHistoryRepository otpHistoryRepository, PasswordHistoryRepository passwordHistoryRepository, ConfigurationRepository configurationRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.userOtpRepository = userOtpRepository;
        this.otpHistoryRepository = otpHistoryRepository;
        this.passwordHistoryRepository = passwordHistoryRepository;
        this.configurationRepository = configurationRepository;
    }

    @Override
    public ResponseDto register(LoginRequestDto dto) throws MessagingException {
        // Validate input
        if (dto.getEmail() == null || dto.getEmail().isBlank() || dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new RuntimeException("Email and password must be provided.");
        }

        Optional<UserEntity> userEntityOptional = userRepository.findByEmailAndIsActiveTrue(dto.getEmail());
        if (userEntityOptional.isPresent()) {
            throw new RuntimeException("User with this email already exists.");
        }

        UserEntity newUser = UserEntity.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .type(UserType.ADMIN)
                .status(UserStatus.ACTIVE)
                .lastChangeDate(LocalDateTime.now())
                .nextChangeDate(LocalDateTime.now().plusDays(365))
                .build();
        userRepository.save(newUser);

        LoginResponseDto loginResponeDto = LoginResponseDto.builder()
                .userId(newUser.getId())
                .isFirstLogin(true)
                .build();

        // Generate OTP and send via email
        String otp = OtpGenerator.generateOtp();
        sendVerificationEmail(dto.getEmail(), otp);

        return success("Signup successful. OTP sent to your email.",loginResponeDto);
    }

    @Override
    public ResponseDto login(LoginRequestDto dto) throws MessagingException {

        if (dto.getEmail() == null || dto.getEmail().isBlank() || dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new RuntimeException("Email and password must be provided.");
        }

        Optional<UserEntity> userEntityOptional = userRepository.findByEmailAndIsActiveTrue(dto.getEmail());
        if (userEntityOptional.isPresent()) {
            throw new RuntimeException("User Already Present.");
        }

        UserEntity user = UserEntity.builder().email(dto.getEmail()).password(dto.getPassword()).build();

        //generate otp and send via Email
        String otp = OtpGenerator.generateOtp();
        sendVerificationEmail(dto.getEmail(), otp);

        LoginResponseDto loginResponeDto = LoginResponseDto.builder().userId(user.getId()).isFirstLogin(true).build();

        UserEntity.builder().email(dto.getEmail()).password(dto.getPassword()).loginTime(LocalDateTime.now()).build();

        return success(loginResponeDto);
    }

    @Override
    public ResponseDto verifyOtp(VerifyOtpRequestDto dto) {
        UserEntity userEntity = userRepository.findByIdAndIsActiveTrue(dto.getUserId()).orElseThrow(() -> new RuntimeException("User Not Found."));

        UserOtp userOtpEntity = userOtpRepository.findByUserEntityAndIsActiveTrue(userEntity).orElseThrow(() -> new RuntimeException("User OTP Not Found."));

        long maxOtpFailures = getConfigurationValue(ConfigurationName.MAX_OTP_FAILURE_LIMIT);
        long otpValidityMinutes = getConfigurationValue(ConfigurationName.OTP_VALIDITY);
        long maxPasswordFailures = getConfigurationValue(ConfigurationName.MAX_PASSWORD_FAILURE_LIMIT);

        if (dto.getOtp().equals(userOtpEntity.getOtp())) {
            LocalDateTime otpExpiryTime = userOtpEntity.getOtpTime().plusMinutes(otpValidityMinutes);
            if (LocalDateTime.now().isAfter(otpExpiryTime)) {
                throw new RuntimeException("OTP Expired. Please Generate a New OTP.");
            }
            recordOtpHistory(userOtpEntity.getOtp(), dto.getOtp(), Status.SUCCESS);
            return ResponseDto.builder().status(Status.SUCCESS).message("OTP Verified Successfully").build();
        } else {
            recordOtpHistory(userOtpEntity.getOtp(), dto.getOtp(), Status.FAILURE);
            if (userOtpEntity.getFailedAttempt() > maxOtpFailures) {
                userOtpEntity.setFailedAttempt(userOtpEntity.getFailedAttempt() + 1);
                userOtpRepository.save(userOtpEntity);
                throw new RuntimeException("OTP Limit Exceeded.");
            } else {
                userOtpEntity.setActive(Boolean.FALSE);
                userOtpEntity.setFailedAttempt(userOtpEntity.getFailedAttempt() + 1);
                userOtpEntity.setFailedAttemptTime(LocalDateTime.now());
                userOtpRepository.save(userOtpEntity);

                userEntity.setStatus(UserStatus.LOCKED);
                userEntity.setFailedAttempt(maxPasswordFailures);
                userEntity.setFailedAttemptTime(LocalDateTime.now());
                userRepository.save(userEntity);
                throw new RuntimeException("User Locked Due to Multiple Failed OTP Attempts.");
            }

        }
    }

    @Override
    public ResponseDto refreshAccessToken(String token) {
        return null;
    }

    @Override
    public ResponseDto logout(String email) {
        return null;
    }

    private void sendVerificationEmail(String email, String otp) throws MessagingException {
        String subject = "Email verification";
        String body = "your verification otp is: " + otp;
        emailService.sendEmail(email, subject, body);
    }

    private long getConfigurationValue(ConfigurationName configName) {
        ConfigurationEntity config = configurationRepository.findByNameAndIsActiveTrue(configName);
        if (Objects.isNull(config.getValue())) {
            throw new RuntimeException("Configuration not found: " + configName);
        }
        return Long.parseLong(config.getValue());
    }

    private void recordOtpHistory(String actualOtp, String enteredOtp, Status status) {
        OtpHistory otpHistory = OtpHistory.builder().actualOtp(actualOtp).enteredOtp(enteredOtp).otpEnteredTime(LocalDateTime.now()).status(status).build();
        otpHistoryRepository.save(otpHistory);
    }
}
