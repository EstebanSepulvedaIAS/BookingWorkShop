package com.app.reserve.user.infrastructure.api;

import com.app.reserve.user.domain.User;
import com.app.reserve.user.domain.useCases.CreateUser;
import com.app.reserve.user.domain.useCases.RetrieveUser;
import com.app.reserve.user.domain.useCases.UpdateUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CreateUser userCreator;
    private final RetrieveUser userRetriever;
    private final UpdateUser userUpdater;

    public UserController(CreateUser userCreator, RetrieveUser userRetriever, UpdateUser userUpdater) {
        this.userCreator = userCreator;
        this.userRetriever = userRetriever;
        this.userUpdater = userUpdater;
    }

    @PostMapping
    public Mono<ResponseEntity<Void>> createUser(@RequestBody UserInput userInput){
        return userCreator.createUser(User.create(userInput.id(), userInput.email(), userInput.password(), userInput.name()))
                .doOnSuccess(unused -> log.info("User created: {}", userInput))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> getUserById(@PathVariable("id") String id){
        return userRetriever.getUser(id)
                .map(user -> {
                    var userResponse = new UserResponse(
                            user.getId(),
                            user.getEmail(),
                            user.getPassword(),
                            user.getName()
                    );
                    log.info("User retrieved: {} {}", user.getName());
                    return ResponseEntity.ok().body(userResponse);
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<ResponseEntity<UserResponse>> getUsers(){
        return userRetriever.getAllUsers()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getName()
                ))
                .doOnComplete(() -> log.info("Users retrieved successfully"))
                .map(ResponseEntity::ok);
    }

    @PutMapping("/role")
    public Mono<ResponseEntity<Void>> addRoleToUser(@RequestBody UserRoleInput userRoleInput){
        return userUpdater.addRoleToUser(userRoleInput.userId(), userRoleInput.roleName())
                .doOnSuccess(unused -> log.info("Role {} added to user {}", userRoleInput.roleName(), userRoleInput.userId()))
                .thenReturn(ResponseEntity.ok().build());
    }
}
