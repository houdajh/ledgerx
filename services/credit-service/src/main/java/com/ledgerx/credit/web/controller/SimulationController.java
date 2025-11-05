package com.ledgerx.credit.web.controller;

import com.ledgerx.credit.domain.entity.CreditSimulationHistory;
import com.ledgerx.credit.domain.repository.CreditSimulationHistoryRepository;
import com.ledgerx.credit.security.SecurityUtils;
import com.ledgerx.credit.service.CreditService;
import com.ledgerx.credit.web.dto.CreditSimulationRequest;
import com.ledgerx.credit.web.dto.SimulationResult;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/credits")
public class SimulationController {

    private final Counter simulationCounter;
    private final CreditService creditService;
    private final CreditSimulationHistoryRepository historyRepository;

    public SimulationController(MeterRegistry meterRegistry, CreditService creditService, CreditSimulationHistoryRepository historyRepository) {
        // Create a custom Prometheus counter metric
        this.simulationCounter = Counter.builder("ledgerx_simulations_total")
                .description("Total number of credit simulations processed")
                .register(meterRegistry);
        this.creditService = creditService;
        this.historyRepository = historyRepository;
    }



    @PostMapping("/simulate")
    public SimulationResult simulate(@RequestBody CreditSimulationRequest request) {
        return creditService.simulateCredit(request);
    }

    @GetMapping("/history")
    public Page<CreditSimulationHistory> getAllSimulations(
            @PageableDefault(sort = "simulatedAt", direction = Sort.Direction.DESC) Pageable pageable) {

        String userId = SecurityUtils.getCurrentUserId();
        return historyRepository.findByUserId(userId, pageable);
    }



    @GetMapping("/simulate")
    public String simulate() throws InterruptedException {
        log.info("Processing credit in thread {}", Thread.currentThread());

        // Simulate some processing time
        Thread.sleep(2000);

        // Increment counter each time endpoint is called
        simulationCounter.increment();

        return "ok";
    }

}