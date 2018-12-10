package helper;

import java.io.FileInputStream;
import java.util.*;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.DataFormatter;

public class ExcelUtils {
	private static String filePath = "test-data.xlsx";
	private static String defaultsSheetName = "defaultData";
	private static String testsSheetName = "testCases";
    private static XSSFWorkbook ExcelWBook;
	private static XSSFSheet DefaultDataSheet;
	private static XSSFSheet TestDataSheet;
    
    public static void setExcelFile() {
    	try {
    		FileInputStream ExcelFile = new FileInputStream(filePath);
    		
    		ExcelWBook = new XSSFWorkbook(ExcelFile);
    		DefaultDataSheet = ExcelWBook.getSheet(defaultsSheetName);
    		TestDataSheet = ExcelWBook.getSheet(testsSheetName);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
    
    private static int getTableCellIndex(String tableName, int headerRowIndex) {
    	XSSFRow headerRow = DefaultDataSheet.getRow(headerRowIndex);
    	int cellIndex = -1;
    	for (int index = 0; index < headerRow.getLastCellNum(); index++) {
    		XSSFCell cell = headerRow.getCell(index);
    		if (cell == null || cell.getStringCellValue() == "") {
    			continue;
    		}
    		String text = cell.getStringCellValue();
    		if (tableName.equalsIgnoreCase(text)) {
    			cellIndex = index;
    			break;
    		}
    	}
    	if (cellIndex == -1) {
    		System.out.println("None of the cells in the first row were " + tableName);
    	}
    	return cellIndex;
    }
    
    private static LinkedHashMap<String, String> getDefaultData(String tableName, int rowIndex) {
        LinkedHashMap<String, String> data = new LinkedHashMap<String, String>();
        try {
			int tableCellIndex = getTableCellIndex(tableName, rowIndex);
			while(true) {
				rowIndex++;
				XSSFRow row = DefaultDataSheet.getRow(rowIndex);
				XSSFCell key = row.getCell(tableCellIndex);
	    		if (key == null || key.getStringCellValue() == "") {
	    			break;
	    		}
	    		DataFormatter formatter = new DataFormatter();
				String value = formatter.formatCellValue(row.getCell(tableCellIndex + 1));
				data.put(key.getStringCellValue(), value);
			}
			return data;
		} catch (Exception e) {
			System.out.println("Can't feed data from excel for table " + e);
			return data;
		}
    }
    
    private static List<String> getHeaderList(XSSFRow row) {
    	List<String> headerList = new ArrayList<String>();
    	int lastCellIndex = row.getLastCellNum();
    	for (int i = 0; i < lastCellIndex; i++) {
    		headerList.add(row.getCell(i).getStringCellValue());
    	}
    	return headerList;
    }
    
    public static List<LinkedHashMap<String, String>> getTestCaseData(int rowIndex) {
    	System.out.println(TestDataSheet);
    	XSSFRow headerRow = TestDataSheet.getRow(rowIndex);
    	System.out.println(headerRow);
    	List<String> headers = getHeaderList(headerRow);
    	System.out.println(headers);
    	List <LinkedHashMap<String, String>> testCaseList = new ArrayList<LinkedHashMap<String, String>>();
    	rowIndex++;
    	XSSFRow row = TestDataSheet.getRow(rowIndex);
    	try {
			while(row.getCell(0) != null && row.getCell(0).toString() != "") {
		        LinkedHashMap<String, String> testCase = new LinkedHashMap<String, String>();
				for (int i = 0; i < row.getLastCellNum(); i++) {
					DataFormatter formatter = new DataFormatter();
					String value = formatter.formatCellValue(row.getCell(i));
					testCase.put(headers.get(i), value);
				}
				testCaseList.add(testCase);
				rowIndex++;
				row = TestDataSheet.getRow(rowIndex);
			}
			return testCaseList;
		} catch (Exception e) {
			System.out.println("Can't feed data from excel for table " + e);
			return testCaseList;
		}
    }
    
    public static void setDefaultData() {
		Defaults.set("user", ExcelUtils.getDefaultData("userDetails", 0));
		Defaults.set("card", ExcelUtils.getDefaultData("cardDetails", 0));
		Defaults.set("app", ExcelUtils.getDefaultData("appDetails", 0));
    }
}