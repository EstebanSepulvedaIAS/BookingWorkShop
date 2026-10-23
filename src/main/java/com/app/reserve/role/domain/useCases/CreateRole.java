package com.app.reserve.role.domain.useCases;

import com.app.reserve.role.domain.Role;
import reactor.core.publisher.Mono;

public interface CreateRole {
    Mono<Void> createRole(Role role);
}
