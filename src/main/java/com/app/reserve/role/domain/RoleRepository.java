package com.app.reserve.role.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    Mono<Void> save(Role role);
    Flux<Role> findAll();
    Mono<Role> findByName(String name);
}
