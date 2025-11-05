package com.ledgerx.credit.service.Impl;

import com.ledgerx.credit.domain.entity.CreditSimulationHistory;
import com.ledgerx.credit.domain.repository.CreditSimulationHistoryRepository;
import com.ledgerx.credit.security.SecurityUtils;
import com.ledgerx.credit.service.CreditService;
import com.ledgerx.credit.web.dto.CreditSimulationRequest;
import com.ledgerx.credit.web.dto.SimulationResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;


@Service
public class CreditServiceImpl implements CreditService {

    private final CreditSimulationHistoryRepository historyRepository;

    public CreditServiceImpl(CreditSimulationHistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public SimulationResult simulateCredit(CreditSimulationRequest request) {
        // existing calculation logic...
        SimulationResult result = calculate(request);

        // store simulation result
        CreditSimulationHistory history = new CreditSimulationHistory();
        history.setAmount(request.getAmount());
        history.setDurationMonths(request.getDuration());
        history.setRate(result.getRate());
        history.setMonthlyPayment(result.getMonthlyPayment());
        history.setSimulatedAt(LocalDateTime.now());
        history.setUserId(SecurityUtils.getCurrentUserId());
        historyRepository.save(history);
        return result;
    }

    private SimulationResult calculate(CreditSimulationRequest request) {
        BigDecimal amount = request.getAmount();
        BigDecimal annualRate = request.getAnnualRate().divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
        int months = request.getDuration();

        // monthly rate = annual / 12
        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        // monthly payment formula: A * r / (1 - (1 + r)^-n)
        BigDecimal numerator = amount.multiply(monthlyRate);
        BigDecimal denominator = BigDecimal.ONE.subtract(
                BigDecimal.ONE.add(monthlyRate).pow(-months, new MathContext(10))
        );
        BigDecimal monthlyPayment = numerator.divide(denominator, 2, RoundingMode.HALF_UP);

        BigDecimal totalCost = monthlyPayment.multiply(BigDecimal.valueOf(months));
        BigDecimal totalInterest = totalCost.subtract(amount);

        return new SimulationResult(
                amount, months, request.getAnnualRate(),
                monthlyPayment, totalCost, totalInterest
        );
    }

}
