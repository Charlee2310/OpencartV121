package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ExcelUtility {

    String path;

    public ExcelUtility(String path) {
        this.path = path;
    }

    // Get Total Rows

    public int getRowCount(String sheetName) throws IOException {

        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);

            if(sheet == null)
                return 0;

            return sheet.getLastRowNum();
        }
    }

    // Get Total Cells

    public int getCellCount(String sheetName, int rownum) throws IOException {

        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);

            if(sheet == null || sheet.getRow(rownum) == null)
                return 0;

            return sheet.getRow(rownum).getLastCellNum();
        }
    }

    // Get Cell Data

    public String getCellData(String sheetName, int rownum, int colnum)
            throws IOException {

        try (FileInputStream fi = new FileInputStream(path);
             XSSFWorkbook workbook = new XSSFWorkbook(fi)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);

            if(sheet == null || sheet.getRow(rownum) == null)
                return "";

            XSSFCell cell = sheet.getRow(rownum).getCell(colnum);

            if(cell == null)
                return "";

            DataFormatter formatter = new DataFormatter();

            return formatter.formatCellValue(cell);
        }
    }

    // Set Cell Data

    public void setCellData(String sheetName, int rownum,
                            int colnum, String data) throws IOException {

        File xlfile = new File(path);

        XSSFWorkbook workbook;

        if(xlfile.exists()) {

            try (FileInputStream fi = new FileInputStream(path)) {
                workbook = new XSSFWorkbook(fi);
            }

        } else {

            workbook = new XSSFWorkbook();
        }

        try (workbook) {

            XSSFSheet sheet = workbook.getSheet(sheetName);

            if(sheet == null)
                sheet = workbook.createSheet(sheetName);

            XSSFRow row = sheet.getRow(rownum);

            if(row == null)
                row = sheet.createRow(rownum);

            XSSFCell cell = row.createCell(colnum);

            cell.setCellValue(data);

            try (FileOutputStream fo = new FileOutputStream(path)) {
                workbook.write(fo);
            }
        }
    }

    // Fill Green Color

    public void fillGreenColor(String sheetName, int rownum,
                               int colnum) throws IOException {

        fillColor(sheetName, rownum, colnum, IndexedColors.GREEN);
    }

    // Fill Red Color

    public void fillRedColor(String sheetName, int rownum,
                             int colnum) throws IOException {

        fillColor(sheetName, rownum, colnum, IndexedColors.RED);
    }

    // Common Color Method

    private void fillColor(String sheetName, int rownum,
                           int colnum, IndexedColors color) throws IOException {

        XSSFWorkbook workbook;

        try (FileInputStream fi = new FileInputStream(path)) {
            workbook = new XSSFWorkbook(fi);
        }

        try (workbook) {

            XSSFSheet sheet = workbook.getSheet(sheetName);

            if(sheet == null || sheet.getRow(rownum) == null
                    || sheet.getRow(rownum).getCell(colnum) == null) {
                throw new IOException("Specified sheet, row or cell not found");
            }

            XSSFCell cell = sheet.getRow(rownum).getCell(colnum);

            CellStyle style = workbook.createCellStyle();

            style.setFillForegroundColor(color.getIndex());

            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            cell.setCellStyle(style);

            try (FileOutputStream fo = new FileOutputStream(path)) {
                workbook.write(fo);
            }
        }
    }
}