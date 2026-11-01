package com.app.reserve.shared.infrastructure.security;

import com.app.reserve.shared.infrastructure.utils.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;

public class JwtValidatorFilter implements WebFilter {

    private final JwtUtils jwtUtils;

    public JwtValidatorFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            try {
                DecodedJWT decodedToken = jwtUtils.validateToken(token);
                String username = jwtUtils.extractUsername(decodedToken);
                String strAuthorities = jwtUtils.getClaim(decodedToken, "authorities").asString();

                Collection<? extends GrantedAuthority> authorities =
                        AuthorityUtils.commaSeparatedStringToAuthorityList(strAuthorities);

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

                return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
            } catch (SecurityException e) {
                return chain.filter(exchange);
            }
        }
        return chain.filter(exchange);
        }
}

