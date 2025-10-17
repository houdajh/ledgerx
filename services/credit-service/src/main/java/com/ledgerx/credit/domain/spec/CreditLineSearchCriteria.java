package com.ledgerx.credit.domain.spec;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
public class CreditLineSearchCriteria {

    private UUID tenantId;
    private UUID clientId;
    private String status;            // e.g. PENDING, APPROVED
    private String currency;          // e.g. MAD, EUR
    private BigDecimal minAmount;     // optional
    private BigDecimal maxAmount;     // optional
    private LocalDateTime fromDate;   // optional
    private LocalDateTime toDate;     // optional
}
