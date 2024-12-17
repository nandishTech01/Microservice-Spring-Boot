package com.aa.UserService.repository;

import com.aa.UserService.entity.UserEntity;
import com.aa.UserService.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository  extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByIdAndIsActiveTrue(Long id);

    Optional<UserEntity> findByEmailAndIsActiveTrue(String email);

    Optional<UserEntity> findByStatusAndIsActiveTrue(UserStatus status);

}
