package com.ledgerx.credit.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ledgerxOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("LedgerX Credit Simulation API")
                        .description("API pour la gestion et la simulation de crédits")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("LedgerX Team")
                                .email("support@ledgerx.com")
                                .url("https://ledgerx.com")))
                // 🔒 Add Bearer Auth support
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
