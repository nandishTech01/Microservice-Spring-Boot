package com.aa.userservice.entity;

import com.aa.userservice.entity.base.BaseModifiedEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "role_menu")
public class RoleMenuEntity extends BaseModifiedEntity {

    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL , orphanRemoval = true , mappedBy = "roleMenu", fetch = FetchType.LAZY)
    private List<RoleMenuItemEntity> roleMenuItems;
}
