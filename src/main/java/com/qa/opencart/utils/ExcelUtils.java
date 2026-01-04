// java
package com.qa.opencart.utils;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.InputStream;
import java.io.IOException;
import java.util.Locale;

public class ExcelUtils {

    // Put the file at `src/test/resources/testdata/OpenCart.xlsx`
    private static final String TESTDATA_FILE = "testdata/OpenCart.xlsx";

    public static Object[][] getTestData(String sheetName) {
        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(TESTDATA_FILE)) {

            if (is == null) {
                throw new IllegalStateException("Excel file not found on classpath: `"+ TESTDATA_FILE +"` (place it under `src/test/resources/`)");
            }

            try (Workbook workbook = WorkbookFactory.create(is)) {
                Sheet sheet = workbook.getSheet(sheetName);
                if (sheet == null) {
                    throw new IllegalStateException("Sheet `"+ sheetName +"` not found in `"+ TESTDATA_FILE +"`");
                }

                Row header = sheet.getRow(0);
                if (header == null) {
                    throw new IllegalStateException("Header row missing in sheet `"+ sheetName +"` of `"+ TESTDATA_FILE +"`");
                }

                int cols = header.getLastCellNum();
                int lastRow = sheet.getLastRowNum();
                if (lastRow < 1) {
                    throw new IllegalStateException("No data rows in sheet `"+ sheetName +"` of `"+ TESTDATA_FILE +"`");
                }

                Object[][] data = new Object[lastRow][cols];

                for (int r = 1; r <= lastRow; r++) {
                    Row row = sheet.getRow(r);
                    if (row == null) {
                        for (int c = 0; c < cols; c++) data[r - 1][c] = "";
                        continue;
                    }
                    for (int c = 0; c < cols; c++) {
                        Cell cell = row.getCell(c, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        data[r - 1][c] = getCellString(cell);
                    }
                }
                return data;
            }

        } catch (EncryptedDocumentException ede) {
            throw new RuntimeException("Excel is encrypted or invalid (`" + TESTDATA_FILE + "`) - " + ede.getMessage(), ede);
        } catch (IOException ioe) {
            throw new RuntimeException("Failed to read Excel test data (`" + TESTDATA_FILE + "`) - " + ioe.getMessage(), ioe);
        }
    }

    private static String getCellString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                double d = cell.getNumericCellValue();
                if (d == Math.floor(d)) return String.format(Locale.ROOT, "%.0f", d);
                return String.valueOf(d);
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    FormulaEvaluator eval = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                    CellValue cv = eval.evaluate(cell);
                    if (cv == null) return "";
                    switch (cv.getCellType()) {
                        case STRING: return cv.getStringValue();
                        case NUMERIC:
                            double dv = cv.getNumberValue();
                            if (dv == Math.floor(dv)) return String.format(Locale.ROOT, "%.0f", dv);
                            return String.valueOf(dv);
                        case BOOLEAN: return String.valueOf(cv.getBooleanValue());
                        default: return "";
                    }
                } catch (Exception ex) {
                    return cell.getCellFormula();
                }
            case BLANK:
            case _NONE:
            case ERROR:
            default:
                return "";
        }
    }
}
