package com.ledgerx.credit.web.dto;

import com.ledgerx.credit.domain.enums.CreditStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CreditLineResponse {

    private UUID id;
    private UUID clientId;
    private BigDecimal amount;
    private String currency;
    private CreditStatus status;
    private UUID tenantId;
    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String comment;
}
