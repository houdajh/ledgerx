package com.ledgerx.credit.domain.entity;

import com.ledgerx.credit.domain.enums.CreditStatus;
import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity

//Global soft-delete filter enabled via JpaConfig
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")

@Table(
        name = "credit_lines",
        indexes = {
                @Index(name = "idx_credit_tenant_status", columnList = "tenant_id, status"),
                @Index(name = "idx_credit_client_created", columnList = "client_id, created_at")
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Builder

//Avoids overwriting null/default fields
@DynamicInsert
@DynamicUpdate

@Data
public class CreditLine {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID clientId;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(length = 3, nullable = false)
    private String currency; // e.g. "MAD", "EUR"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CreditStatus status;

    @Column(nullable = false)
    private UUID tenantId; // organization / company

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean deleted = false;

    @Version
    private int version; // Optimistic locking

    // ---- domain helper methods ----

    public void softDelete() {
        this.deleted = true;
        this.status = CreditStatus.DELETED;
    }

    public boolean isActive() {
        return !deleted && status != CreditStatus.DELETED;
    }
}
