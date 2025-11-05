package com.ledgerx.credit.config;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class MdcTraceFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        try {
            MDC.put("traceId", UUID.randomUUID().toString());
            chain.doFilter(req, res);
        } finally {
            MDC.clear();
        }
    }
}
//Filter intercepte toutes les requêtes HTTP avant qu’elles atteignent tes contrôleurs.