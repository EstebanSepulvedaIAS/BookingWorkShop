package com.app.reserve.shared.infrastructure.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@Slf4j
@RestControllerAdvice
public class CustomRestExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public Mono<ResponseEntity<String>> exception(RuntimeException ex){
        log.error(ex.getMessage(), ex);
        return Mono.just(ResponseEntity.badRequest().body(ex.getMessage()));
    }
}
