package libraries.productFunctions;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.By;

import interfaces.I_AIMS_Incident_Details;
import jxl.read.biff.BiffException;
import libraries.SeleniumBrowser;
import libraries.generalFunctions.ExcelReader;
import libraries.generalFunctions.Functions;
import libraries.objects.VRMExcel;

public class F_Common {
		
	// Create Objects
	public static void getVRM(SeleniumBrowser Browser) throws BiffException, IOException {
		ExcelReader excelReaderObj ;
		CommonMethods commonMethodobj = new CommonMethods();
		VRMExcel td = new VRMExcel();
		 
		// Load the excel file for testing
		excelReaderObj = new ExcelReader("D:\\Excel.xls");
		 
		// Load the Excel Sheet Col in to Dictionary for use in test cases
		excelReaderObj.ColumnDictionary();
		 
		// Get the data from excel file
		commonMethodobj.readExcelData (td);
		// Populate the username
		
		Browser.Driver.findElement(By.id("idofElement")).sendKeys(td.getVrmNumber().get(0));
	}
	
	
	
	
	
	
	
	
}	

