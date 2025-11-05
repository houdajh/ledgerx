package com.ledgerx.credit.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomJwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        String errorMessage = authException.getMessage();

        if (errorMessage != null && errorMessage.contains("expired")) {
            response.getWriter().write("{\"message\": \"Token expired\"}");
        } else {
            response.getWriter().write("{\"message\": \"Invalid or expired token\"}");
        }
    }
}
