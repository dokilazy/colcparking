package libraries.verificationFunctions;

import java.awt.List;
import java.util.Dictionary;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import interfaces.I_Dashboard;
import interfaces.I_Global_Settings;
import interfaces.I_Global_Settings.ParkingSite;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.objects.O_ANPREvent;
import libraries.objects.O_Blacklist;
import libraries.objects.O_NumberPlate;
import libraries.productFunctions.F_ANPRSettings;

public class F_ANPRVerification {

	public static void verifyParkingSite(SeleniumBrowser Browser, String pSName, String cap, TCResult Result) {
		F_ANPRSettings.selectPageSizeMaximun(Browser);
		List lastRow = Browser.findRowElements(pSName);
		String actualName = lastRow.getItem(1);
		String actualCap = lastRow.getItem(2);
		F_GeneralVerification.verifyElementValue(Browser, "New PS Name", actualName, pSName, Result);
		F_GeneralVerification.verifyElementValue(Browser, "Capacity", actualCap, cap, Result);
	}

	public static void verifyGateWay(SeleniumBrowser Browser, String name, String type, String parkingSite,
			TCResult pResult) {

		F_ANPRSettings.selectPageSizeMaximun(Browser);
		List rowElements = Browser.findRowElements(name);
		F_GeneralVerification.verifyElementValue(Browser, "Gateway Name", rowElements.getItem(0), name, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Type", rowElements.getItem(1), type, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Parking Site", rowElements.getItem(2), parkingSite, pResult);
	}

	public static void verifyDriver(SeleniumBrowser Browser, String driverName, String licenseType, String licenseId,
			TCResult pResult) {

		F_ANPRSettings.selectPageSizeMaximun(Browser);
		List rowElements = Browser.findRowElementsEditInline(driverName);
		F_GeneralVerification.verifyElementValue(Browser, "Driver Name", rowElements.getItem(0), driverName, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "license Type", rowElements.getItem(1), licenseType, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "license Id", rowElements.getItem(2), licenseId, pResult);
	}

	public static void verifyCamera(SeleniumBrowser Browser, String cName, String ipAdd, String pGWay,
			TCResult pResult) {

		F_ANPRSettings.selectPageSizeMaximun(Browser);
		List rowElements = Browser.findRowElements(cName);
		F_GeneralVerification.verifyElementValue(Browser, "Camera Name", rowElements.getItem(0), cName, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Ip Address", rowElements.getItem(1), ipAdd, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Type", rowElements.getItem(2), "ARRIVE", pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Gateway", rowElements.getItem(3), pGWay, pResult);
	}

	/**
	 * Verify New Number Plate
	 * 
	 * @param oNumPlate
	 * 
	 */
	public static void verifyNumberPlate(SeleniumBrowser Browser, O_NumberPlate numplate, TCResult pResult) {

		F_ANPRSettings.selectPageSizeMaximun(Browser);
		List rowElements = Browser.findRowElements(numplate.NumberPlate);

		F_GeneralVerification.verifyElementValue(Browser, "Number Plate", rowElements.getItem(0), numplate.NumberPlate,
				pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Parking site", rowElements.getItem(1), numplate.ParkingSite,
				pResult);
		F_GeneralVerification.verifyElementValue(Browser, "TimeProfile", rowElements.getItem(2), numplate.TimeProfile,
				pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Driver Name", rowElements.getItem(3), numplate.DriverName,
				pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Vehicle Name", rowElements.getItem(4), numplate.VehicleName,
				pResult);
	}

	public static void verifyNumberPlateDetails(SeleniumBrowser Browser, O_NumberPlate numplate, TCResult pResult,
			boolean isRegular) {

		Dictionary<String, String> n_Details = F_ANPRSettings.getDetails(Browser);

		if (isRegular == true) {
			F_GeneralVerification.verifyElementValue(Browser, "Header", n_Details.get("Header"), "Regular Number Plate",
					pResult);
			F_GeneralVerification.verifyElementValue(Browser, "Number Plate", n_Details.get("Number Plate:"),
					numplate.NumberPlate, pResult);
			F_GeneralVerification.verifyElementValue(Browser, "Driver Name", n_Details.get("Driver Name:"),
					numplate.DriverName, pResult);
			F_GeneralVerification.verifyElementValue(Browser, "Vehicle Name", n_Details.get("Vehicle Name:"),
					numplate.VehicleName, pResult);
			F_GeneralVerification.verifyElementValue(Browser, "Vehicle Type", n_Details.get("Vehicle Type:"),
					numplate.VehicleType, pResult);
			F_GeneralVerification.verifyElementValue(Browser, "Vehicle Description",
					n_Details.get("Vehicle Description:"), numplate.VehicleDesc, pResult);
			F_GeneralVerification.verifyElementValue(Browser, "Whitelist", n_Details.get("Whitelist:"),
					numplate.Whitelist, pResult);
		}

		else {
			F_GeneralVerification.verifyElementValue(Browser, "Header", n_Details.get("Header"), "Visitor Number Plate",
					pResult);
			F_GeneralVerification.verifyElementValue(Browser, "Number Plate", n_Details.get("Number Plate:"),
					numplate.NumberPlate, pResult);
			F_GeneralVerification.verifyElementValue(Browser, "Parking Site", n_Details.get("Parking Site:"),
					numplate.ParkingSite, pResult);
			F_GeneralVerification.verifyElementValue(Browser, "Time Profile", n_Details.get("Time Profile:"),
					numplate.TimeProfile, pResult);
			F_GeneralVerification.verifyElementValue(Browser, "Driver Name", n_Details.get("Driver Name:"),
					numplate.DriverName, pResult);
			F_GeneralVerification.verifyElementValue(Browser, "Vehicle Name", n_Details.get("Vehicle Name:"),
					numplate.VehicleName, pResult);
			F_GeneralVerification.verifyElementValue(Browser, "Vehicle Type", n_Details.get("Vehicle Type:"),
					numplate.VehicleType, pResult);
			F_GeneralVerification.verifyElementValue(Browser, "Vehicle Description",
					n_Details.get("Vehicle Description:"), numplate.VehicleDesc, pResult);

		}
	}

	public static void verifyTransactionDetails(SeleniumBrowser Browser, O_ANPREvent event, TCResult pResult) {
		Dictionary<String, String> t_Details = F_ANPRSettings.getDetails(Browser);
		F_GeneralVerification.verifyElementValue(Browser, "Number Plate", t_Details.get("Number Plate:"),
				event.NumberPlate, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Parking Site", t_Details.get("Parking Site:"),
				event.ParkingSite, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Time Profile", t_Details.get("Time Profile:"),
				event.TimeProfile, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Driver Name", t_Details.get("Driver Name:"),
				event.DriverName, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Vehicle Name", t_Details.get("Vehicle Name:"),
				event.VehicleName, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Vehicle Type", t_Details.get("Vehicle Type:"),
				event.VehicleType, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Vehicle Description", t_Details.get("Vehicle Description:"),
				event.VehicleDesc, pResult);
	}

	public static void verifyBlacklistDetails(SeleniumBrowser Browser, O_Blacklist numplate, int rowIndex,
			TCResult pResult) {

		F_ANPRSettings.selectRow(Browser, rowIndex);

		Dictionary<String, String> b_Details = F_ANPRSettings.getDetails(Browser);
		F_GeneralVerification.verifyElementValue(Browser, "Header", b_Details.get("Header"), numplate.NumberPlate,
				pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Number Plate", b_Details.get("Number Plate:"),
				numplate.NumberPlate, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Driver Name", b_Details.get("Driver Name:"),
				numplate.DriverName, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Vehicle Name", b_Details.get("Vehicle Name:"),
				numplate.VehicleName, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Vehicle Type", b_Details.get("Vehicle Type:"),
				numplate.VehicleType, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "license Type", b_Details.get("License Type:"),
				numplate.LicenseType, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "license Id", b_Details.get("License Id:"),
				numplate.LicenseID, pResult);
		F_GeneralVerification.verifyElementValue(Browser, "Period", b_Details.get("Period:"), numplate.Period, pResult);
	}

	public static void verifyANPRMessage(SeleniumBrowser Browser, String pExpectedResult, TCResult pResult) {

		Browser.waitForElementVisible(I_Dashboard.frm_message);

		WebElement msg = Browser.captureInterface(I_Dashboard.lbl_message);
		if (msg != null) {

			String pActualResult = msg.getText();

			if (!pActualResult.contains(pExpectedResult)) {
				pResult.SetResult(false);
				pResult.SetMessage("ANPR message is displayed " + pActualResult + " instead of " + pExpectedResult);
			}
		} else {
			pResult.SetResult(false);
			pResult.SetMessage("ANPR message is not displayed");
		}
	}

	public static void verifyANPRMessageForEventOut(SeleniumBrowser Browser, String pExpectedResult, TCResult pResult) {

		Browser.waitForElementVisible(I_Dashboard.frm_message);

		WebElement msg = Browser.captureInterface(I_Dashboard.lbl_message);
		if (msg != null) {

			String pActualResult = msg.getText();
			if (!pActualResult.contains(pExpectedResult))

			{
				int Actualsecond = Integer
						.parseInt(pActualResult.substring(pActualResult.length() - 2, pActualResult.length()));
				int Expectedsecond = Integer
						.parseInt(pExpectedResult.substring(pExpectedResult.length() - 2, pExpectedResult.length()));
				if (Math.abs(Actualsecond - Expectedsecond) >= 3) {
					pResult.SetResult(false);
					pResult.SetMessage("ANPR message is displayed " + pActualResult + " instead of " + pExpectedResult);
				}

			}
		} else {
			pResult.SetResult(false);
			pResult.SetMessage("ANPR message is not displayed");
		}
	}

	public static void verifyTimeProfile(SeleniumBrowser Browser, int dayIndex, int hour, TCResult pResult) {
		// dayIndex -= 1;
		WebElement hEle = Browser.Driver.findElement(
				By.xpath("//angular-dayparts-minute/div/div/table/tbody/tr[" + dayIndex + "]/td[" + hour + "]"));

		WebElement hh1 = hEle.findElement(By.xpath("table/tbody/tr/td[1]"));
		WebElement hh2 = hEle.findElement(By.xpath("table/tbody/tr/td[2]"));
		if (!hh1.getAttribute("class").contains("selected")) {
			pResult.Result = false;
			pResult.Message = "" + hour + '"' + "is not selected";
		}
		// if (hh2.isSelected() == false) {
		// pResult.Result = false;
		// pResult.Message = "Last half hour of" + '"' + hour + '"' + "is not selected";
		// }

	}

	public static void verifyBlacklistAtLocal(SeleniumBrowser Browser, O_Blacklist numplate, TCResult pResult,
			boolean IsExist) {

		Browser.scrollToEle(By.xpath(
				"//div[contains(@ng-include,'dashboard/blacklists')]/div/div/div[2]/div[3]/div[2]/div/ul/li[last()]"));

		WebElement row;

		if (IsExist == false) {
			try {
				row = Browser.captureInterface(By.xpath(
						"//div[contains(@ng-include,'dashboard/blacklists')]/div/div/div[2]/div[2]/div[2]/table/tbody/tr/td[contains(.,'"
								+ numplate.NumberPlate + "')]/.."));
			} catch (NoSuchElementException e) {
				row = null;
			}

			if (row != null) {
				pResult.SetResult(false);
				pResult.SetMessage("" + numplate + "  is still exist");
			}

		} else {

			boolean p = false;

			do {
				row = Browser.captureInterface(By.xpath(
						"//div[contains(@ng-include,'dashboard/blacklists')]/div/div/div[2]/div[2]/div[2]/table/tbody/tr/td[contains(.,'"
								+ numplate.NumberPlate + "')]/.."));

				if (row != null) {

					p = false;
					int NumOfCol = row.findElements(By.xpath("td")).size();

					List rowElement = new List(NumOfCol, false);
					for (int i = 0; i < NumOfCol; i++) {
						int n = i + 1;
						WebElement col = row.findElement(By.xpath("td[" + n + "]"));
						rowElement.add(col.getText(), i);
					}

					String actualNumPlate = rowElement.getItem(0);
					String startDate = rowElement.getItem(1);
					String endDate = rowElement.getItem(2);
					F_GeneralVerification.verifyElementValue(Browser, "[Local]Number Plate", actualNumPlate,
							numplate.NumberPlate, pResult);
					F_GeneralVerification.verifyElementValue(Browser, "[Local]Start Date", startDate,
							numplate.Period.split(" - ")[0], pResult);
					F_GeneralVerification.verifyElementValue(Browser, "[Local]End Date", endDate,
							numplate.Period.split(" - ")[1], pResult);
				}

				else {
					String btn_Next_att = Browser.captureInterface(By.xpath(
							"//div[contains(@ng-include,'dashboard/blacklists')]/div/div/div[2]/div[3]/div[2]/div/ul/li[last()]"))
							.getAttribute("Class");
					if (btn_Next_att == null || btn_Next_att == "" || !btn_Next_att.contains("disabled")) {
						Browser.click(By.xpath(
								"//div[contains(@ng-include,'dashboard/blacklists')]/div/div/div[2]/div[3]/div[2]/div/ul/li[last()]/a"));
						p = true;
					} else {
						pResult.SetResult(false);
						pResult.SetMessage("" + numplate + "  is not exist");
						p = false;
					}

				}
			} while (p);

		}
	}

}
