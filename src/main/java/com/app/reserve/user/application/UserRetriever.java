package com.app.reserve.user.application;

import com.app.reserve.shared.domain.Id;
import com.app.reserve.user.domain.User;
import com.app.reserve.user.domain.UserRepository;
import com.app.reserve.user.domain.useCases.RetrieveUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public record UserRetriever(UserRepository userRepository) implements RetrieveUser {
    @Override
    public Mono<User> getUser(String userId) {
        return userRepository.findById(new Id(userId));
    }

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }
}
