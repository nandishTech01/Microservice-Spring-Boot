package com.aa.userservice.entity;

import com.aa.userservice.entity.base.BaseCreatedEntity;
import com.aa.userservice.enums.OtpType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_otp")
public class UserOtp extends BaseCreatedEntity {

    @NotNull
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OtpType type;

    @NotNull
    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_time")
    private LocalDateTime otpTime;

    @Column(name = "failed_attempt")
    private Long failedAttempt;

    @Column(name = "failed_attempt_time")
    private LocalDateTime failedAttemptTime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;
}
