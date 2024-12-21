package com.aa.userservice.enums;

import lombok.Getter;

@Getter
public enum ConfigurationName {

    OTP_VALIDITY,
    PASSWORD_VALIDITY,
    REPEAT_PASSWORD_COUNT,
    MAX_PASSWORD_FAILURE_LIMIT,
    MAX_OTP_FAILURE_LIMIT,
    REACTIVE_USER_AFTER_LOCK_STATUS,
    RESEND_OTP_TIME
}
