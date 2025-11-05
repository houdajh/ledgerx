package com.ledgerx.reporting.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditLineResponse {
    private String id;
    private String clientName;
    private String productType;
    private double amount;
    private String status;
}
