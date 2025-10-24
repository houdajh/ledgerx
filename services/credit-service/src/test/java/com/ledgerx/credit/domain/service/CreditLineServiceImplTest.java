package com.ledgerx.credit.domain.service;

import com.ledgerx.credit.domain.entity.CreditLine;
import com.ledgerx.credit.domain.repository.CreditLineRepository;
import com.ledgerx.credit.service.CreditLineServiceImpl;
import com.ledgerx.credit.web.dto.CreditLineRequest;
import com.ledgerx.credit.web.dto.CreditLineResponse;
import com.ledgerx.credit.web.mapper.CreditLineMapper;
import com.ledgerx.credit.web.spec.CreditLineSearchCriteria;
import com.ledgerx.credit.web.spec.CreditLineSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CreditLineServiceImplTest {

    @Mock
    private CreditLineRepository creditLineRepository;

    @Mock
    private CreditLineMapper creditLineMapper;

    @InjectMocks
    private CreditLineServiceImpl creditLineService;

    @Test
    void shouldFindAll() {
        // Arrange
        CreditLine entity = new CreditLine();
        entity.setCurrency("EUR");

        CreditLineResponse dto = new CreditLineResponse();
        dto.setCurrency("EUR");

        when(creditLineRepository.findAll()).thenReturn(List.of(entity));
        when(creditLineMapper.toResponse(entity)).thenReturn(dto);

        // Act
        List<CreditLineResponse> result = creditLineService.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getCurrency()).isEqualTo("EUR");
    }

    @Test
    void shouldFindById() {

        UUID id = UUID.randomUUID();
        // Arrange
        CreditLine entity = new CreditLine();
        entity.setCurrency("EUR");

        CreditLineResponse dto = new CreditLineResponse();
        dto.setCurrency("EUR");

        when(creditLineRepository.findById(id)).thenReturn(Optional.of(entity));
        when(creditLineMapper.toResponse(entity)).thenReturn(dto);

        // Act
        CreditLineResponse result = creditLineService.findById(id);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCurrency()).isEqualTo("EUR");

        // Optionnel : vérifie que le repo et le mapper ont bien été utilisés
        verify(creditLineRepository).findById(id);
        verify(creditLineMapper).toResponse(entity);
    }

    @Test
    void shouldCreate() {
        UUID tenantId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        CreditLine creditLine = CreditLine.builder()
                .clientId(clientId)
                .tenantId(tenantId)
                .amount(BigDecimal.valueOf(1000))
                .currency("EUR")
                .build();

        CreditLineRequest creditLineRequest = CreditLineRequest.builder()
                .clientId(clientId)
                .tenantId(tenantId)
                .amount(BigDecimal.valueOf(1000))
                .currency("EUR")
                .build();

        CreditLineResponse creditLineResponse = CreditLineResponse.builder()
                .clientId(clientId)
                .tenantId(tenantId)
                .amount(BigDecimal.valueOf(1000))
                .currency("EUR")
                .build();
        when(creditLineRepository.save(creditLine)).thenReturn(creditLine);
        when(creditLineMapper.toResponse(creditLine)).thenReturn(creditLineResponse);
        when(creditLineMapper.toEntity(creditLineRequest)).thenReturn(creditLine);

        CreditLineResponse result = creditLineService.create(creditLineRequest);

        assertThat(result).isNotNull();
        assertThat(result.getCurrency()).isEqualTo("EUR");
        assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(1000));
        assertThat(result.getClientId()).isEqualTo(clientId);
        assertThat(result.isDeleted()).isFalse();

        verify(creditLineMapper).toEntity(creditLineRequest);
        verify(creditLineRepository).save(creditLine);
        verify(creditLineMapper).toResponse(creditLine);

    }

    @Test
    void shouldUpdate() {
        UUID tenantId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        CreditLine creditLine = CreditLine.builder()
                .clientId(clientId)
                .tenantId(tenantId)
                .amount(BigDecimal.valueOf(1000))
                .currency("MAD")
                .build();

        CreditLineRequest creditLineRequest = CreditLineRequest.builder()
                .clientId(clientId)
                .tenantId(tenantId)
                .amount(BigDecimal.valueOf(1000))
                .currency("EUR")
                .build();

        CreditLineResponse creditLineResponse = CreditLineResponse.builder()
                .clientId(clientId)
                .tenantId(tenantId)
                .amount(BigDecimal.valueOf(1000))
                .currency("EUR")
                .build();
        when(creditLineRepository.findById(id)).thenReturn(Optional.ofNullable(creditLine));
        when(creditLineRepository.save(any(CreditLine.class))).thenReturn(creditLine);
        when(creditLineMapper.toResponse(any(CreditLine.class)))
                .thenReturn(creditLineResponse);


        CreditLineResponse result = creditLineService.update(id, creditLineRequest);

        assertThat(result).isNotNull();
        assertThat(result).isSameAs(creditLineResponse);
        assertThat(result.getCurrency()).isEqualTo("EUR");
        assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(1000));
        assertThat(result.getClientId()).isEqualTo(clientId);
        assertThat(result.isDeleted()).isFalse();

        verify(creditLineRepository).findById(id);
        verify(creditLineRepository).save(any(CreditLine.class));
        verify(creditLineMapper).toResponse(any(CreditLine.class));


    }

    @Test
    void shouldSoftDelete() {
        UUID tenantId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        CreditLine creditLine = CreditLine.builder()
                .clientId(clientId)
                .tenantId(tenantId)
                .amount(BigDecimal.valueOf(1000))
                .currency("MAD")
                .build();

        when(creditLineRepository.findById(id)).thenReturn(Optional.of(creditLine));

        creditLineService.softDelete(id);

        assertThat(creditLine.isActive()).isFalse();
        assertThat(creditLine.isDeleted()).isTrue();


        verify(creditLineRepository).findById(id);
        verify(creditLineRepository).save(creditLine);
    }

    @Test
    void shouldSearch() {
        UUID tenantId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();

        CreditLine creditLine = CreditLine.builder()
                .clientId(clientId)
                .tenantId(tenantId)
                .amount(BigDecimal.valueOf(1000))
                .currency("MAD")
                .build();


        CreditLineSearchCriteria searchCriteria = CreditLineSearchCriteria.builder()
                .clientId(clientId)
                .currency("MAD")
                .build();
        Specification<CreditLine> spec = CreditLineSpecification.build(searchCriteria);
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<CreditLine> creditLines = List.of(creditLine);
        Page<CreditLine> creditLinePage = new PageImpl<>(creditLines, pageable, creditLines.size());

        CreditLineResponse creditLineResponseList = CreditLineResponse.builder()
                .clientId(clientId)
                .tenantId(tenantId)
                .amount(BigDecimal.valueOf(1000))
                .currency("MAD")
                .build();

        when(creditLineRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(creditLinePage);

        when(creditLineMapper.toResponse(creditLine)).thenReturn(creditLineResponseList);

        creditLineService.search(searchCriteria, pageable);

        verify(creditLineRepository).findAll(any(Specification.class), eq(pageable));
        verify(creditLineMapper).toResponse(creditLine);

    }
}
