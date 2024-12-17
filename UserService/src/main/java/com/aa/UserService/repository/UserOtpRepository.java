package com.aa.UserService.repository;

import com.aa.UserService.entity.UserEntity;
import com.aa.UserService.entity.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserOtpRepository extends JpaRepository<UserOtp, Long> {
    Optional<UserOtp> findByUserEntityAndIsActiveTrue(UserEntity userEntity);

    Optional<UserOtp> findByOtpAndIsActiveTrue(String otp);

    List<UserOtp> findAllByUserEntityAndIsActiveTrue(UserEntity userEntity);


}
