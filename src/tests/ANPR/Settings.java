package tests.ANPR;

//import java.util.List;
import java.awt.List;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import interfaces.I_Global_Common;
import interfaces.I_Global_Settings;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.TestConfig.Owner;
import libraries.TestConfig.TestSuite;
import libraries.ValueList.LocalInfo;
import libraries.ValueList;
import libraries.generalFunctions.Functions;
import libraries.objects.O_Blacklist;
import libraries.objects.O_NumberPlate;
import libraries.productFunctions.F_ANPRDashboard;
import libraries.productFunctions.F_Navigation;
import libraries.productFunctions.F_ANPRSettings;
import libraries.verificationFunctions.F_ANPRVerification;
import libraries.verificationFunctions.F_GeneralVerification;
import tests.DefaultAnnotations;

public class Settings extends DefaultAnnotations {

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Add_New_ParkingSite() {
		String title_Homepage = "Parking Site Management";
		String pSName = Functions.randomString("PS_", "");
		String cap = Functions.randomNumber(false);

		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_ANPRSettings.goToPage.ParkingSite(Browser);

		// Add new parking Site
		F_ANPRSettings.AddNew(Browser);
		Functions.waitForSeconds(15);
		F_ANPRSettings.InputName(Browser, pSName);
		Browser.enter(I_Global_Settings.ParkingSite.txt_capacity, cap);
		Browser.click(I_Global_Settings.ParkingSite.btn_Marker);
		Browser.click(I_Global_Settings.ParkingSite.div_mapArea);
		F_ANPRSettings.selectMapArea(Browser, I_Global_Settings.ParkingSite.div_mapArea);
		F_ANPRSettings.Save(Browser);

		// Verify Result
		F_ANPRVerification.verifyParkingSite(Browser, pSName, cap, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(dependsOnMethods = { "Add_New_ParkingSite" }, groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Edit_ParkingSite() {

		String pSName_U = Functions.randomString("PS_", "");
		String cap_U = Functions.randomNumber(false);

		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_ANPRSettings.goToPage.ParkingSite(Browser);

		F_GeneralVerification.verifyElementNotEnabled(Browser, "Edit Button", I_Global_Settings.btn_Edit, Result);
		Browser.click(I_Global_Settings.ParkingSite.tr_LastRow);
		F_GeneralVerification.verifyElementEnabled(Browser, "Edit Button", I_Global_Settings.btn_Edit, Result);

		Browser.click(I_Global_Settings.btn_Edit);
		// Update information
		String initName = Browser.captureInterface(I_Global_Settings.txt_Name).getAttribute("value");
		String initCap = Browser.captureInterface(I_Global_Settings.ParkingSite.txt_capacity).getAttribute("value");

		Browser.enter(I_Global_Settings.txt_Name, pSName_U);
		Browser.enter(I_Global_Settings.ParkingSite.txt_capacity, cap_U);
		Browser.click(I_Global_Settings.btn_SaveEdit);

		// Go back the Table
		F_ANPRSettings.closeEditTab(Browser);
		F_ANPRSettings.goToPage.ParkingSite(Browser);

		F_ANPRVerification.verifyParkingSite(Browser, pSName_U, cap_U, Result);	Assert.assertTrue(Result.Result, Result.Message);

		Assert.assertTrue(Result.Result, Result.Message);
		// --- Update back
		if (initName != null && initCap != null) {
			int rowIndex = F_ANPRSettings.getRowIndex(Browser, pSName_U);
			F_ANPRSettings.selectRow(Browser, rowIndex);

			Browser.click(I_Global_Settings.btn_Edit);
			Browser.enter(I_Global_Settings.txt_Name, initName);
			Browser.enter(I_Global_Settings.ParkingSite.txt_capacity, initCap);
			Browser.click(I_Global_Settings.btn_SaveEdit);
		}

	}

	@Test(dependsOnMethods = { "Edit_ParkingSite" }, groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Add_New_Gateway() {

		String gWName = Functions.randomString("GW ", "");
		String streetName = Functions.randomNumber(false) + " " + Functions.randomText();

		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_ANPRSettings.goToPage.GateWay(Browser);

		String pSite = F_ANPRSettings.addNewGateWay(Browser, gWName, streetName);

		F_ANPRVerification.verifyGateWay(Browser, gWName, "Entrance", pSite, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Edit_Gateway() {

		String gWName = Functions.randomString("GW ", "");
		String streetName = Functions.randomNumber(false) + " " + Functions.randomText();

		String gWName_u = Functions.randomString("GW_u ", "");

		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_ANPRSettings.goToPage.GateWay(Browser);

		F_ANPRSettings.addNewGateWay(Browser, gWName, streetName);
		F_ANPRSettings.selectRow(Browser, gWName);
		F_GeneralVerification.verifyElementEnabled(Browser, "Edit Button", I_Global_Settings.btn_Edit, Result);

		// Update information
		Browser.click(I_Global_Settings.btn_Edit);
		Browser.enter(I_Global_Settings.txt_Name, gWName_u);
		Browser.click(I_Global_Settings.GateWay.Sel_ParkingSiteDD);
		String pSite = Browser.selectRandomDropdown(I_Global_Settings.GateWay.Sel_ParkingSiteDD);

		Browser.click(I_Global_Settings.btn_SaveEdit);

		// Go back the Table
		F_ANPRSettings.closeEditTab(Browser);
		F_ANPRSettings.goToPage.GateWay(Browser);

		F_ANPRVerification.verifyGateWay(Browser, gWName_u, "Entrance", pSite, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(dependsOnMethods = { "Add_New_Gateway" }, groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Add_New_Camera() {

		String cName = Functions.randomString("CAM ", "");
		String ipAdd = Functions.randomIpAddress();
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_ANPRSettings.goToPage.Camera(Browser);

		// Add new Camera
		String pGWay = F_ANPRSettings.addNewCamera(Browser, cName, ipAdd);

		F_ANPRVerification.verifyCamera(Browser, cName, ipAdd, pGWay, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(dependsOnMethods = { "Add_New_Camera" }, groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Edit_Camera() {

		String cName = Functions.randomString("CAM ", "");
		String ipAdd = Functions.randomIpAddress();

		String cName_u = Functions.randomString("Cam ", "");
		String ipAdd_u = Functions.randomIpAddress();

		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_ANPRSettings.goToPage.Camera(Browser);

		String pGWay = F_ANPRSettings.addNewCamera(Browser, cName, ipAdd);

		F_GeneralVerification.verifyElementNotEnabled(Browser, "Edit Button", I_Global_Settings.btn_Edit, Result);
		F_ANPRSettings.selectRow(Browser, cName);
		F_GeneralVerification.verifyElementEnabled(Browser, "Edit Button", I_Global_Settings.btn_Edit, Result);

		// Update information
		Browser.click(I_Global_Settings.btn_Edit);
		Browser.enter(I_Global_Settings.txt_Name, cName_u);
		Browser.enter(I_Global_Settings.Camera.txt_IpAddress, ipAdd_u);
		String pGWay_u = Browser.selectRandomDropdown(I_Global_Settings.Camera.Sel_GatewayDD);
		F_ANPRSettings.saveEdit(Browser);

		// Go back the Table
		F_ANPRSettings.closeEditTab(Browser);
		F_ANPRSettings.goToPage.Camera(Browser);

		F_ANPRVerification.verifyCamera(Browser, cName_u, ipAdd_u, pGWay_u, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Create_Regular_driver_profile() {

		String driverName = Functions.randomFullName();
		String licenseType = Functions.randomText(3);
		String licenseId = Functions.randomString("", "");
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_ANPRSettings.goToPage.DriverProfile(Browser);

		F_ANPRSettings.AddNew(Browser);
		F_ANPRSettings.InputName(Browser, driverName);
		Browser.enter(I_Global_Settings.DriverProfile.txt_LicenseType, licenseType);
		Browser.enter(I_Global_Settings.DriverProfile.txt_LicenseId, licenseId);
		F_ANPRSettings.Save(Browser);

		// Verify Result
		F_ANPRVerification.verifyDriver(Browser, driverName, licenseType, licenseId, Result);

		Assert.assertTrue(Result.Result, Result.Message);

	}

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Edit_Regular_driver_profile() {

		String driverName = Functions.randomFullName();
		String licenseType = Functions.randomText(3);
		String licenseId = Functions.randomString("", "");
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_ANPRSettings.goToPage.DriverProfile(Browser);

		F_ANPRSettings.updateDriverProfile(Browser, 1, driverName, licenseType, licenseId);

		F_Navigation.RefreshPage(Browser);

		F_ANPRSettings.goToPage.DriverProfile(Browser);

		// Verify Results
		F_ANPRVerification.verifyDriver(Browser, driverName, licenseType, licenseId, Result);
		Assert.assertTrue(Result.Result, Result.Message);

	}

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Add_New_Regular_For_new_Driver() {
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_ANPRSettings.goToPage.NumnberPlates(Browser);

		O_NumberPlate numplate = new O_NumberPlate();
		numplate.Init();
		F_ANPRSettings.AddNewRegularNumberPlate(Browser, numplate, true);

		F_ANPRVerification.verifyNumberPlate(Browser, numplate, Result);
		F_ANPRVerification.verifyNumberPlateDetails(Browser, numplate, Result, true);

		Assert.assertTrue(Result.Result, Result.Message);

	}

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Add_New_Visitor() {
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_ANPRSettings.goToPage.NumnberPlates(Browser);

		O_NumberPlate numplate = new O_NumberPlate();
		numplate.Init();
		F_ANPRSettings.AddNewVisitorNumberPlate(Browser, numplate);
		F_ANPRVerification.verifyNumberPlate(Browser, numplate, Result);
		F_ANPRVerification.verifyNumberPlateDetails(Browser, numplate, Result, false);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Add_New_Regular_For_existing_Driver() {
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		O_NumberPlate numplate = new O_NumberPlate();
		numplate.Init();

		F_ANPRSettings.goToPage.DriverProfile(Browser);

		F_ANPRSettings.AddNew(Browser);
		F_ANPRSettings.InputName(Browser, numplate.DriverName);
		Browser.enter(I_Global_Settings.DriverProfile.txt_LicenseType, numplate.LicenseType);
		Browser.enter(I_Global_Settings.DriverProfile.txt_LicenseId, numplate.LicenseID);
		F_ANPRSettings.Save(Browser);

		F_ANPRSettings.goToPage.NumnberPlates(Browser);
		F_ANPRSettings.AddNewRegularNumberPlate(Browser, numplate, false);
		F_ANPRVerification.verifyNumberPlate(Browser, numplate, Result);
		F_ANPRVerification.verifyNumberPlateDetails(Browser, numplate, Result, true);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Add_New_Regular_for_new_Driver_WithOut_Whitelist_Selection() {
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_ANPRSettings.goToPage.NumnberPlates(Browser);

		O_NumberPlate numplate = new O_NumberPlate();
		numplate.Init();
		numplate.Whitelist = "";
		numplate.ParkingSite = "";
		numplate.TimeProfile = "";

		F_ANPRSettings.AddNewRegularNumberPlate(Browser, numplate, true);
		F_ANPRVerification.verifyNumberPlate(Browser, numplate, Result);
		F_ANPRVerification.verifyNumberPlateDetails(Browser, numplate, Result, true);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Add_New_Blacklist() {
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_ANPRSettings.goToPage.Blacklist(Browser);

		O_Blacklist numplate = new O_Blacklist();
		numplate.Init();
		F_ANPRSettings.AddBlacklist(Browser, numplate, true, 2);

		List rowEle = Browser.findRowElements(numplate.NumberPlate);
		String actualNumPlate = rowEle.getItem(0);
		String actualStartDate = rowEle.getItem(1);
		String actualEndDate = rowEle.getItem(2);

		F_GeneralVerification.verifyElementValue(Browser, "Number Plate", actualNumPlate, numplate.NumberPlate, Result);
		F_GeneralVerification.verifyElementValue(Browser, "Start Date", actualStartDate,
				numplate.Period.split(" - ")[0], Result);
		F_GeneralVerification.verifyElementValue(Browser, "End Date", actualEndDate, numplate.Period.split(" - ")[1],
				Result);

		int rowIndex = F_ANPRSettings.getRowIndex(Browser, numplate.NumberPlate);

		F_ANPRSettings.selectRow(Browser, rowIndex);
		F_ANPRSettings.openDetails(Browser);
		F_ANPRVerification.verifyBlacklistDetails(Browser, numplate, rowIndex, Result);

		Functions.waitForSeconds(5);

		// Verify at Local page
		F_Navigation.loginLocal(Browser, Constants.LocalUsername, Constants.LocalUserPass);
		// F_Navigation.goToPage(Browser, I_Global_Common.Tab_Dashboard);

		// F_Dashboard.clickSynCenter(Browser);
		F_ANPRVerification.verifyBlacklistAtLocal(Browser, numplate, Result, true);
		F_ANPRDashboard.createANPREvent(numplate.NumberPlate, true);
		String expectedMsg = MessageFormat.format(ValueList.Message.InBlacklist, numplate.NumberPlate);

		F_ANPRVerification.verifyANPRMessage(Browser, expectedMsg, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(dependsOnMethods = { "Add_New_Blacklist" }, groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Delete_A_Blacklist() {
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
		F_ANPRSettings.goToPage.Blacklist(Browser);

		O_Blacklist numplate = new O_Blacklist();
		numplate.Init();
		F_ANPRSettings.AddBlacklist(Browser, numplate, true, 2);

		// Verify at Local page
		F_Navigation.loginLocal(Browser, Constants.LocalUsername, Constants.LocalUserPass);
		F_ANPRDashboard.createANPREvent(numplate.NumberPlate, true);
		String expectedMsg = MessageFormat.format(ValueList.Message.InBlacklist, numplate.NumberPlate);
		F_ANPRVerification.verifyANPRMessage(Browser, expectedMsg, Result);

		// Delete the blacklist
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_ANPRSettings.DeleteBlacklist(Browser, numplate);
		// F_GeneralVerification.verifyValueNotExist(Browser, "Blacklist",
		// numplate.NumberPlate, Result);

		F_Navigation.loginLocal(Browser, Constants.LocalUsername, Constants.LocalUserPass);
		F_ANPRVerification.verifyBlacklistAtLocal(Browser, numplate, Result, true);

		F_ANPRDashboard.createANPREvent(numplate.NumberPlate, true);
		expectedMsg = MessageFormat.format(ValueList.Message.Unregister, numplate.NumberPlate);

		F_ANPRVerification.verifyANPRMessage(Browser, expectedMsg, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Add_New_Time_Profile() {

		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_ANPRSettings.goToPage.TimeProfile(Browser);
		String tPName = F_ANPRSettings.addTimeProfile(Browser, 2, 3);

		// Verify Result
		List rowEle = Browser.findRowElements(tPName);
		String actualName = rowEle.getItem(0);

		F_GeneralVerification.verifyElementValue(Browser, "Time Profile Name", actualName, tPName, Result);

		Assert.assertTrue(Result.Result, Result.Message);

	}

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Edit_Time_Profile() {
		int dayIndex = Functions.randomInterger(2, 8);
		int hour = Functions.randomInterger(1, 24);

		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
		F_ANPRSettings.goToPage.TimeProfile(Browser);

		String tPName = F_ANPRSettings.addTimeProfile(Browser, dayIndex, hour);

		F_ANPRSettings.selectRow(Browser, tPName);
		
		// Update information
		String tPName_U = Functions.randomString("Time Profile_", "");
		Browser.click(I_Global_Settings.btn_Edit);
		F_ANPRVerification.verifyTimeProfile(Browser, dayIndex, hour, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		dayIndex = Functions.randomInterger(2, 8);
		hour = Functions.randomInterger(1, 24);
		Browser.enter(I_Global_Settings.txt_Name, tPName_U);
		F_ANPRSettings.SelectTimeFrame(Browser, dayIndex, hour);

		F_ANPRSettings.saveEdit(Browser);
		F_ANPRSettings.closeEditTab(Browser);

		// Go back the Table
		F_ANPRSettings.goToPage.TimeProfile(Browser);

		// Verify table
		List rowEle = Browser.findRowElements(tPName_U);
		String actualName = rowEle.getItem(0);
		F_GeneralVerification.verifyElementValue(Browser, "Time Profile Name", actualName, tPName_U, Result);
		F_ANPRSettings.selectRow(Browser, tPName_U);
		F_ANPRSettings.clickEdit(Browser);
		F_ANPRVerification.verifyTimeProfile(Browser, dayIndex, hour, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	// @Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Verify_Time_Profile_affect() {
		int dayIndex = 2;
		int hour = 1;
		String time = "";
		String parkingSite = LocalInfo.ParkingSite;
		String timeProfile = "AutoTimeProfile";
		String currentWindow = Browser.Driver.getWindowHandle();
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);

		for (dayIndex = 2; dayIndex <= 4; dayIndex++) {
			for (hour = 1; hour <= 5; hour++) {
				// Add a visitor
				F_ANPRSettings.goToPage.NumnberPlates(Browser);
				O_NumberPlate newVisitor = new O_NumberPlate();
				newVisitor.Init();
				newVisitor.TimeProfile = timeProfile;
				F_ANPRSettings.AddNewVisitorNumberPlate(Browser, newVisitor);

				// ClearTimeProfile
				F_ANPRSettings.ClearTimeProfile(Browser, timeProfile);
				F_Navigation.goToPage(Browser, I_Global_Common.Tab_Dashboard);

				// Create ANPR event
				time = Functions.getTimeStamp(dayIndex, hour);
				String IN_msg = F_ANPRDashboard.getANPRMessage(newVisitor.NumberPlate, time, LocalInfo.CamIN, "IN");
				F_ANPRDashboard.TriggerSimulator(IN_msg);

				// Check in Dashboard
				String expectedMsg = MessageFormat.format(ValueList.Message.OutTimeFrame, newVisitor.NumberPlate);
				F_ANPRVerification.verifyANPRMessage(Browser, expectedMsg, Result);
				
				if (Result.Result == false) break;

				// Edit TimeProfile
				F_ANPRSettings.UpdateTimeProfile(Browser, timeProfile, dayIndex, hour, Result);

				F_Navigation.goToPage(Browser, I_Global_Common.Tab_Dashboard);

				// Create ANPR even with new Visitor
				time = Functions.getTimeStamp(dayIndex, hour);
				IN_msg = F_ANPRDashboard.getANPRMessage(newVisitor.NumberPlate, time, LocalInfo.CamIN, "IN");
				F_ANPRDashboard.TriggerSimulator(IN_msg);

				// Check in Dashboard
				Browser.Driver.switchTo().window(currentWindow);

				expectedMsg = MessageFormat.format(ValueList.Message.Accepted, newVisitor.NumberPlate);
				F_ANPRVerification.verifyANPRMessage(Browser, expectedMsg, Result);
			}
		}

		Assert.assertTrue(Result.Result, Result.Message);
	}

}