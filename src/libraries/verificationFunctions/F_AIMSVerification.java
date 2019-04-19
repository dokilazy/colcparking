package libraries.verificationFunctions;

import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.generalFunctions.Functions;
import libraries.objects.O_Evidence;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.google.common.collect.Table;

import interfaces.I_AIMS_Incident_Details;
import interfaces.I_AIMS_Settings;
import libraries.Constants;

public class F_AIMSVerification {

	public static void verifyEvidence(O_Evidence evidence, SeleniumBrowser Browser, TCResult pResult) {

		List<String> row = new ArrayList<>();
		String evidentList = "";
		int r = Functions.countRow(Browser);
		boolean flag = false;

		for (int i = 1; i <= r; i++) {
			row = Functions.GetRowData(Browser, i);
			if (row.get(2).contains(evidence.Desc)) {
				flag = true;
			break;
			}
			evidentList += row.get(2) + ",";
			
			

		}
		
		if (flag == false) {
			pResult.SetResult(false);
			pResult.SetMessage("Evidence " + evidence.Desc + " is not exist");
			pResult.SetMessage("List Evidence: " + evidentList);
			return;
		}
		
		F_GeneralVerification.verifyElementValue(Browser, "Author", row.get(4), evidence.Author, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Type", row.get(1), evidence.Type, pResult);
	
		if(evidence.Uploadedon != null)
		{
			F_GeneralVerification.verifyDatetime(Browser, "Uploaded on", row.get(5), evidence.Uploadedon, 2, pResult);
		
			if(evidence.Observedon == null)
				{
					evidence.Observedon = evidence.Uploadedon;
				}
			F_GeneralVerification.verifyDatetime(Browser, "Observed on", row.get(3), evidence.Observedon, 2, pResult);
		}
	}

	public static void verifyExportLog(O_Evidence evidence, SeleniumBrowser Browser, TCResult pResult) {

		List<String> row = new ArrayList<>();

		int r = Functions.countRow(Browser);
		boolean flag = false;

		CheckName: for (int i = 1; i <= r; i++) {
			row = Functions.GetRowData(Browser, i);
			if (row.get(2).contains(evidence.Desc)) {
				flag = true;
			break CheckName;
			}

		}
		
		if (flag == false) {
			pResult.SetResult(false);
			pResult.SetMessage("Evidence " + evidence.Desc + " is not exist");
			return;
		}
		
		F_GeneralVerification.verifyElementValue(Browser, "Author", row.get(4), evidence.Author, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Type", row.get(1), evidence.Type, pResult);
		F_GeneralVerification.verifyDatetime(Browser, "Update on", row.get(5), evidence.Uploadedon, 2, pResult);
	}

	
	public static void verifyIncident(O_Evidence evidence, SeleniumBrowser Browser, TCResult pResult) {
		List<String> row = new ArrayList<>();

		int r = Functions.countRow(Browser);
		boolean flag = false;

		CheckName: for (int i = 1; i <= r; i++) {
			row = Functions.GetRowData(Browser, i);
			if (row.get(2).contains(O_Evidence.Desc)) {
				flag = true;
			break CheckName;
			}

		}
	}
	
	
	public static void verifyCatelogExist(SeleniumBrowser Browser, String categoryName, TCResult pResult) {
		
		WebElement LastestCategories = Browser.Driver.findElement(I_AIMS_Settings.ol_CategoryTree);
		By pcategory = By.xpath("//li/div/span[contains(text(),'" + categoryName + "')]");

		try {
			WebElement newCategory = LastestCategories.findElement(pcategory);
		} catch (Exception e) {
			pResult.SetResult(false);
			pResult.SetMessage("New Category: " + categoryName + " is not displayed");
		}
	}
	
	
	public static void verifyMandataryFieldsError(SeleniumBrowser Browser, String fieldName, By pBy, TCResult pResult) {
		WebElement fieldEle = Browser.captureInterface(pBy);
		if(!fieldEle.getAttribute("class").contains("has-error"))
		{
			pResult.SetResult(false);
			pResult.SetMessage("Field: " + fieldName + " does not display the Mandatary error ");
		}
	}
	
	
	
}
