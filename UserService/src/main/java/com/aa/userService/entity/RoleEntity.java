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
@Table(name = "user_role")
public class RoleEntity extends BaseModifiedEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY , orphanRemoval = true , mappedBy = "role")
    private List<RoleMenuEntity> roleMenus;

}
