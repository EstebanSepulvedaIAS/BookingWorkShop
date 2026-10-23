package com.app.reserve.user.infrastructure.repositories;

import com.app.reserve.user.infrastructure.entities.UserEntity;
import com.app.reserve.user.infrastructure.entities.UserRoleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface JpaUserRoleRepository extends ReactiveCrudRepository<UserRoleEntity, UUID> {
    Flux<UserRoleEntity> findAllByUserEntity(UserEntity userEntity);
}
