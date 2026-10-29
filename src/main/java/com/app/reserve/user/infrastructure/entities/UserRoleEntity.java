package com.app.reserve.user.infrastructure.entities;

import com.app.reserve.role.infrastructure.entities.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_role")
public class UserRoleEntity {

    @Id
    @Column("user_role_id")
    private UUID id;

    @Column("user_id")
    private UUID userEntity;

    @Column("role_id")
    private UUID roleEntity;
}
