package com.ledgerx.identity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableMethodSecurity // enables @PreAuthorize, @Secured, etc.
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(reg -> reg
                        .requestMatchers("/public/**", "/actuator/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(keycloakAuthorities()))
                );

        return http.build();
    }

    /** Configures how to turn a Jwt into GrantedAuthorities. */
    private JwtAuthenticationConverter keycloakAuthorities() {
        JwtAuthenticationConverter conv = new JwtAuthenticationConverter();
        conv.setJwtGrantedAuthoritiesConverter(this::extractAuthorities);
        return conv;
    }

    /**
     * Extracts authorities from Keycloak-style JWT:
     *  - realm roles in "realm_access.roles"
     *  - client roles in "resource_access.ledgerx-api.roles"
     */
    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        // ----- realm roles -----
        List<String> realmRoles = List.of();
        Map<String, List<String>> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null) {
            realmRoles = realmAccess.getOrDefault("roles", List.of());
        }

        // ----- client roles for client-id "ledgerx-api" -----
        List<String> clientRoles = List.of();
        Map<String, Object> ra = jwt.getClaim("resource_access");
        if (ra != null) {
            Object client = ra.get("ledgerx-api");
            if (client instanceof Map<?, ?> m) {
                Object rolesObj = m.get("roles");
                if (rolesObj instanceof List<?> l) {
                    clientRoles = l.stream().map(Object::toString).toList();
                }
            }
        }

        // Merge roles and convert to GrantedAuthority (exact type)
        return Stream.concat(realmRoles.stream(), clientRoles.stream())
                .distinct()
                .map(r -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + r))
                .collect(Collectors.toList()); // List<GrantedAuthority>
    }
}
