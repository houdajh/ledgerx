package com.ledgerx.credit.service;

import com.ledgerx.credit.domain.spec.CreditLineSearchCriteria;
import com.ledgerx.credit.web.dto.CreditLineRequest;
import com.ledgerx.credit.web.dto.CreditLineResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface CreditLineService {

    List<CreditLineResponse> findAll();
    CreditLineResponse findById(UUID id);
    CreditLineResponse create(CreditLineRequest request);
    CreditLineResponse update(UUID id, CreditLineRequest request);
    void softDelete(UUID id);

    List<CreditLineResponse> search(CreditLineSearchCriteria criteria);

}
