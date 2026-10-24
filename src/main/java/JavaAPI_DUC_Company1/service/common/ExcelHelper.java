package JavaAPI_DUC_Company1.service.common;

import org.apache.poi.ss.usermodel.*;

public class ExcelHelper {
    public static void createHeaders(
            Workbook workbook,
            Sheet sheet, String[] headerNames, Integer[] headerWidths) {

        // Create font
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);

        // Create style
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(headerFont);

        // Yellow background
        headerStyle.setFillForegroundColor(
                IndexedColors.YELLOW.getIndex());
        headerStyle.setFillPattern(
                FillPatternType.SOLID_FOREGROUND);

        // (Optional) Center the text
        headerStyle.setAlignment(
                HorizontalAlignment.CENTER);

        // (Optional) Vertical center
        headerStyle.setVerticalAlignment(
                VerticalAlignment.CENTER);

        // ===== Add borders =====
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        headerStyle.setTopBorderColor(
                IndexedColors.BLACK.getIndex());

        headerStyle.setBottomBorderColor(
                IndexedColors.BLACK.getIndex());

        headerStyle.setLeftBorderColor(
                IndexedColors.BLACK.getIndex());

        headerStyle.setRightBorderColor(
                IndexedColors.BLACK.getIndex());

        // =======================

        Row header = sheet.createRow(0);

        Cell cell;

        for (int i = 0; i < headerNames.length; i++) {
            cell = header.createCell(i);
            cell.setCellValue(headerNames[i]);
            cell.setCellStyle(headerStyle);

            sheet.setColumnWidth(i, headerWidths[i]);
        }

    }
}
