package com.ledgerx.reporting.service.impl;

import com.ledgerx.reporting.service.ExportService;
import com.ledgerx.reporting.web.dto.ExportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExportServiceFactory {

    private final ExcelExportService excelExportService;
    private final PdfExportService pdfExportService;

    public ExportService getExporter(ExportRequest.ExportType type) {
        if (type == null)
            throw new IllegalArgumentException("Export type cannot be null");

        return switch (type) {
            case EXCEL -> excelExportService;
            case PDF -> pdfExportService;
        };
    }

}
