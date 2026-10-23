package com.app.reserve.user.domain.useCases;

import com.app.reserve.user.domain.User;
import reactor.core.publisher.Mono;

public interface CreateUser {
    Mono<Void> createUser(User user);
}
