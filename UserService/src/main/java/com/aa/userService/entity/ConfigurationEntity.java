package com.aa.userservice.entity;

import com.aa.userservice.entity.base.BaseCreatedEntity;
import com.aa.userservice.enums.ConfigurationName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "app_configuration")
public class ConfigurationEntity extends BaseCreatedEntity {

    @NotNull
    @Column(insertable=false, updatable=false, name = "name")
    @Enumerated(EnumType.STRING)
    private ConfigurationName name;

    @NotNull
    @Column(name = "name")
    private String value;


}
