package com.app.reserve.user.application;

import com.app.reserve.role.domain.RoleRepository;
import com.app.reserve.shared.domain.Id;
import com.app.reserve.user.domain.UserRepository;
import com.app.reserve.user.domain.exceptions.RoleNotFoundException;
import com.app.reserve.user.domain.exceptions.UserNotFoundException;
import com.app.reserve.user.domain.useCases.UpdateUser;
import reactor.core.publisher.Mono;

public record UserUpdater(UserRepository userRepository, RoleRepository roleRepository) implements UpdateUser {
    @Override
    public Mono<Void> addRoleToUser(String userId, String roleName) {
        return userRepository.findById(new Id(userId))
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")))
                .flatMap(user -> roleRepository.findByName(roleName)
                        .switchIfEmpty(Mono.error(new RoleNotFoundException("Role not found")))
                        .flatMap(role -> {
                            user.addRole(role);
                            return userRepository.addRoleToUser(user, role);
                        })
                );
    }
}
