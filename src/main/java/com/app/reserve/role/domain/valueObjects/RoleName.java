package com.app.reserve.role.domain.valueObjects;

public record RoleName(String roleName) {
    public RoleName{
        if(roleName == null || roleName.isBlank()){
            throw new IllegalArgumentException("El nombre del rol no puede estar vacío");
        }
    }
}
