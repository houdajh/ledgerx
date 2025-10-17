package com.ledgerx.credit.web.controller;

import com.ledgerx.credit.web.spec.CreditLineSearchCriteria;
import com.ledgerx.credit.service.CreditLineService;
import com.ledgerx.credit.web.dto.CreditLineRequest;
import com.ledgerx.credit.web.dto.CreditLineResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/credits")
public class CreditLineController {

    private final CreditLineService creditLineService;

    @GetMapping
    public ResponseEntity<List<CreditLineResponse>> getCreditLines() {
        return ResponseEntity.ok(creditLineService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditLineResponse> findCreditLineById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(creditLineService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CreditLineResponse> createCreditLine(@Valid @RequestBody CreditLineRequest creditLineRequest) {
        log.info("Creating credit line for client {}", creditLineRequest.getClientId());
        return ResponseEntity.ok(creditLineService.create(creditLineRequest));
    }

    @PostMapping("/search")
    public ResponseEntity<List<CreditLineResponse>> searchCredits(@RequestBody @Valid CreditLineSearchCriteria criteria,
                                                                  @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        List<CreditLineResponse> results = creditLineService.search(criteria,pageable);
        return ResponseEntity.ok(results);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreditLineResponse> updateCreditLine(@PathVariable("id") UUID id, @Valid @RequestBody CreditLineRequest creditLineRequest) {
        log.info("Updating credit line {}", id);
        return ResponseEntity.ok(creditLineService.update(id, creditLineRequest));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreditLine(@PathVariable("id") UUID id) {
        log.info("Soft deleting credit line {}", id);
        creditLineService.softDelete(id);
        return ResponseEntity.noContent().build();
    }



}
