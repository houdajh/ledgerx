package com.ledgerx.identity.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/me")
public class MeController {

    @GetMapping
    public Map<String, Object> me(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            throw new IllegalStateException("No authenticated user found");
        }

        // ✅ Extraction correcte des rôles Keycloak
        List<String> realmRoles = List.of();
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null && realmAccess.get("roles") instanceof List<?> roles) {
            realmRoles = roles.stream().map(Object::toString).toList();
        }

        List<String> clientRoles = List.of();
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess != null) {
            Object client = resourceAccess.get("ledgerx-api");
            if (client instanceof Map<?, ?> m && m.get("roles") instanceof List<?> roles) {
                clientRoles = roles.stream().map(Object::toString).toList();
            }
        }

        // ✅ Fusion propre des rôles
        List<String> allRoles = Stream.concat(realmRoles.stream(), clientRoles.stream())
                .distinct()
                .toList();

        return Map.of(
                "username", jwt.getClaim("preferred_username"),
                "email", jwt.getClaim("email"),
                "roles", allRoles
        );
    }
}
