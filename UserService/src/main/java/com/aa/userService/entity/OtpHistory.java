package com.aa.userservice.entity;

import com.aa.userservice.entity.base.BaseCreatedEntityWithoutIsActive;
import com.aa.userservice.entity.base.BaseEntity;
import com.aa.userservice.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "user_otp_history")
public class OtpHistory extends BaseCreatedEntityWithoutIsActive {


    @NotNull
    @Column(name = "actual_otp")
    private String actualOtp;

    @NotNull
    @Column(name = "entered_otp")
    private String enteredOtp;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "otp_entered_time")
    private LocalDateTime otpEnteredTime;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;
}
