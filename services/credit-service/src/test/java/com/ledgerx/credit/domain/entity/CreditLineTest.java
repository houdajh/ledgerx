package com.ledgerx.credit.domain.entity;

import com.ledgerx.credit.domain.enums.CreditStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CreditLineTest {

    @Test
    void shouldBuildCreditLineWithDefaults() {
        UUID tenantId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        CreditLine creditLine=CreditLine.builder()
                .clientId(clientId)
                .tenantId(tenantId)
                .amount(BigDecimal.valueOf(1000))
                .currency("EUR")
                .build();

        assertThat(creditLine.getTenantId()).isEqualTo(tenantId);
        assertThat(creditLine.getClientId()).isEqualTo(clientId);
        assertThat(creditLine.getAmount()).isEqualTo(BigDecimal.valueOf(1000));
        assertThat(creditLine.getCurrency()).isEqualTo("EUR");
        assertThat(creditLine.getStatus()).isEqualTo(CreditStatus.PENDING);
            assertThat(creditLine.isActive()).isTrue();
    }

    @Test
    void shouldMarkAsDeletedWhenSoftDeleteCalled() {
        UUID tenantId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        CreditLine creditLine=CreditLine.builder()
                .clientId(clientId)
                .tenantId(tenantId)
                .amount(BigDecimal.valueOf(1000))
                .currency("EUR")
                .build();
        creditLine.softDelete();

        assertThat(creditLine.isDeleted()).isTrue();
        assertThat(creditLine.getStatus()).isEqualTo(CreditStatus.DELETED);
        assertThat(creditLine.isActive()).isFalse();
    }


}
