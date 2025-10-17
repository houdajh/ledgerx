package com.ledgerx.credit.service;

import com.ledgerx.credit.web.spec.CreditLineSearchCriteria;
import com.ledgerx.credit.web.dto.CreditLineRequest;
import com.ledgerx.credit.web.dto.CreditLineResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;


public interface CreditLineService {

    List<CreditLineResponse> findAll();
    CreditLineResponse findById(UUID id);
    CreditLineResponse create(CreditLineRequest request);
    CreditLineResponse update(UUID id, CreditLineRequest request);
    void softDelete(UUID id);

    List<CreditLineResponse> search(CreditLineSearchCriteria criteria, Pageable pageable);

}
