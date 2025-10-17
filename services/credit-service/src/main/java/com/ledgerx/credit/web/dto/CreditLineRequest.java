package com.ledgerx.credit.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreditLineRequest {

    @NotNull(message = "clientId is required")
    private UUID clientId;

    @NotNull(message = "amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "currency is required")
    @Size(min = 3, max = 3, message = "currency must be ISO code (e.g. MAD, EUR)")
    private String currency;

    @NotNull(message = "tenantId is required")
    private UUID tenantId;

}
