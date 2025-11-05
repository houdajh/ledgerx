package com.ledgerx.reporting.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceAccountTokenProvider {

    private final WebClient webClient = WebClient.builder().build();

    @Value("${keycloak.auth-server-url}")
    private String tokenUrl;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private String cachedToken;
    private long expiresAt = 0;

    public synchronized String getToken() {
        if (cachedToken != null && System.currentTimeMillis() < expiresAt) {
            return cachedToken;
        }

        log.info("Fetching new service token from Keycloak...");

        Map<String, Object> response = webClient.post()
                .uri(tokenUrl)
                .body(BodyInserters.fromFormData("grant_type", "client_credentials")
                        .with("client_id", clientId)
                        .with("client_secret", clientSecret))
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        cachedToken = (String) response.get("access_token");
        int expiresIn = (int) response.get("expires_in");
        expiresAt = System.currentTimeMillis() + (expiresIn - 60) * 1000L;

        log.info("✅ Token fetched (expires in {}s)", expiresIn);
        return cachedToken;
    }
}
