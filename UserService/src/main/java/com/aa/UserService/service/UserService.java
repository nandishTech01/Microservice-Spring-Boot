package com.aa.UserService.service;

import com.aa.UserService.dto.request.LoginRequestDto;
import com.aa.UserService.dto.request.VerifyOtpRequestDto;
import com.aa.UserService.dto.response.ResponseDto;
import jakarta.mail.MessagingException;

public interface UserService {

    ResponseDto login(LoginRequestDto dto) throws MessagingException;

    ResponseDto verifyOtp(VerifyOtpRequestDto dto);

    ResponseDto refreshAccessToken(String token);

    ResponseDto logout(String email);

}
