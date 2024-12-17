package com.aa.UserService.service;

import com.aa.UserService.dto.request.LoginRequestDto;
import com.aa.UserService.dto.request.VerifyOtpRequestDto;
import com.aa.UserService.dto.response.LoginResponseDto;
import com.aa.UserService.dto.response.ResponseDto;
import com.aa.UserService.entity.UserEntity;
import com.aa.UserService.repository.UserEntityRepository;
import com.aa.UserService.repository.UserOtpRepository;
import com.aa.UserService.utils.OtpGenerator;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.aa.UserService.utils.BaseService.success;

@Service
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userRepository;
    private final EmailService emailService;

    private final UserOtpRepository userOtpRepository;

    public UserServiceImpl(UserEntityRepository userRepository, EmailService emailService, UserOtpRepository userOtpRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.userOtpRepository = userOtpRepository;
    }

    @Override
    public ResponseDto login(LoginRequestDto dto) throws MessagingException {

        Optional<UserEntity> userEntityOptional = userRepository.findByEmailAndIsActiveTrue(dto.getEmail());
        if(userEntityOptional.isPresent()) {
            throw new RuntimeException("User Already Present.");
        }

        UserEntity user = UserEntity.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();

        //generate otp and send via Email
        String otp = OtpGenerator.generateOtp();
        sendVerificationEmail(dto.getEmail(),otp);

        LoginResponseDto loginResponeDto = LoginResponseDto.builder()
                .userId(user.getId())
                .isFirstLogin(true)
                .build();

        return success(loginResponeDto);
    }

    @Override
    public ResponseDto verifyOtp(VerifyOtpRequestDto dto) {
        return null;
    }

    @Override
    public ResponseDto refreshAccessToken(String token) {
        return null;
    }

    @Override
    public ResponseDto logout(String email) {
        return null;
    }

    private void sendVerificationEmail(String email,String otp) throws MessagingException {
        String subject = "Email verification";
        String body ="your verification otp is: "+otp;
        emailService.sendEmail(email,subject,body);
    }
}
