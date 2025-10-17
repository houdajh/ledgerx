package com.ledgerx.credit.web.spec;

import com.ledgerx.credit.domain.entity.CreditLine;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreditLineSpecification {

    public static Specification<CreditLine> build(CreditLineSearchCriteria criteria) {
        Specification<CreditLine> spec = Specification.allOf();

        if (criteria.getTenantId() != null)
            spec = spec.and(hasTenantId(criteria.getTenantId()));

        if (criteria.getClientId() != null)
            spec = spec.and(hasClientId(criteria.getClientId()));

        if (criteria.getCurrency() != null)
            spec = spec.and(hasCurrency(criteria.getCurrency()));

        if (criteria.getStatus() != null)
            spec = spec.and(hasStatus(criteria.getStatus()));

        if (criteria.getMinAmount() != null || criteria.getMaxAmount() != null)
            spec = spec.and(hasAmountBetween(criteria.getMinAmount(), criteria.getMaxAmount()));

        if (criteria.getFromDate() != null || criteria.getToDate() != null)
            spec = spec.and(hasDateBetween(criteria.getFromDate(), criteria.getToDate()));

        return spec;
    }



    public static Specification<CreditLine> hasTenantId(UUID tenantId) {
        return ((root, query, cb) ->cb.equal(root.get("tenantId"), tenantId));
    }

    public static Specification<CreditLine> hasStatus(String status) {
        return ((root, query, cb) ->cb.equal(root.get("status"), status));
    }

    public static Specification<CreditLine> hasCurrency(String currency) {
        return (root, query, cb) ->
                cb.equal(cb.lower(root.get("currency")), currency.toLowerCase());
    }


    public static Specification<CreditLine> hasAmountBetween(BigDecimal minAmount, BigDecimal maxAmount) {
        return ((root, query, cb) ->cb.between(root.get("amount"), minAmount,maxAmount));
    }
    public static Specification<CreditLine> hasDateBetween(LocalDateTime fromDate, LocalDateTime toDate) {
        return ((root, query, cb) ->cb.between(root.get("createdAt"), fromDate,toDate));
    }

    public static Specification<CreditLine> hasClientId(UUID clientId) {
        return ((root, query, cb) ->cb.equal(root.get("clientId"), clientId));
    }


}
