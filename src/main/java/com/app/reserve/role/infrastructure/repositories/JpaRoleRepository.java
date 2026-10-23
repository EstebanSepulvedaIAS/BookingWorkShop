package com.app.reserve.role.infrastructure.repositories;

import com.app.reserve.role.infrastructure.entities.RoleEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface JpaRoleRepository extends ReactiveCrudRepository<RoleEntity, UUID> {
    Mono<RoleEntity> findByName(String name);
}
