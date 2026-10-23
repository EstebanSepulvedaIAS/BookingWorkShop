package com.app.reserve.role.infrastructure.repositories;

import com.app.reserve.role.domain.Role;
import com.app.reserve.role.domain.RoleRepository;
import com.app.reserve.role.infrastructure.entities.RoleMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public class PostgresRoleRepository implements RoleRepository {

    private final JpaRoleRepository jpaRoleRepository;

    public PostgresRoleRepository(JpaRoleRepository jpaRoleRepository){
        this.jpaRoleRepository = jpaRoleRepository;
    }

    @Override
    public Mono<Void> save(Role role) {
        var rollEntity = RoleMapper.toRoleEntity(role);
        return jpaRoleRepository.save(rollEntity).then();
    }

    @Override
    public Flux<Role> findAll() {
        return jpaRoleRepository.findAll()
                .map(RoleMapper::toDomainRole);
    }

    @Override
    public Mono<Role> findByName(String name) {
        return jpaRoleRepository.findByName(name)
                .map(RoleMapper::toDomainRole);
    }
}
