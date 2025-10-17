package com.ledgerx.credit.service;

import com.ledgerx.credit.domain.entity.CreditLine;
import com.ledgerx.credit.domain.repository.CreditLineRepository;
import com.ledgerx.credit.domain.spec.CreditLineSearchCriteria;
import com.ledgerx.credit.domain.spec.CreditLineSpecification;
import com.ledgerx.credit.exception.NotFoundException;
import com.ledgerx.credit.web.dto.CreditLineRequest;
import com.ledgerx.credit.web.dto.CreditLineResponse;
import com.ledgerx.credit.web.mapper.CreditLineMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CreditLineServiceImpl implements CreditLineService {

    private final CreditLineRepository creditLineRepository;
    private final CreditLineMapper creditLineMapper;



    @Override
    public List<CreditLineResponse> findAll() {
        List<CreditLine> creditLines = creditLineRepository.findAll();
        return creditLines.stream().map(creditLineMapper::toResponse).toList();
    }

    @Override
    public CreditLineResponse findById(UUID id) {
        CreditLine creditLine = creditLineRepository.findById(id).orElseThrow(()-> new NotFoundException("CreditLine not found"+id));
        return creditLineMapper.toResponse(creditLine);
    }

    @Override
    @Transactional
    public CreditLineResponse create(CreditLineRequest request) {
        CreditLine creditLine = creditLineMapper.toEntity(request);
        creditLineRepository.save(creditLine);
        return creditLineMapper.toResponse(creditLine);
    }

    @Override
    @Transactional
    public CreditLineResponse update(UUID id, CreditLineRequest request) {
        CreditLine existing = creditLineRepository.findById(id).orElseThrow(()-> new NotFoundException("CreditLine not found"+id));

        existing.setAmount(request.getAmount());
        existing.setCurrency(request.getCurrency());
        existing.setTenantId(request.getTenantId());
        existing.setClientId(request.getClientId());
        existing.setUpdatedAt(LocalDateTime.now());

        return creditLineMapper.toResponse(creditLineRepository.save(existing));
    }

    @Override
    @Transactional
    public void softDelete(UUID id) {
        CreditLine creditLine = creditLineRepository.findById(id).orElseThrow();
        creditLine.setDeleted(true);
        creditLineRepository.save(creditLine);
        //creditLineRepository.delete(creditLine); this is hard delete
    }

    @Override
    public List<CreditLineResponse> search(CreditLineSearchCriteria criteria) {
        Specification<CreditLine> sp=Specification.allOf(
                CreditLineSpecification.hasTenantId(criteria.getTenantId()),
                CreditLineSpecification.hasClientId(criteria.getClientId()),
                CreditLineSpecification.hasStatus(criteria.getStatus()),
                CreditLineSpecification.hasDevise(criteria.getCurrency()),
                CreditLineSpecification.hasDateBetween(criteria.getFromDate(),criteria.getToDate()),
                CreditLineSpecification.hasAmountBetween(criteria.getMinAmount(),criteria.getMaxAmount())
        );

        return creditLineRepository.findAll(sp).stream().map(creditLineMapper::toResponse).toList();
    }
}
