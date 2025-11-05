package com.ledgerx.reporting.service;

import com.ledgerx.reporting.web.dto.ExportRequest;

public interface ExportService {

    /**
     * Generates a report file based on the export request.
     *
     * @param request the export parameters (type, filters, report name)
     * @return a byte array representing the generated file (Excel or PDF)
     */
    byte[] export(ExportRequest request);
}
//Strategy pattern