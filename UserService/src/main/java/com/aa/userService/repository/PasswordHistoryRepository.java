package com.aa.userservice.repository;

import com.aa.userservice.entity.PasswordHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistoryEntity,Long> {
}
