package com.ledgerx.credit.web.dto;

import com.ledgerx.credit.domain.enums.CreditStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
