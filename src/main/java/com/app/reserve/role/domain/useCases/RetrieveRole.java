package com.app.reserve.role.domain.useCases;

import com.app.reserve.role.domain.Role;
import reactor.core.publisher.Flux;

import java.util.List;

public interface RetrieveRole {
    Flux<Role> getAllRoles();
}
