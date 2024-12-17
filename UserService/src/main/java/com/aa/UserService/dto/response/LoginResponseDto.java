package com.aa.UserService.dto.response;

import com.aa.UserService.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDto {

    private Long userId;

    private Boolean isFirstLogin;

    private LoginResponseDto(Builder builder) {
        this.userId = builder.userId;
        this.isFirstLogin = builder.isFirstLogin;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long userId;
        private Boolean isFirstLogin;

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder isFirstLogin(Boolean isFirstLogin) {
            this.isFirstLogin = isFirstLogin;
            return this;
        }

        public LoginResponseDto build() {
            return new LoginResponseDto(this);
        }
    }

}
