package practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Readdata {
	 public static Object[][] readDataFromExcel(String sheetname) throws IOException {
		 File file=new File("./src/test/resources/Testdata.xlsx");
			FileInputStream fis=new FileInputStream(file);
			XSSFWorkbook book=new XSSFWorkbook(fis);
			XSSFSheet sheet=book.getSheet(sheetname);
			int rownum=sheet.getPhysicalNumberOfRows();
			int cellnum=sheet.getRow(1).getLastCellNum();
			String[][] data =new  String[rownum-1][cellnum];
			for(int i=0;i<rownum-1;i++)
			{ 
				for(int j=0;j<cellnum;j++)
				{
					DataFormatter format=new DataFormatter();
					data[i][j]=format.formatCellValue(sheet.getRow(i+1).getCell(j));
				}
				
			}
			return data; 
}
}