package com.aa.userservice.repository;

import com.aa.userservice.entity.OtpHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpHistoryRepository extends JpaRepository<OtpHistory,Long> {
}
