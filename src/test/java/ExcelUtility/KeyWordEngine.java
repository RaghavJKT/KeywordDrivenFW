package ExcelUtility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.Base;

public class KeyWordEngine {

	public Base base;
	public WebElement element;
	public WebDriver driver;
	public Properties prop;

	//public static Workbook book;
	//public static Sheet sheet;
	
	public static XSSFWorkbook book;
	public static XSSFSheet sheet;

	public final static String SHEET_PATH = "C:\\Users\\Ragavendran.v\\eclipse-workspace\\KeyWordDrivenFrameworks\\src\\test\\java\\ExcelData\\KeywordData.xlsx";

	public void startEngine(String sheetName) {

		String locatorName = null;
		String locatorValue = null;

		FileInputStream file = null;
		try {
			file = new FileInputStream(SHEET_PATH);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			//book = WorkbookFactory.create(file);
			book = new XSSFWorkbook(file);
		} catch (EncryptedDocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetName);
		//int j = 0;
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			try {
				String locatorColValue = sheet.getRow(i + 1).getCell(1).toString().trim();
				if (!locatorColValue.equalsIgnoreCase("NA")) {
					locatorName = locatorColValue.split("=")[0].trim();
					locatorValue = locatorColValue.split("=")[1].trim();
				}

				String action = sheet.getRow(i + 1).getCell(2).toString().trim();
				String value = sheet.getRow(i + 1).getCell(3).toString().trim();

				switch (action) {
				case "open browser":
					base = new Base();
					try {
						prop = base.init_properties();
						if (value.isEmpty() || value.equals("NA")) {
							driver = base.init_driver(prop.getProperty("browser"));
						} else {
							driver = base.init_driver(value);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

					break;

				case "enter url":

					if (value.isEmpty() || value.equals("NA")) {
						driver = base.init_driver(prop.getProperty("url"));
					} else {
						driver.get(value);
					}
					break;

				case "quit":
					Thread.sleep(5000);
					driver.quit();
					break;

				default:
					break;
				}

				switch (locatorName) {
				case "id":
					element = driver.findElement(By.id(locatorValue));
					if (action.equalsIgnoreCase("sendkeys")) {
						element.clear();
						element.sendKeys(value);
					} else if (action.equalsIgnoreCase("click")) {
						element.click();
					}
					locatorName = null;
					break;

				case "linkText":
					element = driver.findElement(By.linkText(locatorValue));
					element.click();
					locatorName = null;
					break;

				default:
					break;

				}
			} catch (Exception e) {

			}

		}
	}

}
