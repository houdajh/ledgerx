package com.ledgerx.credit.domain.entity;

import com.ledgerx.credit.domain.enums.CreditStatus;
import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "credit_lines",
        indexes = {
                @Index(name = "idx_credit_tenant_status", columnList = "tenant_id, status"),
                @Index(name = "idx_credit_client_created", columnList = "client_id, created_at")
        })
@SQLDelete(sql = "UPDATE credit_lines SET deleted = true, status = 'DELETED' WHERE id = ? AND version = ?")
@Where(clause = "deleted = false")
@DynamicInsert @DynamicUpdate
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CreditLine {

    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false)
    private UUID clientId;

    @Positive
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(length = 3, nullable = false, columnDefinition = "char(3)")
    private String currency;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CreditStatus status = CreditStatus.PENDING;

    @Column(nullable = false)
    private UUID tenantId;

    @CreationTimestamp @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder.Default
    @Column(nullable = false)
    private boolean deleted = false;

    @Version
    private int version;

    public void softDelete() { this.deleted = true; this.status = CreditStatus.DELETED; }
    public boolean isActive() { return !deleted && status != CreditStatus.DELETED; }
}
