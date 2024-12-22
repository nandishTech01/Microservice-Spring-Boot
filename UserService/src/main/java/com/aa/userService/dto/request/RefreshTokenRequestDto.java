package com.aa.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenRequestDto {

    @NotBlank
    private String refreshToken;
}
