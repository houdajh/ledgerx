package com.ledgerx.credit.domain.repository;

import com.ledgerx.credit.domain.entity.CreditLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CreditLineRepository extends JpaRepository<CreditLine, UUID>,
        JpaSpecificationExecutor<CreditLine> {

}
