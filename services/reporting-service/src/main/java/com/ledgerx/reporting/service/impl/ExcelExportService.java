package com.ledgerx.reporting.service.impl;

import com.ledgerx.reporting.client.CreditLineClient;
import com.ledgerx.reporting.service.ExportService;
import com.ledgerx.reporting.web.dto.CreditLineResponse;
import com.ledgerx.reporting.web.dto.ExportRequest;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelExportService implements ExportService {

    private final CreditLineClient creditLineClient;

    @Override
    public byte[] export(ExportRequest request) {
        List<CreditLineResponse> data = creditLineClient.getCreditLines(request.getFilter());

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Credit Lines");
            int rowIdx = 0;

            // Header
            Row header = sheet.createRow(rowIdx++);
            String[] columns = {"ID", "Client", "Product Type", "Amount", "Status"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(columns[i]);
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            // Fill rows from real data
            for (CreditLineResponse credit : data) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(credit.getId());
                row.createCell(1).setCellValue(credit.getClientName());
                row.createCell(2).setCellValue(credit.getProductType());
                row.createCell(3).setCellValue(credit.getAmount());
                row.createCell(4).setCellValue(credit.getStatus());
            }

            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to export Excel report", e);
        }
    }
}
