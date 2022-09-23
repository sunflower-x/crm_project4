package bing.hui.crm.hssf;

import bing.hui.crm.commons.utils.HSSFUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;

public class UploadFileDemo {
    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("D:\\TestPoi\\b\\activityList.xls");
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow row=null;
        HSSFCell cell=null;
        for (int i=0;i<=sheet.getLastRowNum();i++){
            row=sheet.getRow(i);
            System.out.println();
            for (int j=0;j<row.getLastCellNum();j++){
                System.out.print(HSSFUtils.getCellValueForStr(row.getCell(j))+" ");

            }
        }
    }
}
