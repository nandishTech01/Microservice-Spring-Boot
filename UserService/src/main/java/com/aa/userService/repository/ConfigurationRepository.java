package com.aa.userservice.repository;

import com.aa.userservice.entity.ConfigurationEntity;
import com.aa.userservice.enums.ConfigurationName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, Long> {

    ConfigurationEntity findByNameAndIsActiveTrue(ConfigurationName name);

}
