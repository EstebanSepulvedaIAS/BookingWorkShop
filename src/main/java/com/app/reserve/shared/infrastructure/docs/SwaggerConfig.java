package com.app.reserve.shared.infrastructure.docs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
        info = @Info(
                title = "BookingIAS",
                description = "Web system for a booked knowledge enterprise learn",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Dev server",
                        url = "http://localhost:8080"
                )
        },
        security = @SecurityRequirement(
                name = "Security token"
        )
)
@SecurityScheme(
        name = "Security token",
        description = "Access Token For my API",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}

