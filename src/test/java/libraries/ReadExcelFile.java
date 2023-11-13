package libraries;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelFile {

	public ReadExcelFile() {

	}

	public void readExcel(String filepath, String sheetName) throws IOException {

		File file = new File(filepath);

		FileInputStream inputStream = new FileInputStream(file);

		try (XSSFWorkbook newWorkBook = new XSSFWorkbook(inputStream)) {
			XSSFSheet newSheet = newWorkBook.getSheet(sheetName);

			int rowCount = newSheet.getLastRowNum() + newSheet.getFirstRowNum();

			for (int i = 0; i < rowCount + 1; i++) {
				XSSFRow row = newSheet.getRow(i);

				for (int j = 0; j < row.getLastCellNum(); j++) {
					DataFormatter formatter = new DataFormatter();
					Cell cell = newSheet.getRow(i).getCell(j);
					formatter.formatCellValue(cell);
				}
			}
		}
	}

	public String getCellValue(String filePath, String sheetName, int rowNumber, int cellNumber) throws IOException {

		File file = new File(filePath);

		FileInputStream inputStream = new FileInputStream(file);

		try (XSSFWorkbook newWorkBook = new XSSFWorkbook(inputStream)) {
			XSSFSheet newSheet = newWorkBook.getSheet(sheetName);

			XSSFRow row = newSheet.getRow(rowNumber);

			XSSFCell cell = row.getCell(cellNumber);

			DataFormatter formatter = new DataFormatter();
			String cellString = formatter.formatCellValue(cell);

			// System.out.println("El contenido de cellStirng " + cellString);

			String replacedString = cellString.replace(",", ".");

			// System.out.println("El contenido de repacedString" + replacedString);
			return replacedString;
		}
	}

//public String getCellValueMonto(String filePath, String sheetName, int rowNumber, int cellNumber) throws IOException {
//		
//		File file = new File(filePath);
//
//		FileInputStream inputStream = new FileInputStream(file);
//
//        try (XSSFWorkbook newWorkBook = new XSSFWorkbook(inputStream)) {
//			XSSFSheet newSheet = newWorkBook.getSheet(sheetName);
//			 
//			XSSFRow row = newSheet.getRow(rowNumber);
//			
//			XSSFCell cell = row.getCell(cellNumber); 
//			System.out.println("Contenido de Cell "+ cell);
//			
//			double value = cell.getNumericCellValue(); // Obtener el valor de la celda como double
//			System.out.println("Contenido de Value "+ value);
//			DecimalFormat df = new DecimalFormat("0.00"); // Crear un objeto DecimalFormat
//			System.out.println("Contenido de df "+ df);
//			String formattedValue = df.format(value); // Formatear el valor double usando DecimalFormat
//			System.out.println("Contenido de formattedValue "+ formattedValue);
//			String stringValue = formattedValue.replaceAll("\\.", ""); // Eliminar los puntos del resultado formateado
//			System.out.println("Contenido de StringValue "+ stringValue);
//			return stringValue;
//		}
//	}
//	
	public int countRows(String filepath, String sheetName) throws IOException {

		File file = new File(filepath);

		FileInputStream inputStream = new FileInputStream(file);

		try (XSSFWorkbook newWorkBook = new XSSFWorkbook(inputStream)) {
			XSSFSheet newSheet = newWorkBook.getSheet(sheetName);

			return newSheet.getLastRowNum();

		}

	}

	public static int search(String filepath) {
		try {
			FileInputStream file = new FileInputStream(filepath);
			try (XSSFWorkbook workbook = new XSSFWorkbook(file)) {
				XSSFSheet sheet = workbook.getSheetAt(0);

				String searchText = "Operaciones entre cuentas propias";

				for (Row row : sheet) {
					for (Cell cell : row) {
						if (cell.getCellType() == CellType.STRING) {
							String cellValue = cell.getStringCellValue();
							if (cellValue.contains(searchText)) {
								return row.getRowNum();
								// System.out.println("Texto encontrado en la celda (" + row.getRowNum() + ", "
								// + cell.getColumnIndex() + ")");
							}
						}
					}
				}

				workbook.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}