package com.aa.UserService.entity;

import com.aa.UserService.entity.base.BaseCreatedEntity;
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
@Table(name = "user_password_history")
public class PasswordHistoryEntity extends BaseCreatedEntity {

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "pwd_chng_date")
    private LocalDateTime changeDate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;
}
