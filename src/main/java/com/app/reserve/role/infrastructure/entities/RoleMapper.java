package com.app.reserve.role.infrastructure.entities;

import com.app.reserve.role.domain.Role;

public class RoleMapper {

    private RoleMapper() {}

    public static RoleEntity toRoleEntity(Role role) {
        return new RoleEntity(
                role.getId(),
                role.getName()
        );
    }

    public static Role toDomainRole(RoleEntity roleEntity) {
        return new Role(
                roleEntity.getId().toString(),
                roleEntity.getName()
        );
    }
}
