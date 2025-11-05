package com.ledgerx.reporting.config;

import com.ledgerx.reporting.security.ServiceAccountTokenProvider;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignSecurityConfig {

    private final ServiceAccountTokenProvider tokenProvider;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                String token = tokenProvider.getToken();
                template.header("Authorization", "Bearer " + token);
            }
        };
    }
}
