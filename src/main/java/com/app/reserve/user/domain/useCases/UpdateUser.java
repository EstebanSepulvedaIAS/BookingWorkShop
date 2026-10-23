package com.app.reserve.user.domain.useCases;

import reactor.core.publisher.Mono;

public interface UpdateUser {
    Mono<Void> addRoleToUser(String userId, String roleName);
}
