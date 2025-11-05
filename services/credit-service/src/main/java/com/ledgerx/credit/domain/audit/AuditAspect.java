package com.ledgerx.credit.domain.audit;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class AuditAspect {

    @Around("@annotation(audit)")
    public Object auditAction(ProceedingJoinPoint joinPoint, Audit audit) throws Throwable {
        long start = System.currentTimeMillis();
        String actor = SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : "anonymous";

        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;
            log.info("{{\"action\":\"{}\",\"actor\":\"{}\",\"resource\":\"{}\",\"status\":\"SUCCESS\",\"durationMs\":{}}}",
                    audit.action(), actor, audit.resource(), duration);
            String traceId = MDC.get("traceId");

            log.info(
                    "{{\"traceId\":\"{}\",\"actor\":\"{}\",\"action\":\"{}\",\"resource\":\"{}\",\"status\":\"SUCCESS\",\"durationMs\":{}}}",
                    traceId, actor, audit.action(), audit.resource(), duration
            );


            return result;
        } catch (Exception ex) {
            long duration = System.currentTimeMillis() - start;
            log.error("{{\"action\":\"{}\",\"actor\":\"{}\",\"resource\":\"{}\",\"status\":\"ERROR\",\"durationMs\":{},\"error\":\"{}\"}}",
                    audit.action(), actor, audit.resource(), duration, ex.getMessage());
            throw ex;
        }
    }
}
