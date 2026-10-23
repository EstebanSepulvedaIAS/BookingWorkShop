package com.app.reserve.user.application;

import com.app.reserve.user.domain.User;
import com.app.reserve.user.domain.UserRepository;
import com.app.reserve.user.domain.useCases.CreateUser;
import reactor.core.publisher.Mono;

public record UserCreator(UserRepository userRepository) implements CreateUser {
    @Override
    public Mono<Void> createUser(User user) {
        return userRepository.save(user);
    }
}
