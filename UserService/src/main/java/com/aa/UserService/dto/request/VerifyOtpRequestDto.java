package com.aa.UserService.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerifyOtpRequestDto {

    private String otp;

    private String userId;

}