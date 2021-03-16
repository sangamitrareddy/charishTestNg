package qa.testng.Utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import qa.testng.base.BaseClass;

	public class ExcelSheet extends BaseClass {
		
		static String celldata;
		static String Exceldata;
		public static FileInputStream fi;
		public static XSSFWorkbook wb;
		public static XSSFSheet ws;
		
	public static String readexcel(String file_path, String sheet_name, String row_name) throws IOException {

		fi= new FileInputStream(file_path);
		wb= new XSSFWorkbook(fi);
		ws= wb.getSheet(sheet_name);
			
			//Find number of rows in excel file
	int rowCount = ws.getLastRowNum()-ws.getFirstRowNum();

	       //Create a loop over all the rows of excel file to read it
	for (int i = 0; i < rowCount+1; i++) {
		Row row = ws.getRow(i);

		//Create a loop to print cell values in a row

		for (int j = 0; j < row.getLastCellNum(); j++) {
			celldata = row.getCell(j).getStringCellValue();
			if(celldata.equalsIgnoreCase(row_name))
			{
				j++;
				Exceldata = row.getCell(j).getStringCellValue();
				}
			}
		}
	wb.close();
	fi.close();
	return Exceldata;
	}
	}

