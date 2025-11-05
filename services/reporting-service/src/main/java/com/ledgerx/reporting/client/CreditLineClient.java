package com.ledgerx.reporting.client;

import com.ledgerx.reporting.web.dto.CreditLineResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(
        name = "credit-service",
        url = "${credit.service.url}",
        configuration = com.ledgerx.reporting.config.FeignSecurityConfig.class
)
public interface CreditLineClient {
    @GetMapping("/api/credit-lines")
    List<CreditLineResponse> getCreditLines(@RequestParam(value = "status", required = false) String status);
}

