package com.example.demo.config;

import com.example.demo.security.ApiKeyAuthenticationConverter;
import com.example.demo.security.ApiKeyAuthenticationManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.security.config.web.server.SecurityWebFiltersOrder.AUTHENTICATION;

@Configuration
class SecurityConfig {
	private static final String[] SWAGGER_RESOURCES = {
			"/swagger-ui.html",
			"/v3/api-docs/**",
			"/v3/api-docs.yaml",
			"/webjars/swagger-ui/**"
	};

	private final HttpStatusServerEntryPoint authenticationEntryPoint = new HttpStatusServerEntryPoint(UNAUTHORIZED);

	@Bean
	SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity http,
	                                                    @Value("${security.authentication.header}") String apikeyHeader,
	                                                    @Value("${security.authentication.apikey}") String apikeyValue) {
		http.csrf(ServerHttpSecurity.CsrfSpec::disable);

		http.authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
				.pathMatchers(GET, SWAGGER_RESOURCES).permitAll()
				.anyExchange().authenticated()
		);

		apiKeyAuthentication(http, apikeyHeader, apikeyValue);

		// Don't show any basic auth popups:
		http.exceptionHandling(eh -> eh.authenticationEntryPoint(authenticationEntryPoint));
		http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);
		http.logout(ServerHttpSecurity.LogoutSpec::disable);

		return http.build();
	}

	private void apiKeyAuthentication(ServerHttpSecurity http, String apikeyHeader, String apikeyValue) {
		var authenticationManager = new ApiKeyAuthenticationManager(apikeyValue);
		var authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);

		var authenticationConverter = new ApiKeyAuthenticationConverter(apikeyHeader);
		authenticationWebFilter.setServerAuthenticationConverter(authenticationConverter);

		var authenticationFailureHandler = new ServerAuthenticationEntryPointFailureHandler(authenticationEntryPoint);
		authenticationWebFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

		http.addFilterAfter(authenticationWebFilter, AUTHENTICATION);
	}
}
