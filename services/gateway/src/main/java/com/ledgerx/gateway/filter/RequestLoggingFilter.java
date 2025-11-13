package com.ledgerx.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);
    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String correlationId = headers.getFirst(CORRELATION_ID_HEADER);

        // Generate one if missing
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

// Make it effectively final for lambda
        final String cid = correlationId;

// Log incoming request
        log.info("→ [{}] {} {}", cid, exchange.getRequest().getMethod(), exchange.getRequest().getURI());

// Add header downstream
        var mutatedExchange = exchange.mutate()
                .request(r -> r.headers(h -> h.set(CORRELATION_ID_HEADER, cid)))
                .build();

        return chain.filter(mutatedExchange)
                .doOnSuccess(v -> log.info("← [{}] Completed successfully", cid))
                .doOnError(e -> log.error("← [{}] Error: {}", cid, e.getMessage()));

    }

    @Override
    public int getOrder() {
        return -1; // run early
    }
}
