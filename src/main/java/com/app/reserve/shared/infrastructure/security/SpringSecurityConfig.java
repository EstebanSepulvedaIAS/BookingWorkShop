package com.app.reserve.shared.infrastructure.security;

import com.app.reserve.shared.infrastructure.utils.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {

    private final JwtUtils jwtUtils;

    public SpringSecurityConfig(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity){
        httpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeExchange(exchanges -> {

                    exchanges.pathMatchers(HttpMethod.GET, "/").permitAll();
                    exchanges.pathMatchers(HttpMethod.POST, "/auth/login").permitAll();

                    // Private endpoints

                    exchanges.pathMatchers(HttpMethod.GET, "/api/roles").authenticated();
                    exchanges.pathMatchers(HttpMethod.POST, "/api/roles").hasAnyRole("ADMIN", "GESTOR");

                    exchanges.pathMatchers(HttpMethod.GET, "/api/users").hasAnyRole("PARTICIPANTE", "ADMIN", "GESTOR");
                    exchanges.pathMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("PARTICIPANTE", "ADMIN", "GESTOR");
                    exchanges.pathMatchers(HttpMethod.POST, "/api/users").hasAnyRole("PARTICIPANTE", "ADMIN", "GESTOR");
                    exchanges.pathMatchers(HttpMethod.PUT, "/api/users/role").hasAnyRole("PARTICIPANTE", "ADMIN", "GESTOR");

                    exchanges.pathMatchers(HttpMethod.GET, "/api/offers").authenticated();
                    exchanges.pathMatchers(HttpMethod.POST, "/api/offers").hasAnyRole("ADMIN", "GESTOR");
                    exchanges.pathMatchers(HttpMethod.GET, "/api/offers/**").hasAnyRole("PARTICIPANTE", "ADMIN", "GESTOR");
                    exchanges.pathMatchers(HttpMethod.GET, "/api/offers/title/**").hasAnyRole("PARTICIPANTE", "ADMIN", "GESTOR");
                    exchanges.pathMatchers(HttpMethod.PUT, "/api/offers/update/**").hasAnyRole("ADMIN", "GESTOR");
                    exchanges.pathMatchers(HttpMethod.PUT, "/api/offers/update/**").hasAnyRole("PARTICIPANTE", "ADMIN", "GESTOR");
                    exchanges.pathMatchers(HttpMethod.DELETE, "/api/offers/delete/**").hasAnyRole("ADMIN", "GESTOR");

                    exchanges.pathMatchers(HttpMethod.GET, "/api/booking").authenticated();
                    exchanges.pathMatchers(HttpMethod.POST, "/api/booking").hasAnyRole("PARTICIPANTE", "ADMIN", "GESTOR");
                    exchanges.pathMatchers(HttpMethod.GET, "/api/booking/**").hasAnyRole("PARTICIPANTE", "ADMIN", "GESTOR");
                    exchanges.pathMatchers(HttpMethod.PUT, "/api/booking/cancel/**").hasAnyRole("PARTICIPANTE", "ADMIN", "GESTOR");
                    exchanges.pathMatchers(HttpMethod.DELETE, "/api/booking/**").hasAnyRole("ADMIN", "GESTOR");

                    exchanges.anyExchange().denyAll();
                })
                .addFilterBefore(new JwtValidatorFilter(jwtUtils), SecurityWebFiltersOrder.HTTP_BASIC);

        return httpSecurity.build();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(
            ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {

        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager =
                new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

