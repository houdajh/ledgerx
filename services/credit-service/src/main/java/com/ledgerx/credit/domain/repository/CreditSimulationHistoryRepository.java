package com.ledgerx.credit.domain.repository;

import com.ledgerx.credit.domain.entity.CreditSimulationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditSimulationHistoryRepository extends JpaRepository<CreditSimulationHistory, Long> {
    Page<CreditSimulationHistory> findByUserId(String userId, Pageable pageable);
}
