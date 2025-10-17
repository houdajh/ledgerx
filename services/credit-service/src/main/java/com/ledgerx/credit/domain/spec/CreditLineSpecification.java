package com.ledgerx.credit.domain.spec;

import com.ledgerx.credit.domain.entity.CreditLine;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreditLineSpecification {

    public static Specification<CreditLine> hasTenantId(UUID tenantId) {
        return ((root, query, cb) ->cb.equal(root.get("tenantId"), tenantId));
    }

    public static Specification<CreditLine> hasStatus(String status) {
        return ((root, query, cb) ->cb.equal(root.get("status"), status));
    }

    public static Specification<CreditLine> hasDevise(String currency) {
        return ((root, query, cb) ->cb.equal(root.get("currency"), currency));
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
