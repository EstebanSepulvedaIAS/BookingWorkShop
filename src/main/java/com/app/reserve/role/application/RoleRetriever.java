package com.app.reserve.role.application;

import com.app.reserve.role.domain.Role;
import com.app.reserve.role.domain.RoleRepository;
import com.app.reserve.role.domain.useCases.RetrieveRole;
import reactor.core.publisher.Flux;


public record RoleRetriever(RoleRepository roleRepository) implements RetrieveRole {

    @Override
    public Flux<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
