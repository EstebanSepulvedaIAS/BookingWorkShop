package com.app.reserve.shared.infrastructure.security;

import com.app.reserve.shared.infrastructure.api.AuthRequest;
import com.app.reserve.shared.infrastructure.api.AuthResponse;
import com.app.reserve.shared.infrastructure.utils.JwtUtils;
import com.app.reserve.user.domain.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailService implements ReactiveUserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public CustomUserDetailService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .switchIfEmpty(Mono.error(() -> new UsernameNotFoundException("The user with the email " + username + " doesn't exist")))
                .map(user -> {
                    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
                    user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
                    return new CustomUserDetails(user.getEmail(), user.getPassword(), authorities);
                })
                .cast(UserDetails.class);
    }

    public Mono<AuthResponse> loginUser(AuthRequest authRequest) {
        var username = authRequest.username();
        var password = authRequest.password();

        return this.authenticate(username, password)
                .map(authentication -> {
                    var token = jwtUtils.generateToken(authentication);
                    List<String> roles = Collections.singletonList(
                            jwtUtils.getClaim(jwtUtils.validateToken(token), "authorities").asString()
                    );
                    return new AuthResponse(username, "User logged in successfully", token, roles);
                });
    }

    private Mono<Authentication> authenticate(String username, String password) {
        return this.findByUsername(username)
                .onErrorMap(UsernameNotFoundException.class, e -> new BadCredentialsException("Invalid username or password."))
                .flatMap(userDetails -> {
                    if(!passwordEncoder.matches(password, userDetails.getPassword())){
                        return Mono.error(new BadCredentialsException("Invalid password"));
                    }
                    return Mono.just(new UsernamePasswordAuthenticationToken(
                            username,
                            userDetails.getPassword(),
                            userDetails.getAuthorities()
                    ));
                });
    }

}
