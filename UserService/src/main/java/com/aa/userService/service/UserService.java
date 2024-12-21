package com.aa.userservice.service;

import com.aa.userservice.dto.request.LoginRequestDto;
import com.aa.userservice.dto.request.VerifyOtpRequestDto;
import com.aa.userservice.dto.response.ResponseDto;
import jakarta.mail.MessagingException;

public interface UserService {

    ResponseDto login(LoginRequestDto dto) throws MessagingException;

    ResponseDto verifyOtp(VerifyOtpRequestDto dto);

    ResponseDto refreshAccessToken(String token);

    ResponseDto logout(String email);

}
