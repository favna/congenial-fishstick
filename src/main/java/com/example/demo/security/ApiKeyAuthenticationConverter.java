package com.example.demo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static reactor.core.publisher.Mono.justOrEmpty;

/**
 * Using the value in the configured header as credentials for authentication.
 */
@RequiredArgsConstructor
public class ApiKeyAuthenticationConverter implements ServerAuthenticationConverter {
    private final String apikeyHeader;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        var headers = exchange.getRequest().getHeaders();

        return justOrEmpty(headers.getFirst(apikeyHeader))
            .map(value -> new PreAuthenticatedAuthenticationToken(null, value));
    }
}
