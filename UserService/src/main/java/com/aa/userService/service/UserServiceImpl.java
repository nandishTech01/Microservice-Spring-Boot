package com.aa.userservice.service;

import com.aa.userservice.dto.request.LoginRequestDto;
import com.aa.userservice.dto.request.VerifyOtpRequestDto;
import com.aa.userservice.dto.response.LoginResponseDto;
import com.aa.userservice.dto.response.ResponseDto;
import com.aa.userservice.entity.UserEntity;
import com.aa.userservice.repository.UserEntityRepository;
import com.aa.userservice.repository.UserOtpRepository;
import com.aa.userservice.utils.OtpGenerator;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.aa.userservice.utils.BaseService.success;


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
