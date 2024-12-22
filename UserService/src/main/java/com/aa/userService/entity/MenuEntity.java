package com.aa.userservice.entity;

import com.aa.userservice.entity.base.BaseCreatedEntity;
import com.aa.userservice.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "app_menu")
public class MenuEntity extends BaseCreatedEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_menu_id")
    private String parentMenuId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY , orphanRemoval = true, mappedBy = "menu")
    private List<RoleMenuItemEntity> roleMenuItems;
}
