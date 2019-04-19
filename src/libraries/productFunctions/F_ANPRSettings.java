package libraries.productFunctions;

import java.util.List;
//import java.awt.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.common.base.Function;

import interfaces.I_CP_Common;
import interfaces.I_Global_Common;
//import executionEngine.RunTestscript;
import interfaces.I_Global_Settings;
//import interfaces.alert;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.generalFunctions.Functions;
import libraries.objects.O_ANPREvent;
import libraries.objects.O_Blacklist;
import libraries.objects.O_NumberPlate;
import libraries.verificationFunctions.F_GeneralVerification;
//import utility.Log;

public class F_ANPRSettings {

	public static class goToPage {

		public static void ParkingSite(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
			F_Navigation.goToPage(Browser, I_Global_Settings.Tab_ParkingSite);
		}

		public static void GateWay(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
			F_Navigation.goToPage(Browser, I_Global_Settings.Tab_Gateway);
		}

		public static void Camera(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
			F_Navigation.goToPage(Browser, I_Global_Settings.Tab_Camera);
		}

		public static void DriverProfile(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
			F_Navigation.goToPage(Browser, I_Global_Settings.Tab_DriverProfile);
		}

		public static void TimeProfile(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
			F_Navigation.goToPage(Browser, I_Global_Settings.Tab_TimeProfile);
		}

		public static void NumnberPlates(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
			F_Navigation.goToPage(Browser, I_Global_Settings.Tab_NumnberPlates);
		}

		public static void Whitelist(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
			F_Navigation.goToPage(Browser, I_Global_Settings.Tab_Whitelist);
		}

		public static void Blacklist(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
			F_Navigation.goToPage(Browser, I_Global_Settings.Tab_Blacklist);
		}

		public static void Transaction(SeleniumBrowser Browser) {
			F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
			F_Navigation.goToPage(Browser, I_Global_Settings.Tab_Transaction);

		}
	}

	public static void AddNew(SeleniumBrowser Browser) {
		Browser.click(I_Global_Settings.btn_Add);
	}

	public static void InputName(SeleniumBrowser Browser, String pValue) {
		Browser.enter(I_Global_Settings.txt_Name, pValue);
	}

	public static void Save(SeleniumBrowser Browser) {

		Browser.click(I_Global_Settings.btn_Save);
		Functions.waitForSeconds(2);
	}

	public static void Cancel(SeleniumBrowser Browser) {
		Browser.click(I_Global_Settings.btn_Cancel);
	}

	public static void clickEdit(SeleniumBrowser Browser) {
		Browser.click(I_Global_Settings.btn_Edit);
		Functions.waitForSeconds(1);
	}

	public static void saveEdit(SeleniumBrowser Browser) {
		Browser.click(I_Global_Settings.btn_SaveEdit);
		Functions.waitForSeconds(2);
	}

	public static void selectPageSize(SeleniumBrowser Browser, String size) {
		Browser.click(I_Global_Common.dd_selectSize);
		Browser.selectDropdownByText(I_Global_Common.dd_selectSize, size);
		Functions.waitForSeconds(0.5);
	}

	public static void selectPageSizeMaximun(SeleniumBrowser Browser) {
		Browser.click(I_Global_Common.dd_selectSize);
		WebElement ddList = Browser.captureInterface(I_Global_Common.dd_selectSize);
		WebElement item = ddList.findElement(By.xpath("option[last()]"));
		Browser.scrollToEle(item);
		item.click();
		Functions.waitForSeconds(1);

		// --- close Dropdown --
		Browser.click(I_Global_Common.dd_selectSize);
	}

	public static void nextPagination(SeleniumBrowser Browser) {
		// int total = countPagination(Browser);
		// int current = getCurrentPagination(Browser);
		// if (current < total) {
		Browser.click(I_Global_Settings.btn_pagination_Next);
		// Functions.waitForSeconds(0.5);
		// }
	}

	public static int getCurrentPagination(SeleniumBrowser Browser) {
		String n = Browser.captureInterface(I_CP_Common.lbl_activepagination).getText().toString();
		return Functions.convertStringToInteger(n);
	}

	public static int countPagination(SeleniumBrowser Browser) {
		WebElement paginationEle = Browser.captureInterface(I_Global_Settings.ul_pagination);
		return paginationEle.findElements(By.tagName("li")).size() - 3;
	}

	public static void selectMapArea(SeleniumBrowser Browser, By locator) {
		try {

			Actions action = new Actions(Browser.Driver);
			// WebElement element = Browser.captureInterface(locator);
			// action.moveToElement(element);

			action.moveByOffset(445, 437);

			action.wait(5);
			action.click();

			action.build().perform();

		} catch (Exception e) {

		}

		/* Get Offset */
		/*
		 * function offset(el) { var rect = el.getBoundingClientRect(), scrollLeft =
		 * window.pageXOffset || document.documentElement.scrollLeft, scrollTop =
		 * window.pageYOffset || document.documentElement.scrollTop; return { top:
		 * rect.top + scrollTop, left: rect.left + scrollLeft } }
		 * 
		 * // example use var div = document.querySelector('#q'); var divOffset =
		 * offset(div); console.log(divOffset.left, divOffset.top);
		 * 
		 */

	}

	public static void closeEditTab(SeleniumBrowser Browser) {
		Browser.click(I_Global_Settings.btn_CloseEdit);

	}

	public static void SelectCameraRow(SeleniumBrowser Browser) {
		String cName = "CAM 18";
		WebElement row = Browser.captureInterface(
				By.xpath("//table[contains(@id,'DataTables_Table')]/tbody/tr/td[starts-with(.,'" + cName + "')]/.."));

	}

	/**
	 * Get First Row of Table in Driver Profile table
	 * 
	 * @param Browser,
	 *            rowIndex, driverName, licenseType, licenseId
	 * 
	 */
	public static void updateDriverProfile(SeleniumBrowser Browser, int rowIndex, String driverName, String licenseType,
			String licenseId) {
		Browser.enter(By.xpath("//table[contains(@id,'DataTables_Table')]/tbody/tr[" + rowIndex + "]/td[1]/form/input"),
				driverName);
		Browser.enter(By.xpath("//table[contains(@id,'DataTables_Table')]/tbody/tr[" + rowIndex + "]/td[2]/form/input"),
				licenseType);
		Browser.enter(By.xpath("//table[contains(@id,'DataTables_Table')]/tbody/tr[" + rowIndex + "]/td[3]/form/input"),
				licenseId);
	}

	public static String addNewCamera(SeleniumBrowser Browser, String cName, String ipAdd) {
		F_ANPRSettings.AddNew(Browser);
		F_ANPRSettings.InputName(Browser, cName);
		Browser.enter(I_Global_Settings.Camera.txt_IpAddress, ipAdd);
		Browser.click(I_Global_Settings.Camera.Sel_GatewayDD);
		String pGWay = Browser.selectRandomDropdownSkipFisrt(I_Global_Settings.Camera.Sel_GatewayDD);
		Browser.click(I_Global_Settings.ParkingSite.btn_Marker);
		Functions.waitForSeconds(2);
		Browser.click(I_Global_Settings.ParkingSite.div_mapArea);
		F_ANPRSettings.selectMapArea(Browser, I_Global_Settings.ParkingSite.div_mapArea);
		F_ANPRSettings.Save(Browser);
		Functions.waitForSeconds(1);
		return pGWay;
	}

	public static String addTimeProfile(SeleniumBrowser Browser, int dayIndex, int hour) {
		F_ANPRSettings.AddNew(Browser);

		String tPName = Functions.randomString("Time Profile_", "");
		F_ANPRSettings.InputName(Browser, tPName);

		// Select Time Frame
		F_ANPRSettings.SelectTimeFrame(Browser, dayIndex, hour);
		F_ANPRSettings.Save(Browser);
		return tPName;
	}

	public static void AddNewVisitorNumberPlate(SeleniumBrowser Browser, O_NumberPlate nPlate) {
		Browser.click(I_Global_Settings.NumberPlates.btn_AddVisitor);
		Functions.waitForSeconds(1);
		Browser.enter(I_Global_Settings.NumberPlates.txt_numberplate, nPlate.NumberPlate);
		Browser.enter(I_Global_Settings.NumberPlates.txt_DriverName, nPlate.DriverName);
		Browser.enter(I_Global_Settings.NumberPlates.txt_VehicleName, nPlate.VehicleName);
		Browser.enter(I_Global_Settings.NumberPlates.txt_VehicleType, nPlate.VehicleType);
		Browser.enter(I_Global_Settings.NumberPlates.txt_Desc, nPlate.VehicleDesc);
		Browser.click(I_Global_Settings.NumberPlates.Sel_ParkingSiteDD);
		Browser.selectDropdownByText(I_Global_Settings.NumberPlates.Sel_ParkingSiteDD, nPlate.ParkingSite);
		Browser.click(I_Global_Settings.NumberPlates.Sel_TimeProfileDD);
		Browser.selectDropdownByText(I_Global_Settings.NumberPlates.Sel_TimeProfileDD, nPlate.TimeProfile);
		Browser.click(I_Global_Settings.NumberPlates.txt_Period);
		Browser.click(I_Global_Settings.NumberPlates.sel_today);
		Browser.click(I_Global_Settings.NumberPlates.sel_endOfCanlendar);
		Browser.click(I_Global_Settings.NumberPlates.btn_Apply);
		Functions.waitForSeconds(2);
		F_ANPRSettings.Save(Browser);
	}

	public static void AddNewRegularNumberPlate(SeleniumBrowser Browser, O_NumberPlate nPlate, Boolean isNew) {

		Browser.click(I_Global_Settings.NumberPlates.btn_AddRegular);
		Browser.enter(I_Global_Settings.NumberPlates.txt_numberplate, nPlate.NumberPlate);

		if (isNew == true) {

			Browser.click(I_Global_Settings.NumberPlates.rd_NewDP);
			Browser.enter(I_Global_Settings.DriverProfile.txt_LicenseType, nPlate.LicenseType);
			Browser.enter(I_Global_Settings.DriverProfile.txt_LicenseId, nPlate.LicenseID);
			Browser.enter(I_Global_Settings.NumberPlates.txt_DriverName, nPlate.DriverName);
			Browser.enter(I_Global_Settings.NumberPlates.txt_VehicleName, nPlate.VehicleName);
			Browser.enter(I_Global_Settings.NumberPlates.txt_VehicleType, nPlate.VehicleType);
			Browser.enter(I_Global_Settings.NumberPlates.txt_Desc, nPlate.VehicleDesc);
		} else {
			// Browser.click(I_Global_Settings.NumberPlates.rd_ExistingDP);
			Browser.click(I_Global_Settings.NumberPlates.Sel_DriverProfileDD);
			Browser.selectDropdownByText(I_Global_Settings.NumberPlates.Sel_DriverProfileDD, nPlate.DriverName);
			Browser.enter(I_Global_Settings.NumberPlates.txt_VehicleName, nPlate.VehicleName);
			Browser.enter(I_Global_Settings.NumberPlates.txt_VehicleType, nPlate.VehicleType);
			Browser.enter(I_Global_Settings.NumberPlates.txt_Desc, nPlate.VehicleDesc);
		}

		if (nPlate.Whitelist != null && nPlate.Whitelist != "") {

			Browser.click(I_Global_Settings.NumberPlates.Sel_WhitelistDD);
			Browser.selectDropdownByText(I_Global_Settings.NumberPlates.Sel_WhitelistDD, nPlate.Whitelist);
		}

		F_ANPRSettings.Save(Browser);
	}

	public static void SelectPeriodInBlacklist(SeleniumBrowser Browser, int durationType) {
		Browser.click(I_Global_Settings.Blacklist.txt_period);
		Browser.waitForElementVisible(I_Global_Settings.Blacklist.div_datepicker);
		switch (durationType) {
		case 1:
			Browser.click(I_Global_Settings.Blacklist.pr_1day);
			break;
		case 2:
			Browser.click(I_Global_Settings.Blacklist.pr_1week);
			break;
		case 3:
			Browser.click(I_Global_Settings.Blacklist.pr_1Month);
			break;
		case 4:
			Browser.click(I_Global_Settings.Blacklist.pr_1Year);
			break;
		case 5:
			Browser.click(I_Global_Settings.Blacklist.pr_custom);
			break;
		}
	}

	public static void AddBlacklist(SeleniumBrowser Browser, O_Blacklist nPlate, Boolean isNew, int PeriodType) {

		Browser.click(I_Global_Settings.btn_Add);

		if (isNew == true) {

			Browser.enter(I_Global_Settings.Blacklist.txt_NumplateName, nPlate.NumberPlate);
			Browser.enter(I_Global_Settings.Blacklist.txt_DriverName, nPlate.DriverName);
			Browser.enter(I_Global_Settings.Blacklist.txt_LicenseType, nPlate.LicenseType);
			Browser.enter(I_Global_Settings.Blacklist.txt_LicenseId, nPlate.LicenseID);
			Browser.enter(I_Global_Settings.Blacklist.txt_VehicleName, nPlate.VehicleName);
			Browser.enter(I_Global_Settings.Blacklist.txt_VehicleType, nPlate.VehicleType);

		} else {
			Browser.enter(I_Global_Settings.Blacklist.txt_NumplateName, nPlate.NumberPlate);
		}
		SelectPeriodInBlacklist(Browser, PeriodType);
		nPlate.Period = Browser.captureInterface(I_Global_Settings.Blacklist.txt_period).getAttribute("value");

		Functions.waitForSeconds(1);
		Browser.click(I_Global_Settings.Blacklist.btn_Save);
		Functions.waitForSeconds(2);
	}

	public static void DeleteBlacklist(SeleniumBrowser Browser, O_Blacklist nPlate) {
		F_ANPRSettings.goToPage.Blacklist(Browser);
		int rowIndex = F_ANPRSettings.getRowIndex(Browser, nPlate.NumberPlate);
		F_ANPRSettings.selectRow(Browser, rowIndex);

		Browser.click(I_Global_Settings.Blacklist.btn_Delete);
		Functions.waitForSeconds(1);

		Browser.waitForElementVisible(I_Global_Settings.Blacklist.dlg_Confirm);
		Browser.click(I_Global_Settings.Blacklist.btn_YesConfirm);
	}

	public static void AddWhitelist(SeleniumBrowser Browser, String whitelist) {
		Browser.click(I_Global_Settings.btn_Add);
		Browser.enter(I_Global_Settings.txt_Name, whitelist);
		F_ANPRSettings.Save(Browser);
	}

	public static void getLastestEventDetails(SeleniumBrowser Browser, O_ANPREvent event) {
		F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
		F_ANPRSettings.goToPage.Transaction(Browser);

	}

	public static O_ANPREvent getTransactionDetails(SeleniumBrowser Browser, String numPlate, String timeStamp) {
		F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
		F_ANPRSettings.goToPage.Transaction(Browser);
		O_ANPREvent event = new O_ANPREvent();

		return event;
	}

	public static void openDetails(SeleniumBrowser Browser) {

		if (Browser.captureInterface(I_Global_Settings.pl_Rightpanel).getAttribute("aria-hidden").contains("true")) {

			Browser.scrollToEle(I_Global_Settings.btn_Details);
			Browser.click(I_Global_Settings.btn_Details);
			// Browser.waitForElementVisible(I_Global_Settings.pl_Rightpanel);
		}
	}

	public static Dictionary<String, String> getDetails(SeleniumBrowser Browser) {

		String lable, value;

		openDetails(Browser);

		Dictionary<String, String> d = new Hashtable<String, String>();

		WebElement details = Browser.captureInterface(I_Global_Settings.div_Rightpanel);
		// int size = details.findElements(By.xpath("div")).size();

		String Name = details.findElement(By.tagName("h4")).getText().toString();
		d.put("Header", Name);

		List<WebElement> ele = details.findElements(By.tagName("label"));
		for (WebElement e : ele)

		{
			lable = e.getText();
			value = e.findElement(By.xpath("../div")).getText();
			d.put(lable, value);
		}
		return d;
	}

	public static void selectRow(SeleniumBrowser Browser, int rowIndex) {
		int rowID = rowIndex + 1;
		Browser.click(By.xpath("//table[contains(@id,'DataTables_Table')]/tbody/tr[" + rowID + "]/td[1]"));
	}

	public static void selectRow(SeleniumBrowser Browser, String SearchValue) {
		int rowID = getRowIndex(Browser, SearchValue) + 1;
		Browser.click(By.xpath("//table[contains(@id,'DataTables_Table')]/tbody/tr[" + rowID + "]/td[1]"));
	}

	public static int getRowIndex(SeleniumBrowser Browser, String SearchValue) {

		List<WebElement> element = Browser.Driver
				.findElements(By.xpath("//table[contains(@id,'DataTables_Table')]/tbody/tr"));

		int row = 0;

		for (WebElement w : element) {

			String elemText = w.getText();

			if (elemText.contains(SearchValue)) {

				break;
			}
			row++;
		}
		return row;
	}

	/**
	 * Select time frame for a time profile at settings
	 * 
	 * @param Browser
	 * @param dayIndex
	 *            >=2 (monday = 2)
	 * @param hour
	 */
	public static void SelectTimeFrame(SeleniumBrowser Browser, int dayIndex, int hour) {

		// List<WebElement> rowElements =
		// Browser.Driver.findElements(By.xpath("//angular-dayparts-minute/div/div/table/tbody/tr"));

		Browser.click(I_Global_Settings.TimeProfile.btn_Clear);
		WebElement hEle = Browser.Driver.findElement(
				By.xpath("//angular-dayparts-minute/div/div/table/tbody/tr[" + dayIndex + "]/td[" + hour + "]"));

		WebElement hh1 = hEle.findElement(By.xpath("table/tbody/tr/td[1]"));
		WebElement hh2 = hEle.findElement(By.xpath("table/tbody/tr/td[2]"));

		if (hh1.isSelected() == false) {
			hh1.click();
			Functions.waitForSeconds(1);
		}
		if (hh2.isSelected() == false) {
			hh2.click();
			Functions.waitForSeconds(1);
		}
	}

	public static void SelectAllTimeFrame(SeleniumBrowser Browser) {
		int dayIndex = 0;

		for (dayIndex = 2; dayIndex <= 8; dayIndex++) {
			WebElement hEle = Browser.Driver
					.findElement(By.xpath("//angular-dayparts-minute/div/div/table/tbody/tr[" + dayIndex + "]/th/a"));
			hEle.click();
		}

	}

	public static void UpdateTimeProfile(SeleniumBrowser Browser, String timeProfile, int dayIndex, int hour,
			TCResult Result) {
		F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
		F_ANPRSettings.goToPage.TimeProfile(Browser);
		int rowIndex = F_ANPRSettings.getRowIndex(Browser, timeProfile);
		F_ANPRSettings.selectRow(Browser, rowIndex);
		Browser.click(I_Global_Settings.btn_Edit);
		F_ANPRSettings.SelectTimeFrame(Browser, dayIndex, hour);
		// F_GeneralVerification.verifyTimeProfile(Browser, dayIndex, hour, Result);
		Browser.click(I_Global_Settings.btn_SaveEdit);
		F_ANPRSettings.closeEditTab(Browser);
	}

	public static void ClearTimeProfile(SeleniumBrowser Browser, String timeProfile) {
		F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
		F_ANPRSettings.goToPage.TimeProfile(Browser);
		int rowIndex = F_ANPRSettings.getRowIndex(Browser, timeProfile);
		F_ANPRSettings.selectRow(Browser, rowIndex);
		Browser.click(I_Global_Settings.btn_Edit);
		Functions.waitForSeconds(1);
		Browser.click(I_Global_Settings.TimeProfile.btn_Clear);
		Browser.click(I_Global_Settings.btn_SaveEdit);
		F_ANPRSettings.closeEditTab(Browser);
	}

	public static void VerifySuccessMessage(SeleniumBrowser Browser, String expectedMessage, TCResult pResult) {

		WebElement pElement = Browser.captureInterface(I_Global_Settings.frm_Success);
		if (!pElement.isDisplayed()) {
			pResult.SetResult(false);
			pResult.SetMessage("Success pop-up does not display!");
		} else {
			String message = pElement.getText();
			F_GeneralVerification.verifyElementValue(Browser, "Success Popup", message, expectedMessage, pResult);
		}

	}

	/**
	 * Get data of a column in table
	 * 
	 * @param Browser
	 * @param column
	 * @return
	 */
	public static List<String> getDataColumn(SeleniumBrowser Browser, int column) {

		List<WebElement> paginationEles = Browser.Driver
				.findElements(By.xpath("//div[@class='dataTables_paginate paging_simple_numbers']/ul/li"));

		int numOfpages = 1;

		if (paginationEles.size() > 3) {
			String s = Browser.Driver
					.findElement(By.xpath("//div[@class='dataTables_paginate paging_simple_numbers']/ul/li["
							+ paginationEles.size() + "-1]/a"))
					.getText();
			numOfpages = Functions.convertStringToInteger(s);
		}

		List<String> value = new ArrayList<>();

		List<WebElement> rows = null;

		for (int i = 0; i < numOfpages; i++) {

			rows = Browser.Driver
					.findElements(By.xpath("//table[contains(@id,'DataTables_Table')]/tbody/tr/td[" + column + "]"));

			for (WebElement e : rows) {
				value.add(e.getText());
			}
			Browser.click(I_Global_Settings.btn_pagination_Next);
			Functions.waitForSeconds(1);
		}
		return value;
	}

	public static List<String> getListNumberPlate(SeleniumBrowser Browser) {
		F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
		F_ANPRSettings.goToPage.NumnberPlates(Browser);
		List<String> nList = getDataColumn(Browser, 1);

		return nList;
	}

	public static List<String> getListNumberPlateCurrentParking(SeleniumBrowser Browser) {
		F_Navigation.loginLocal(Browser, Constants.LocalUsername, Constants.Password);
		F_Navigation.goToPage(Browser, I_Global_Common.Tab_Dashboard);

		List<WebElement> paginationEles = Browser.Driver
				.findElements(By.xpath("//div[@id='DataTables_Table_0_paginate']/ul/li"));
		int numOfpages = 1;
		if (paginationEles.size() > 3) {
			String s = Browser.Driver
					.findElement(By.xpath(
							"//div[@id='DataTables_Table_0_paginate']/ul/li[" + paginationEles.size() + -1 + "]/a"))
					.getText();
			numOfpages = Functions.convertStringToInteger(s);
		}

		List<String> value = new ArrayList<>();

		List<WebElement> rows = null;

		for (int i = 0; i < numOfpages; i++) {

			rows = Browser.Driver.findElements(By.xpath("//table[contains(@id,'DataTables_Table_0')]/tbody/tr/td[1]"));

			for (WebElement e : rows) {
				value.add(e.getText());
			}
			Browser.scrollToEle(I_Global_Settings.btn_pagination_Next);
			Browser.click(I_Global_Settings.btn_pagination_Next);
			Functions.waitForSeconds(1);
		}
		return value;
	}

	public static String addNewGateWay(SeleniumBrowser Browser, String gateWay, String street) {
		F_ANPRSettings.AddNew(Browser);
		F_ANPRSettings.InputName(Browser, gateWay);
		Browser.enter(I_Global_Settings.GateWay.txt_Street, street);
		Browser.click(I_Global_Settings.GateWay.Sel_ParkingSiteDD);
		Functions.waitForSeconds(0.5);

		String pSite = Browser.selectRandomDropdownSkipFisrt(I_Global_Settings.GateWay.Sel_ParkingSiteDD);
		// Browser.selectDropdownByText(I_Global_Settings.GateWay.Sel_ParkingSiteDD,
		// pSite);
		Browser.click(I_Global_Settings.ParkingSite.btn_Marker);
		Functions.waitForSeconds(7);
		Browser.waitForElementVisible(I_Global_Settings.ParkingSite.div_mapArea);
		Browser.click(I_Global_Settings.ParkingSite.div_mapArea);
		F_ANPRSettings.selectMapArea(Browser, I_Global_Settings.ParkingSite.div_mapArea);
		Functions.waitForSeconds(1);
		F_ANPRSettings.Save(Browser);

		return pSite;
	}

}
