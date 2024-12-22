package com.aa.userservice.entity;

import com.aa.userservice.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.persistence.criteria.Fetch;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "role_menu_items")
public class RoleMenuItemEntity extends BaseEntity {

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_menu_id", nullable = false)
    private RoleMenuEntity roleMenu;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_id", nullable = false)
    private MenuEntity menu;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "action_id", nullable = false)
    private UserActionEntity userAction;

}
