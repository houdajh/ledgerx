package com.ledgerx.credit.web.mapper;


import com.ledgerx.credit.domain.entity.CreditLine;
import com.ledgerx.credit.domain.enums.CreditStatus;
import com.ledgerx.credit.web.dto.CreditLineRequest;
import com.ledgerx.credit.web.dto.CreditLineResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;



//Avoid compiler warnings when DTO and entity don’t have 1-to-1 field mapping.
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreditLineMapper {

    @Mapping(target = "status", expression = "java(com.ledgerx.credit.domain.enums.CreditStatus.PENDING)")
    @Mapping(target = "deleted", constant = "false")
    CreditLine  toEntity(CreditLineRequest dto);

    CreditLineResponse toResponse(CreditLine entity);

    default CreditStatus map(String status) {
        return CreditStatus.valueOf(status.toUpperCase());
    }

}
