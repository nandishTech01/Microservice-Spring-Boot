package com.aa.userservice.entity;

import com.aa.userservice.entity.base.BaseCreatedEntity;
import com.aa.userservice.enums.Action;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "user_action")
public class UserActionEntity extends BaseCreatedEntity {

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private Action action;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "userAction")
    private List<RoleMenuItemEntity> roleMenuItems;
}
