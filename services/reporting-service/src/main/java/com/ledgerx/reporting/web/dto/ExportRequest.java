package com.ledgerx.reporting.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExportRequest {

    // Example: CREDIT_LINES, CLIENTS, ACCOUNTS ...
    private String reportName;

    // EXCEL or PDF
    private ExportType exportType;

    // Optional filter JSON or search keyword
    private String filter;

    public enum ExportType {
        EXCEL, PDF
    }
}
