package com.app.reserve.role.domain;

import com.app.reserve.role.domain.valueObjects.RoleName;
import com.app.reserve.shared.domain.Id;

import java.util.UUID;

public class Role {

    private final Id id;
    private final RoleName roleName;

    public Role(String id, String name) {
        this.id = new Id(id);
        this.roleName = new RoleName(name);
    }

    public static Role create(String id, String name) {
        return new Role(id, name);
    }

    public UUID getId() {
        return id.value();
    }

    public String getName() {
        return roleName.roleName();
    }

    @Override
    public String toString() {
        return "{roleName=" + getName() + '}';
    }
}
