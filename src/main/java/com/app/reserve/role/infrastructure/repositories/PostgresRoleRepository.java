package com.app.reserve.role.infrastructure.repositories;

import com.app.reserve.role.domain.Role;
import com.app.reserve.role.domain.RoleRepository;
import com.app.reserve.role.infrastructure.entities.RoleMapper;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public class PostgresRoleRepository implements RoleRepository {

    private final JpaRoleRepository jpaRoleRepository;

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public PostgresRoleRepository(JpaRoleRepository jpaRoleRepository, R2dbcEntityTemplate r2dbcEntityTemplate){
        this.jpaRoleRepository = jpaRoleRepository;
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    @Override
    public Mono<Void> save(Role role) {
        var rollEntity = RoleMapper.toRoleEntity(role);
        return r2dbcEntityTemplate.insert(rollEntity).then();
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
