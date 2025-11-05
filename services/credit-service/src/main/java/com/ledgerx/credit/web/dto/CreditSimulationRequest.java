package com.ledgerx.credit.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditSimulationRequest {

    private BigDecimal amount;          // e.g. 100000.00
    private Integer duration;           // e.g. 24 months
    private BigDecimal annualRate;      // e.g. 5.5 (%)
    private BigDecimal insuranceRate;   // optional


}
