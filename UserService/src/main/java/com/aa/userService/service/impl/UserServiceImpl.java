package com.aa.userservice.service.impl;

import com.aa.userservice.Exception.NoDataFoundException;
import com.aa.userservice.Exception.UserNotFoundException;
import com.aa.userservice.Exception.ValidationException;
import com.aa.userservice.dto.request.*;
import com.aa.userservice.dto.response.LoginResponseDto;
import com.aa.userservice.dto.response.ResponseDto;
import com.aa.userservice.entity.ConfigurationEntity;
import com.aa.userservice.entity.OtpHistory;
import com.aa.userservice.entity.UserEntity;
import com.aa.userservice.entity.UserOtp;
import com.aa.userservice.enums.*;
import com.aa.userservice.repository.*;
import com.aa.userservice.service.EmailService;
import com.aa.userservice.service.UserService;
import com.aa.userservice.utils.JwtTokenUtil;
import com.aa.userservice.utils.OtpGenerator;
import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
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
        if (dto.getEmail() == null  || dto.getEmail().isBlank() || dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new ValidationException("Email and password must be provided.");
        }
        Optional<UserEntity> userEntityOptional = userRepository.findByEmailAndIsActiveTrue(dto.getEmail());
        if (userEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            if (UserStatus.IN_ACTIVE.equals(userEntity.getStatus())) {
                throw new ValidationException("The user is inactive. Please contact the system administrator for assistance.");
            }
            if (UserStatus.LOCKED.equals(userEntity.getStatus())) {
                LocalDateTime failedAttemptTime = userEntity.getFailedAttemptTime();
                long minutesElapsed = Duration.between(failedAttemptTime, LocalDateTime.now()).toMinutes();
                throw new ValidationException("The user account is locked. Please try again after "
                        + minutesElapsed + " minutes.");
            }
            throw new ValidationException("User with this email already exists.");
        }

        long passwordValidity = getConfigurationValue(ConfigurationName.PASSWORD_VALIDITY);

        UserEntity newUser = UserEntity.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .type(UserType.ADMIN)
                .status(UserStatus.ACTIVE)
                .lastChangeDate(LocalDateTime.now())
                .nextChangeDate(LocalDateTime.now().plusDays(passwordValidity))
                .failedAttempt(0L)
                .loginTime(LocalDateTime.now())
                .build();
        userRepository.save(newUser);

        LoginResponseDto loginResponeDto = LoginResponseDto.builder()
                .userId(newUser.getId())
                .isFirstLogin(true)
                .build();

        // Generate OTP and send via email
         generateAndSendOtp(newUser);

        return success("Signup successful. OTP sent to your email.",loginResponeDto);
    }

    @Override
    public ResponseDto login(LoginRequestDto dto) throws MessagingException {

        if (dto.getEmail() == null || dto.getEmail().isBlank() || dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new ValidationException("Email and password must be provided.");
        }

        Optional<UserEntity> userEntityOptional = userRepository.findByEmailAndIsActiveTrue(dto.getEmail());
        if (!userEntityOptional.isPresent()) {
            throw new UserNotFoundException("No User Found with this email.");
        }

        LoginResponseDto loginResponeDto = LoginResponseDto.builder()
                .userId(userEntityOptional.get().getId())
                .isFirstLogin(true).build();

        generateAndSendOtp(userEntityOptional.get());

        return success(loginResponeDto);
    }

    @Override
    public ResponseDto verifyOtp(VerifyOtpRequestDto dto) {
        UserEntity userEntity = userRepository.findByIdAndIsActiveTrue(dto.getUserId()).orElseThrow(() -> new UserNotFoundException("User Not Found."));

        UserOtp userOtpEntity = userOtpRepository.findByUserEntityAndIsActiveTrue(userEntity).orElseThrow(() -> new NoDataFoundException("User OTP Not Found."));

        long maxOtpFailures = getConfigurationValue(ConfigurationName.MAX_OTP_FAILURE_LIMIT);
        long otpValidityMinutes = getConfigurationValue(ConfigurationName.OTP_VALIDITY);
        long maxPasswordFailures = getConfigurationValue(ConfigurationName.MAX_PASSWORD_FAILURE_LIMIT);

        if (dto.getOtp().equals(userOtpEntity.getOtp())) {
            LocalDateTime otpExpiryTime = userOtpEntity.getOtpTime().plusMinutes(otpValidityMinutes);
            if (LocalDateTime.now().isAfter(otpExpiryTime)) {
                userOtpEntity.setActive(false);
                userOtpRepository.save(userOtpEntity);
                throw new ValidationException("OTP Expired. Please Generate a New OTP.");
            }
            recordOtpHistory(userOtpEntity.getOtp(), dto.getOtp(), Status.SUCCESS, userEntity);
            Map<String, String> tokens = generateTokens(userEntity);
            return success("OTP Verified Successfully", tokens);
            //  return ResponseDto.builder().status(Status.SUCCESS).message("OTP Verified Successfully").build();
        } else {
            recordOtpHistory(userOtpEntity.getOtp(), dto.getOtp(), Status.FAILURE, userEntity);
            if (userOtpEntity.getFailedAttempt() > maxOtpFailures) {
                userOtpEntity.setFailedAttempt(userOtpEntity.getFailedAttempt() + 1);
                userOtpRepository.save(userOtpEntity);
                throw new ValidationException("OTP Limit Exceeded.");
            } else {
                userOtpEntity.setActive(Boolean.FALSE);
                userOtpEntity.setFailedAttempt(userOtpEntity.getFailedAttempt() + 1);
                userOtpEntity.setFailedAttemptTime(LocalDateTime.now());
                userOtpRepository.save(userOtpEntity);

                userEntity.setStatus(UserStatus.LOCKED);
                userEntity.setFailedAttempt(maxPasswordFailures);
                userEntity.setFailedAttemptTime(LocalDateTime.now());
                userRepository.save(userEntity);
                throw new ValidationException("User Locked Due to Multiple Failed OTP Attempts.");
            }

        }
    }


    private Map<String, String> generateTokens(UserEntity userEntity) {
        Map<String, Object> claims = Map.of(
                "userId", userEntity.getId(),
                "email", userEntity.getEmail(),
                "roles", userEntity.getType().name()
        );

        String accessToken = JwtTokenUtil.generateAccessToken(userEntity.getEmail(), claims);
        String refreshToken = JwtTokenUtil.generateRefreshToken(userEntity.getEmail());

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    @Override
    public ResponseDto refreshAccessToken(RefreshTokenRequestDto dto) {
        if (!JwtTokenUtil.validateToken(dto.getRefreshToken())) {
            throw new ValidationException("Invalid or expired refresh token");
        }

        String email = JwtTokenUtil.extractEmail(dto.getRefreshToken());
        UserEntity userEntity = userRepository.findByEmailAndIsActiveTrue(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Map<String, Object> claims = Map.of(
                "userId", userEntity.getId(),
                "email", userEntity.getEmail(),
                "roles", userEntity.getType().name()
        );
        String newAccessToken = JwtTokenUtil.generateAccessToken(email, claims);
        return success("Access token refreshed successfully", Map.of("accessToken", newAccessToken));
    }

    @Override
    public ResponseDto logout(String email) {
        return null;
    }

    @Override
    public ResponseDto changePassword(ChangePasswordRequestDto dto) {
        UserEntity userEntity = this.userRepository.findByIdAndIsActiveTrue(dto.getUserId()).orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(dto.getCurrentPassword(), userEntity.getPassword())) {
            throw new ValidationException("Current password is incorrect");
        }
        if (passwordEncoder.matches(dto.getNewPassword(), userEntity.getPassword())) {
            throw new ValidationException("New password cannot be the same as the current password");
        }
        userEntity.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userEntity.setLastChangeDate(LocalDateTime.now());
        userRepository.save(userEntity);
        return success("Password changed successfully");
    }

    @Override
    public ResponseDto forgotPassword(ForgotPasswordRequestDto dto) {
        UserEntity userEntity = userRepository.findByEmailAndIsActiveTrue(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (UserStatus.IN_ACTIVE.equals(userEntity.getStatus())) {
            throw new ValidationException("The user is inactive. Please contact the system administrator for assistance.");
        }
        if (UserStatus.LOCKED.equals(userEntity.getStatus())) {
            LocalDateTime failedAttemptTime = userEntity.getFailedAttemptTime();
            long minutesElapsed = Duration.between(failedAttemptTime, LocalDateTime.now()).toMinutes();
            throw new ValidationException("The user account is locked. Please try again after "
                    + minutesElapsed + " minutes.");
        }

        //send Password
//        sendVerificationEmail(dto.getEmail(), otp);

        return success("OTP sent to the registered email");
    }

    public ResponseDto validateToken(String token) {
        if (JwtTokenUtil.validateToken(token)) {
            Claims claims = JwtTokenUtil.extractAllClaims(token);
            return success("Token is valid", claims);
        } else {
            throw new ValidationException("Invalid or expired token");
        }
    }


    private void sendVerificationEmail(String email, String otp) throws MessagingException {
        String subject = "Email verification";
        String body = "your verification otp is: " + otp;
        emailService.sendEmail(email, subject, body);
    }

    private long getConfigurationValue(ConfigurationName configName) {
        ConfigurationEntity config = configurationRepository.findByNameAndIsActiveTrue(configName);
        if (Objects.isNull(config.getValue())) {
            throw new NoDataFoundException("Configuration not found: " + configName);
        }
        return Long.parseLong(config.getValue());
    }

    private void recordOtpHistory(String actualOtp, String enteredOtp, Status status, UserEntity user) {
        OtpHistory otpHistory = OtpHistory.builder()
                .actualOtp(actualOtp).
                enteredOtp(enteredOtp)
                .otpEnteredTime(LocalDateTime.now())
                .status(status)
                .userEntity(user)
                .build();
        otpHistoryRepository.save(otpHistory);
    }

    public String generateAndSendOtp(UserEntity user) throws MessagingException {
        String otp = OtpGenerator.generateOtp();
        sendVerificationEmail(user.getEmail(), otp);

        UserOtp userOtp = UserOtp.builder()
                .otp(otp)
                .type(OtpType.SEND)
                .userEntity(user)
                .otpTime(LocalDateTime.now())
                .failedAttempt(0L)
                .build();
        userOtpRepository.save(userOtp);
        return otp;
    }


}
