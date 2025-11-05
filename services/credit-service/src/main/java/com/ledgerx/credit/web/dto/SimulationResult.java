package com.ledgerx.credit.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimulationResult {

    private BigDecimal amount;            // borrowed amount
    private Integer durationMonths;       // number of months
    private BigDecimal rate;              // annual interest rate
    private BigDecimal monthlyPayment;    // monthly repayment
    private BigDecimal totalCost;         // total to repay (amount + interest)
    private BigDecimal totalInterest;     // total interest paid

}
