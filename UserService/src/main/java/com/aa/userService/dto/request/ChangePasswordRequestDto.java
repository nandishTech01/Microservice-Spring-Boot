package com.aa.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ChangePasswordRequestDto {

    @NotNull
    private long userId;

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String newPassword;
}
