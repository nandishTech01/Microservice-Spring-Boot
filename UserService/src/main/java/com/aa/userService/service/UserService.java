package com.aa.userservice.service;

import com.aa.userservice.dto.request.*;
import com.aa.userservice.dto.response.ResponseDto;
import jakarta.mail.MessagingException;

public interface UserService {

    ResponseDto register(LoginRequestDto dto) throws MessagingException;

    ResponseDto login(LoginRequestDto dto) throws MessagingException;

    ResponseDto verifyOtp(VerifyOtpRequestDto dto);

    ResponseDto refreshAccessToken(RefreshTokenRequestDto dto);

    ResponseDto logout(String email);

    ResponseDto changePassword(ChangePasswordRequestDto dto);

    ResponseDto forgotPassword(ForgotPasswordRequestDto dto);

    ResponseDto validateToken(String token);
}
