package com.example.demo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import reactor.core.publisher.Mono;

import java.util.Set;

import static reactor.core.publisher.Mono.just;

/**
 * Validates that the given {@link Authentication} contains the configured API key (prefixed by "Bearer ").
 */
@RequiredArgsConstructor
public class ApiKeyAuthenticationManager implements ReactiveAuthenticationManager {
    private final String apikeyValue;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return just(authentication)
            .map(this::validate);
    }

    private PreAuthenticatedAuthenticationToken validate(Authentication authentication) {
        if (!apikeyValue.equals(authentication.getCredentials())) {
            throw new BadCredentialsException("Invalid API key supplied.");
        }

        return new PreAuthenticatedAuthenticationToken(null, authentication.getCredentials(), Set.of());
    }
}
