package com.app.reserve.user.domain.valueObjects;

import com.app.reserve.role.domain.Role;

import java.util.Set;

public record UserRoles(Set<Role> roles) {
    public void add(Role role){
        roles.add(role);
    }
}
