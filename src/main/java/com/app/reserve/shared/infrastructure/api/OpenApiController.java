package com.app.reserve.shared.infrastructure.api;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.net.URI;

@Controller
public class OpenApiController {

    @GetMapping("/")
    @Parameter(hidden = true)
    public Mono<Void> index(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FOUND);
        response.getHeaders().setLocation(URI.create("/swagger-ui,html"));
        return response.setComplete();
    }
}
