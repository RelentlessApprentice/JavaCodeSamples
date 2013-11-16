package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Main {

	public static void main(String[] args) throws Exception {
		xlsToCsv();
		createXls();
	}

	private static void xlsToCsv() throws Exception {
		InputStream inputStream = null;
		FileWriter writer = null;
		try {
			inputStream = new FileInputStream("input.xls");
			writer = new FileWriter("output.csv");
			
			Workbook workbook = WorkbookFactory.create(inputStream);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			
			for(int iSheet=0; iSheet<workbook.getNumberOfSheets(); iSheet++){
				Sheet sheet = workbook.getSheetAt(iSheet);
				for(int iRow=0; iRow < sheet.getLastRowNum()+1; iRow++){
					Row row = sheet.getRow(iRow);
					if(row != null){
						for(int iCell=0; iCell < row.getLastCellNum(); iCell++){
							Cell cell = row.getCell(iCell);
							String value = "";
							if(cell != null){
								if (Cell.CELL_TYPE_FORMULA == cell.getCellType()){
									evaluator.evaluateInCell(cell);
								}
								value = cell.toString();
							} else {
								
							}
							writer.append("\"").append(value).append("\"").append(";");
						}
					}
					if(iRow < sheet.getLastRowNum()){
						writer.append("\n");
					}
				}
			}
			writer.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) inputStream.close();
			if (writer != null) writer.close();
		}
	}

	private static void createXls() throws Exception {
		Workbook workbook = new HSSFWorkbook();
		
		Sheet sheet1 = workbook.createSheet("first");
		
		Row row = sheet1.createRow(0);
		row.createCell(0).setCellValue(5.7);
		row.createCell(1).setCellValue("white");
		row.createCell(5).setCellValue("green");
		Row row3 = sheet1.createRow(3);
		row3.createCell(2).setCellValue(true);
		
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream("output.xls");
			workbook.write(fileOut);
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if (fileOut != null) fileOut.close();
		}
	}
	
}
