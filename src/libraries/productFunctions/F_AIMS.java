package libraries.productFunctions;

import java.util.List;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import interfaces.I_AIMS_Home;
import interfaces.I_AIMS_Incident_Details;
import interfaces.I_AIMS_Settings;
import interfaces.I_Dashboard;
import interfaces.I_Global_Common;
//import executionEngine.RunTestscript;
import interfaces.I_Global_Settings;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.ValueList.Incident;
import libraries.ValueList.LocalInfo;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Log;
import libraries.objects.O_ANPREvent;
import libraries.objects.O_Blacklist;
import libraries.objects.O_Evidence;
import libraries.objects.O_ExportLog;
import libraries.objects.O_NumberPlate;

public class F_AIMS {

	public static void closeCurrentTab(SeleniumBrowser Browser) {
		Browser.clickJavascript(I_AIMS_Home.btn_CloseCurrentTab);
		// Browser.click(I_AIMS_Settings.btn_CloseCurrentTab);
		Functions.waitForSeconds(1);
	}

	public static void clickAddIncident(SeleniumBrowser Browser) {
		// Browser.clickJavascript(I_AIMS_Home.btn_AddIncident);

		// Browser.click(I_AIMS_Home.btn_AddIncident);
		Browser.click(I_AIMS_Home.btn_addIncidentOnToolbar);

		Browser.waitForElementVisible(I_AIMS_Incident_Details.btn_closeActiveTab);
		Functions.waitForSeconds(2);
	}

	public static void FillBasicInfo(SeleniumBrowser Browser) {

		Browser.click(I_AIMS_Incident_Details.div_Officer);
		Browser.waitForElementVisible(I_AIMS_Incident_Details.ddl_Officer);
		selectDropDown_AIMS(Browser, I_AIMS_Incident_Details.ddl_Officer);
		Browser.waitForElementNotVisible(I_AIMS_Incident_Details.ddl_Officer);

		Browser.click(I_AIMS_Incident_Details.txt_IncidentType);
		// Browser.selectDropdownByText(I_AIMS_Incident_Details.ddl_IncidentType,
		// Incident.IncidentType);
		Browser.waitForElementVisible(I_AIMS_Incident_Details.div_IncidentType);

		selectRandomIncidentType(Browser, I_AIMS_Incident_Details.div_IncidentType);

		// Browser.click(I_AIMS_Incident_Details.btn_pickerDate);

		Browser.enter(I_AIMS_Incident_Details.txt_desc, Functions.randomText(20));

		// Caller
		Browser.enter(I_AIMS_Incident_Details.txt_firstName, Functions.randomText(6));
		Browser.enter(I_AIMS_Incident_Details.txt_lastName, Functions.randomText(6));

		// Browser.enter(I_AIMS_Incident_Details.txt_callCity, Incident.city);
		// Browser.enter(I_AIMS_Incident_Details.txt_callzipCode, Incident.zipCode);
		Browser.enter(I_AIMS_Incident_Details.txt_callPhone, Functions.randomNumberString(10));

		scrollToEndOfDetailsPanel(Browser);

		// Incident Location
		selectMap(Browser);
		// Browser.enter(I_AIMS_Incident_Details.txt_incidentCity, Incident.city);
		// Browser.enter(I_AIMS_Incident_Details.txt_incidentzipCode, Incident.zipCode);

		FillCustomFields(Browser);
	}

	public static void selectMap(SeleniumBrowser Browser) {
		Browser.click(I_AIMS_Incident_Details.btn_zoomOutmap);
		Functions.waitForSeconds(1);
		Browser.click(I_AIMS_Incident_Details.btn_zoomOutmap);
		Functions.waitForSeconds(1);
		Browser.click(I_AIMS_Incident_Details.btn_selMapIcon);

		selectMapArea(Browser, I_AIMS_Incident_Details.btn_selMapIcon);
		// selectMapArea(Browser, I_AIMS_Incident_Details.incident_maps);

		Functions.waitForSeconds(2);
	}

	public static void selectMapArea(SeleniumBrowser Browser, By locator) {
		try {

			Actions action = new Actions(Browser.Driver);
			WebElement element = Browser.captureInterface(locator);

			action.moveToElement(element, Functions.randomInterger(0, 200), Functions.randomInterger(0, 200));

			// action.moveByOffset(900, 600);

			// action.wait(3);
			action.click();
			action.build().perform();

		} catch (Exception e) {

		}

	}

	public static void SaveIncident(SeleniumBrowser Browser) {
		Browser.click(I_AIMS_Incident_Details.btn_Save);
		Functions.waitForSeconds(2);
	}

	public static void CloseIncident(SeleniumBrowser Browser) {

		// Browser.waitForElementNotVisible(I_AIMS_Incident_Details.popup_Messages);
		// Functions.waitForSeconds(2);
		// Browser.click(I_AIMS_Incident_Details.btn_closeActiveTab);
		Browser.clickJavascript(I_AIMS_Incident_Details.btn_closeActiveTab);

		Functions.waitForSeconds(1);
	}

	public static void FillCustomFields(SeleniumBrowser Browser) {

		List<WebElement> inputList = Browser.Driver
				.findElements(By.xpath("//fieldset[@id='fieldsetCustomFields']/div//input"));

		for (WebElement input : inputList) {

			// WebElement input = cEle.get(i).findElement(By.xpath("//input"));

			if (input.getAttribute("type").contains("text") && input.getAttribute("ng-if") != null) {
				Browser.scrollToEle(input);
				input.sendKeys(Functions.randomText(15));
			}

			if (input.getAttribute("type").contains("number")) {
				Browser.scrollToEle(input);
				input.sendKeys(Functions.randomNumberString(10));
			}

			if (input.getAttribute("type").contains("text") && input.getAttribute("ng-if") == null) {

				if (input.getAttribute("options") != ""
						&& input.getAttribute("options").contains("dateOnlyPickerOptions")) {

					Browser.scrollToEle(input);
					input.sendKeys(Functions.getCurrentDate());

				} else {

					Browser.scrollToEle(input);
					input.sendKeys(Functions.getCurrentDateTime());
				}
			}

		}

	}

	public static String selectDropDown_AIMS(SeleniumBrowser Browser, By pBy_li) {

		// Select mSelect = new Select(Browser.Driver.findElement(pBy_li));

		WebElement ddList = Browser.captureInterface(pBy_li);

		int count = ddList.findElements(By.xpath("div")).size();
		int s = Functions.randomInterger(3, count);

		WebElement item = ddList.findElement(By.xpath("div[" + s + "]"));
		String name = item.getText();

		Browser.scrollToEle(item);
		item.click();
		Functions.waitForSeconds(1);
		
		return name;
	}

	public static String selectRandomIncidentType(SeleniumBrowser Browser, By pBy_div) {

		WebElement ddList = Browser.captureInterface(pBy_div);
		int Groupcount = ddList.findElements(By.xpath("li")).size();

		int i = Functions.randomInterger(1, Groupcount);
		WebElement Group = ddList.findElement(By.xpath("li[" + i + "]"));

		int count = Group.findElements(By.xpath("div")).size();
		int s = Functions.randomInterger(3, count);

		WebElement item = Group.findElement(By.xpath("div[" + s + "]"));
		String name = item.getText();

		Browser.scrollToEle(item);
		// Functions.waitForSeconds(1);
		item.click();

		return name;
	}

	public static void scrollToEndOfDetailsPanel(SeleniumBrowser Browser) {

		Browser.scrollToEle(I_AIMS_Incident_Details.pnl_CustomFields);
	}

	public static void DeclineWarning(SeleniumBrowser Browser) {

		WebElement wElement = Browser.captureInterface(I_AIMS_Incident_Details.btn_DeclineWwarning);
		if (wElement != null) {
			// Browser.waitForElementVisible(I_AIMS_Incident_Details.btn_DeclineWwarning);
			// Browser.click(I_AIMS_Incident_Details.btn_DeclineWwarning);

			Browser.clickJavascript(I_AIMS_Incident_Details.btn_DeclineWwarning);

		}
	}

	public static void AddNewIncident(SeleniumBrowser Browser, String name, String code) {

		F_Navigation.goToPage(Browser, I_AIMS_Settings.tab_IncidentTypes);
		Browser.click(I_AIMS_Settings.btn_addNewType);
		Browser.enter(I_AIMS_Settings.txt_InputName, name);
		Browser.enter(I_AIMS_Settings.txt_incidentCode, code);
		Browser.click(I_AIMS_Settings.btn_Save);
	}

	public static void ImportIncidentType(SeleniumBrowser Browser) {
		String path =  Constants.ProjectURL + "\\src\\Test Data\\IncidentTypes.csv";
		List<String> list = Functions.ReadCSV_Scanner(path);
		F_Navigation.goToPage(Browser, I_AIMS_Home.tab_Settings);
		F_Navigation.goToPage(Browser, I_AIMS_Settings.tab_IncidentTypes);

		String incident = "";

		for (int i = 0; i < list.size(); i++) {

			incident = list.get(i);
			if (incident != "") {
				Browser.click(I_AIMS_Settings.btn_addNewType);
				Browser.enter(I_AIMS_Settings.txt_InputName, incident);
				Browser.enter(I_AIMS_Settings.txt_incidentCode, Functions.randomNumberString(5));
				Browser.click(I_AIMS_Settings.btn_Save);
				Functions.waitForSeconds(3);
				Log.info("Incident type addded success = " + incident);
			}
		}

	}

	/**
	 * Open incident
	 * 
	 * @param Browser
	 * @param ReferId
	 */
	public static void OpenIncident(SeleniumBrowser Browser, String ReferId) {

		String temp_r = "//table[@id='inbox-list']/tbody/tr/td[contains(text(),'" + ReferId + "')]";

		WebElement IncidentEle = Browser.Driver.findElement(By.xpath(temp_r));

		IncidentEle.click();
		Actions action = new Actions(Browser.Driver);
		action.moveToElement(IncidentEle).doubleClick().perform();

	}

	public static void OpenLatestIncident(SeleniumBrowser Browser) {

		String temp_r = "//table[@id='inbox-list']/tbody/tr[1]/td[2]";

		WebElement IncidentEle = Browser.Driver.findElement(By.xpath(temp_r));

		IncidentEle.click();
		Actions action = new Actions(Browser.Driver);
		action.moveToElement(IncidentEle).doubleClick().perform();
		Functions.waitForSeconds(1);
	}

	public static String InputEvidenceMetadata(SeleniumBrowser Browser, boolean IsUpdateDesc) {

		String desc = "";

		if (IsUpdateDesc == true) {
			desc = Functions.randomText(9);
			Browser.enter(I_AIMS_Incident_Details.Evidence.txt_Desc, desc);
		} else
			desc = Browser.captureInterface(I_AIMS_Incident_Details.Evidence.txt_Desc).getAttribute("value");

		// Browser.enter(I_AIMS_Incident_Details.Evidence.txt_Street,
		// Functions.randomText(10));
		// Browser.enter(I_AIMS_Incident_Details.Evidence.txt_City,
		// Functions.randomText(6));
		// Browser.enter(I_AIMS_Incident_Details.Evidence.txt_ZipCode,
		// Functions.randomNumberString(6));

		Browser.click(I_AIMS_Incident_Details.Evidence.dd_Criticality);

		selectDropDown_AIMS(Browser, I_AIMS_Incident_Details.Evidence.li_Criticality);

		selectLocationForEvidence(Browser);

		// Functions.waitForSeconds(1);
		// Browser.click(I_AIMS_Incident_Details.Evidence.lbl_mapInfo);
		Functions.waitForSeconds(1);

		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Save);
		Functions.waitForSeconds(2);

		return desc;
	}

	public static void selectLocationForEvidence(SeleniumBrowser Browser) {
		
		Browser.waitForElementEnabled(I_AIMS_Incident_Details.Evidence.btn_zoomOut);
		//Browser.waitAndClick(I_AIMS_Incident_Details.Evidence.btn_zoomOut);
		Browser.clickJavascript(I_AIMS_Incident_Details.Evidence.btn_zoomOut);
		// Browser.click(I_AIMS_Incident_Details.Evidence.btn_zoomOut);
		Functions.waitForSeconds(1);
		Browser.clickJavascript(I_AIMS_Incident_Details.Evidence.btn_zoomOut);
		//Browser.click(I_AIMS_Incident_Details.Evidence.btn_zoomOut);
		//Functions.waitForSeconds(1);
		//Browser.click(I_AIMS_Incident_Details.Evidence.btn_zoomOut);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_pickCood);

		// Browser.click(I_AIMS_Incident_Details.Evidence.lbl_mapInfo);
		// selectMapArea(Browser, I_AIMS_Incident_Details.Evidence.lbl_mapInfo);
		Functions.waitForSeconds(1);
		int count = 0;
		do {

			Actions action = new Actions(Browser.Driver);
			action.moveToElement(Browser.captureInterface(I_AIMS_Incident_Details.Evidence.btn_pickCood),
					Functions.randomInterger(30, 200), Functions.randomInterger(-100, 100));
			action.click();
			action.build().perform();
			count++;
		} while (!Browser.Driver.findElement(I_AIMS_Incident_Details.Evidence.txt_Coordinates).getAttribute("class")
				.contains("ng-not-empty") && count < 4);

	}

	public static List<String> UploadRandomEvidence(SeleniumBrowser Browser, int numOffiles, boolean IsUpdateDesc) {

		String folderPath = Constants.ProjectURL + "\\data";
		List<String> evidenceList = new ArrayList<>();
		int n = 0;
		n = numOffiles;

		File[] listFile = Functions.getListOfFile(folderPath);
		if (numOffiles == 0)
			n = listFile.length;

		for (int i = 0; i < n; i++) {
			int r = Functions.randomInterger(1, listFile.length - 1);

			if (listFile[r].isFile()) {
				UploadAEvidence(Browser, listFile[r].getName());
				evidenceList.add(listFile[r].getName());
				InputEvidenceMetadata(Browser, IsUpdateDesc);
			}
		}
		return evidenceList;
	}

	/**
	 * Update a file to evidence
	 * 
	 * @param Browser
	 * @param filename
	 */
	public static void UploadAEvidence(SeleniumBrowser Browser, String filename) {

		String file_path = Constants.ProjectURL + "\\src\\Test Data\\Upload Data\\";

		Browser.click(I_AIMS_Incident_Details.Evidence.btn_File);

		Functions.uploadfile(file_path, filename);

	}

	/**
	 * Select an Evidence for Exporting
	 * 
	 * @param Browser
	 * @param incidentName
	 */
	public static void SelectEvidence(SeleniumBrowser Browser, String incidentName, TCResult result) {

		String temp_r = "//table[@id='evidences-list']/tbody/tr/td[contains(text(),'" + incidentName + "')]";

		WebElement IncidentEle = Browser.Driver.findElement(By.xpath(temp_r));

		if (IncidentEle != null)
			IncidentEle.click();
		else {
			result.Result = false;
			result.Message = "The evidence " + incidentName + " does not exist!";
		}
	}

	/**
	 * Select random Evidence for Exporting
	 * 
	 * @param Browser
	 * 
	 */

	public static String SelectRandomEvidence(SeleniumBrowser Browser) {

		List<String> row = new ArrayList<>();
		int r = Functions.countRow(Browser);
		row = Functions.GetRowData(Browser, Functions.randomInterger(1, r));

		String temp_r = "//table[@id='evidences-list']/tbody/tr/td[contains(text(),'" + row.get(2) + "')]";

		WebElement IncidentEle = Browser.Driver.findElement(By.xpath(temp_r));

		IncidentEle.click();
		return row.get(2);
	}

	public static String fillNicheID(SeleniumBrowser Browser) {
		String nicheId = Functions.randomString("NI", "");
		String oofid = Functions.randomString("OOF", "");
		Browser.enter(I_AIMS_Incident_Details.Evidence.txt_NicheId, nicheId);
		Browser.enter(I_AIMS_Incident_Details.Evidence.txt_OOF, oofid);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Exportdialog);

		return nicheId;
	}

	public static void openEvidence(SeleniumBrowser Browser, String name, TCResult result) {

		SelectEvidence(Browser, name, result);

		if (result.Result == true)
			Browser.click(I_AIMS_Incident_Details.Evidence.btn_View);
	}

	public static O_ExportLog getExLog(SeleniumBrowser Browser) {

		O_ExportLog elog = new O_ExportLog();

		return elog;
	}

	public static int getNumberOfEvidence(SeleniumBrowser Browser) {

		String originalEvidencetab = Browser.captureInterface(I_AIMS_Incident_Details.tab_Evidence).getText();
		int itemCount = Integer.parseInt(originalEvidencetab.substring(10, 11));
		return itemCount;
	}

	/**
	 *  Create new Category in AIMS Settings 
	 * @param Browser
	 * @return
	 */
	public static String createNewCategory(SeleniumBrowser Browser) {
		Browser.click(I_AIMS_Settings.btn_addNewCategory);
		WebElement Categories = Browser.Driver.findElement(I_AIMS_Settings.ol_CategoryTree);

		WebElement newdefaultCategory = Categories.findElement(By.xpath("//li[last()]/div/input"));
		String randomName = Functions.randomText();
		newdefaultCategory.clear();
		newdefaultCategory.sendKeys(randomName);
		Browser.click(I_AIMS_Settings.btn_SaveCategory);
		return randomName;
	}

	/**
	 * Delete a category 
	 * @param Browser
	 * @param categoryName
	 */
	public static void deleteCategory(SeleniumBrowser Browser, String categoryName) {
		
		WebElement newCategory = Browser.Driver
				.findElement(By.xpath("//ol/li/div/span[contains(text(),'" + categoryName
						+ "')]/../span[last()]/button[2]"));
		newCategory.click();
	}
	
	/**
	 * 
	 * @param Browser
	 * @param categoryName
	 */
	public static String addnewSubcategory(SeleniumBrowser Browser, String categoryName) {
		WebElement newCategory = Browser.Driver
				.findElement(By.xpath("//div[@id='tree-root-categorized']/ol/li/div/span[contains(text(),'" + categoryName
						+ "')]/../span[last()]/button[1]"));
		newCategory.click();
		
		By ol_Subcategory = By.xpath("//div[@id='tree-root-categorized']/ol/li/div/span[contains(text(),'" + categoryName + "')]/../following-sibling::ol");
		
		WebElement Category = Browser.Driver.findElement(ol_Subcategory);

		WebElement newdefaultCategory = Category.findElement(By.xpath("//li[last()]/div/input"));
		String randomName = Functions.randomText();
		newdefaultCategory.clear();
		newdefaultCategory.sendKeys(randomName);
		Browser.click(I_AIMS_Settings.btn_SaveCategory);
		return randomName;
	}
	
	public static void extendCatItems (SeleniumBrowser Browser, String categoryName) {
		By pbtnExtend = By.xpath("//div[@id='tree-root-categorized']/ol/li/div/span[contains(text(),'" + categoryName
				+ "')]/../span[1]/i");
		WebElement btnExtend = Browser.Driver.findElement(pbtnExtend);
		if (btnExtend.getAttribute("class").contains("right"))
			btnExtend.click();
	}
	
}
