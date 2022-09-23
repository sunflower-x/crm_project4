package bing.hui.crm.poiDemo;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CreateExcelDemo {
    public static void main(String[] args) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("学号");
        cell = row.createCell(1);
        cell.setCellValue("姓名");
        cell = row.createCell(2);
        cell.setCellValue("年龄");

        for (int i = 1; i <= 10; i++) {
            row=sheet.createRow(i);
            cell=row.createCell(0);
            cell.setCellValue(100+i);
            cell=row.createCell(1);
            cell.setCellValue("name"+i);
            cell=row.createCell(2);
            cell.setCellValue(20+i);

        }
        OutputStream outputStream = new FileOutputStream("D:\\TestPoi\\a\\studentList.xls");
        workbook.write(outputStream);

        outputStream.close();
        workbook.close();
    }
}
