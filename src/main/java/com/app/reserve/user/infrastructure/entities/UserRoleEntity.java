package com.app.reserve.user.infrastructure.entities;

import com.app.reserve.role.infrastructure.entities.RoleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"User_Role\"")
public class UserRoleEntity {

    @Id
    @Column(name = "user_role_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "role_id", updatable = false)
    private RoleEntity roleEntity;
}
