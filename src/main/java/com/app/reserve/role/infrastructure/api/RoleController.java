package com.app.reserve.role.infrastructure.api;

import com.app.reserve.role.domain.Role;
import com.app.reserve.role.domain.useCases.CreateRole;
import com.app.reserve.role.domain.useCases.RetrieveRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final CreateRole roleCreator;
    private final RetrieveRole roleRetriever;

    public RoleController(CreateRole roleCreator, RetrieveRole roleRetriever) {
        this.roleCreator = roleCreator;
        this.roleRetriever = roleRetriever;
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> createRole(@RequestBody RoleInput roleInput){
        return roleCreator.createRole(Role.create(roleInput.id(), roleInput.name()))
                .doOnSuccess(unused -> log.info("Role created: {}", roleInput))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
    }

    @GetMapping
    public Flux<ResponseEntity<RoleResponse>> getRoles(){
        return roleRetriever.getAllRoles()
                .map(role -> new RoleResponse(role.getId(), role.getName()))
                .doOnComplete(() -> log.info("Roles retrieved successfully"))
                .map(ResponseEntity::ok);
    }
}
