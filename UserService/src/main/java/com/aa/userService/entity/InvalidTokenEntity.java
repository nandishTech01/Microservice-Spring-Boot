package com.aa.userservice.entity;

import com.aa.userservice.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "invalid_token")
public class InvalidTokenEntity extends BaseEntity {

    @NotNull
    @Column(name = "token")
    private String token;

}
