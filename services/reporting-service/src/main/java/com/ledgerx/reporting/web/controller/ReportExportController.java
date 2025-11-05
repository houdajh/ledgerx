package com.ledgerx.reporting.web.controller;

import com.ledgerx.reporting.web.dto.ExportRequest;
import com.ledgerx.reporting.service.impl.ExportServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports/export")
@RequiredArgsConstructor
public class ReportExportController {

    private final ExportServiceFactory exportServiceFactory;

    @PostMapping
    public ResponseEntity<ByteArrayResource> export(@RequestBody ExportRequest request) {
        var exportService = exportServiceFactory.getExporter(request.getExportType());
        byte[] fileBytes = exportService.export(request);

        String fileName = request.getReportName() +
                (request.getExportType() == ExportRequest.ExportType.EXCEL ? ".xlsx" : ".pdf");

        MediaType mediaType = request.getExportType() == ExportRequest.ExportType.EXCEL
                ? MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                : MediaType.APPLICATION_PDF;

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new ByteArrayResource(fileBytes));

    }
}
