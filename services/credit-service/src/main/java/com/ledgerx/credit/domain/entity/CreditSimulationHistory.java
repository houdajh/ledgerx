package com.ledgerx.credit.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_simulation_history")
@Data
public class CreditSimulationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private Integer durationMonths;
    private BigDecimal rate;
    private BigDecimal monthlyPayment;
    private LocalDateTime simulatedAt;

    private String userId; // optional, if you want to link to Keycloak user
}
