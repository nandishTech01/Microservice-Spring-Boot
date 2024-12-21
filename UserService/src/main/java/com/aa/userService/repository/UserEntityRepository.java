package com.aa.userservice.repository;

import com.aa.userservice.entity.UserEntity;
import com.aa.userservice.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository  extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByIdAndIsActiveTrue(Long id);

    Optional<UserEntity> findByEmailAndIsActiveTrue(String email);

    Optional<UserEntity> findByStatusAndIsActiveTrue(UserStatus status);

}
