package com.aa.userservice.entity;

import com.aa.userservice.entity.base.BaseCreatedEntity;
import com.aa.userservice.enums.UserStatus;
import com.aa.userservice.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "user_info")
public class UserEntity extends BaseCreatedEntity {

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @NotNull
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private UserType type;

    @Column(name = "login_time")
    private LocalDateTime loginTime;


    @Column(name = "failed_attempt")
    private Long failedAttempt;

    @Column(name = "failed_attempt_time")
    private LocalDateTime failedAttemptTime;

    @NotNull
    @Column(name = "last_pwd_change_date")
    private LocalDateTime lastChangeDate;

    @NotNull
    @Column(name = "next_pwd_change_date")
    private LocalDateTime nextChangeDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "userEntity")
    private List<UserOtp> userOtpEntities;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "userEntity")
    private List<PasswordHistoryEntity> passwordHistoryEntities;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "userEntity")
    private List<OtpHistory> otpHistoryEntities;
}
