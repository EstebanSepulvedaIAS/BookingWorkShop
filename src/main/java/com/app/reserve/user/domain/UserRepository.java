package com.app.reserve.user.domain;

import com.app.reserve.role.domain.Role;
import com.app.reserve.shared.domain.Id;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<Void> save(User user);
    Mono<User> findById(Id id);
    Mono<User> findByEmail(String email);
    Flux<User> findAll();
    Mono<Void> addRoleToUser(User user, Role role);
}
