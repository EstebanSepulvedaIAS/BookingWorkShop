package com.app.reserve.role.application;

import com.app.reserve.role.domain.Role;
import com.app.reserve.role.domain.RoleRepository;
import com.app.reserve.role.domain.useCases.CreateRole;
import com.app.reserve.role.infrastructure.entities.RoleEntity;
import reactor.core.publisher.Mono;

public record RoleCreator(RoleRepository roleRepository) implements CreateRole {

    @Override
    public Mono<Void> createRole(Role role) {
        return roleRepository.save(role);
    }
}
