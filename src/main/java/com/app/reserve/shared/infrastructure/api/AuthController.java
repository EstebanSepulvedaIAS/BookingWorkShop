package com.app.reserve.shared.infrastructure.api;

import com.app.reserve.shared.infrastructure.security.CustomUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CustomUserDetailService userDetailService;

    public AuthController(CustomUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest authRequest){
        return this.userDetailService.loginUser(authRequest)
                .doOnSuccess(authResponse -> log.info("Login successful. User logged {}", authResponse.username()))
                .map(authResponse -> ResponseEntity.ok().body(authResponse));
    }
}
