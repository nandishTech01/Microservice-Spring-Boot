package com.aa.userservice.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaseCreatedEntityWithoutIsActive extends BaseEntity {

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
//
//    @PrePersist
//    public void prePersist() {
//        this.createdBy = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
//                .map(Authentication::getPrincipal)
//                .filter(user -> !"anonymousUser".equals(user))
//                .map(Jwt.class::cast)
//                .map(jwt -> jwt.getClaim(TokenClaims.USER_ID.getValue()).toString())
//                .orElse("anonymousUser");
//        this.createdDate = LocalDateTime.now();
//    }

}
