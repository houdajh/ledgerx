package com.ledgerx.gateway.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class CustomJwtAuthEntryPoint implements ServerAuthenticationEntryPoint, ServerAccessDeniedHandler {

    private Mono<Void> writeResponse(ServerWebExchange exchange, HttpStatus status, String message) {
        var response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        var bytes = String.format("{\"status\": %d, \"message\": \"%s\"}", status.value(), message)
                .getBytes(StandardCharsets.UTF_8);
        var buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public Mono<Void> commence(ServerWebExchange exchange,
                               org.springframework.security.core.AuthenticationException e) {
        return writeResponse(exchange, HttpStatus.UNAUTHORIZED, "Authentication required");
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange,
                             org.springframework.security.access.AccessDeniedException e) {
        return writeResponse(exchange, HttpStatus.FORBIDDEN, "Access denied");
    }
}
