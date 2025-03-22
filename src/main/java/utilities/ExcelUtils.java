package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExcelUtils {
    private static Workbook workbook;
    private static Sheet sheet;

    public static void loadExcel(String filePath, String sheetName) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("Excel file not found: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IOException("Sheet not found: " + sheetName);
            }
        }
    }

    public static String getCellData(int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) return "";

        Cell cell = row.getCell(colNum);
        if (cell == null) return "";

        return cell.toString();
    }

    public static void closeExcel() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }

    // ✅ NEW METHOD: Get a random product from the "Products" sheet
    public static String getRandomProductName(String filePath, String sheetName) throws IOException {
        loadExcel(filePath, sheetName);
        List<String> productNames = new ArrayList<>();

        int totalRows = sheet.getPhysicalNumberOfRows();
        for (int i = 1; i < totalRows; i++) { // Skip header row
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    productNames.add(cell.getStringCellValue().trim());
                }
            }
        }

        closeExcel(); // Close after reading

        if (productNames.isEmpty()) {
            throw new IOException("No product names found in the sheet.");
        }

        Random random = new Random();
        return productNames.get(random.nextInt(productNames.size())); // Pick a random product
    }
}
