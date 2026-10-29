package com.app.reserve.user.infrastructure.repositories;

import com.app.reserve.role.domain.Role;
import com.app.reserve.role.infrastructure.entities.RoleMapper;
import com.app.reserve.role.infrastructure.repositories.JpaRoleRepository;
import com.app.reserve.shared.domain.Id;
import com.app.reserve.user.domain.User;
import com.app.reserve.user.domain.UserRepository;
import com.app.reserve.user.domain.exceptions.RoleNotFoundException;
import com.app.reserve.user.domain.exceptions.UserNotFoundException;
import com.app.reserve.user.infrastructure.entities.UserMapper;
import com.app.reserve.user.infrastructure.entities.UserRoleEntity;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class PostgresUserRepository implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final JpaUserRoleRepository jpaUserRoleRepository;
    private final JpaRoleRepository jpaRoleRepository;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public PostgresUserRepository(JpaUserRepository jpaUserRepository, JpaUserRoleRepository jpaUserRoleRepository, JpaRoleRepository jpaRoleRepository, R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.jpaUserRepository = jpaUserRepository;
        this.jpaUserRoleRepository = jpaUserRoleRepository;
        this.jpaRoleRepository = jpaRoleRepository;
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    @Override
    public Mono<Void> save(User user) {
        var usersEntity = UserMapper.toUserEntity(user);
        return r2dbcEntityTemplate.insert(usersEntity).then();
    }

    @Override
    public Mono<User> findById(Id id) {
        return jpaUserRepository.findById(id.value())
                .map(UserMapper::toDomainUser);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User with " + email + " not found")))
                .flatMap(userEntity -> {
                    var user = UserMapper.toDomainUser(userEntity);
                    return jpaUserRoleRepository.findAllByUserEntity(userEntity)
                            .flatMap(userRoleEntity -> jpaRoleRepository.findById(userRoleEntity.getRoleEntity())
                                    .switchIfEmpty(Mono.error(new RoleNotFoundException("Role: " + userRoleEntity.getRoleEntity().toString() + " no exists")))
                                    .doOnNext(roleEntity -> user.addRole(RoleMapper.toDomainRole(roleEntity)))
                            )
                            .then().thenReturn(user);
                });
    }

    @Override
    public Flux<User> findAll() {
        return jpaUserRepository.findAll()
                .map(UserMapper::toDomainUser);
    }

    @Override
    public Mono<Void> addRoleToUser(User user, Role role) {
        var userEntity = UserMapper.toUserEntity(user);
        var roleEntity = RoleMapper.toRoleEntity(role);
        var userRoleEntity = new UserRoleEntity(UUID.randomUUID(), userEntity.getId(), roleEntity.getId());
        return r2dbcEntityTemplate.insert(userRoleEntity).then();
    }
}
