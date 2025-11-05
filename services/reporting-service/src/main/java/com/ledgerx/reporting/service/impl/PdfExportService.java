package com.ledgerx.reporting.service.impl;

import com.ledgerx.reporting.service.ExportService;
import com.ledgerx.reporting.web.dto.ExportRequest;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;


@Service
public class PdfExportService implements ExportService {
    @Override
    public byte[] export(ExportRequest request) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph("LedgerX Report"));
            document.add(new Paragraph("Filter: " + request.getFilter()));
            document.add(new Paragraph("Generated from Reporting Service"));
            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to export PDF", e);
        }
    }
}
