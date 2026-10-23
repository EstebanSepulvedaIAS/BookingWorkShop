package com.app.reserve.user.domain.useCases;

import com.app.reserve.user.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RetrieveUser {
    Mono<User> getUser(String userId);
    Flux<User> getAllUsers();
}
