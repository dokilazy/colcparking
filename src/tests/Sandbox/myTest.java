package tests.Sandbox;

//import java.awt.List;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.Test;

import interfaces.I_Global_Common;
import interfaces.I_Global_Settings;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.ValueList;
import libraries.ValueList.LocalInfo;
import libraries.TestConfig.Owner;
import libraries.TestConfig.TestSuite;
import libraries.generalFunctions.Functions;
import libraries.objects.O_Blacklist;
import libraries.objects.O_NumberPlate;
import libraries.productFunctions.F_ANPRDashboard;
import libraries.productFunctions.F_Navigation;
import libraries.productFunctions.F_ANPRSettings;
import libraries.verificationFunctions.F_GeneralVerification;
import tests.DefaultAnnotations;

public class myTest extends DefaultAnnotations {

	 @Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Create_ANPK_Event() {
		String currentWindow = Browser.Driver.getWindowHandle();
		// Login to Global
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);

		for (int i = 0; i < 1; i++) {
			F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
			F_ANPRSettings.goToPage.NumnberPlates(Browser);

			// Adding a Number plate
			O_NumberPlate numplate = new O_NumberPlate();
			numplate.Init();
			F_ANPRSettings.AddNewVisitorNumberPlate(Browser, numplate);

			// String numPlate = Functions.randomNumberPlate();
			String timeStamp_in = Functions.randomTimeStamp();
			String camIN =  ValueList.LocalInfo.CamIN;
			String camOUT = ValueList.LocalInfo.CamOUT;
			// Assign Time Profile for new Number Plate

			F_Navigation.goToPage(Browser, I_Global_Common.Tab_Dashboard);

			String IN_msg = F_ANPRDashboard.getANPRMessage(numplate.NumberPlate, timeStamp_in, camIN, "IN");
			// Modify and run the Simulator
			F_ANPRDashboard.TriggerSimulator(IN_msg);
			Functions.waitForSeconds(3);

			String timeStamp_out = Functions.randomTimeStamp();
			// Create OUT event
			String OUT_msg = F_ANPRDashboard.getANPRMessage(numplate.NumberPlate, timeStamp_out, camOUT, "OUT");
			F_ANPRDashboard.TriggerSimulator(OUT_msg);

			Browser.Driver.switchTo().window(currentWindow);
			Functions.waitForSeconds(3);
		}
		// F_Navigation.goToPage(Browser, I_Global_Common.Tab_Dashboard);
		// //Login to Local
		// F_Navigation.loginLocal(Browser, Constants.LocalUsername,
		// Constants.Password);
		// F_Navigation.goToPage(Browser, I_Global_Common.Tab_Dashboard);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Create_ANPK_Event_For_Unregistered_NP() {
		// Login to Global
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_Navigation.goToPage(Browser, I_Global_Common.Tab_Dashboard);
		
		for (int i = 0; i < 1; i++) {
			// Add a Number plate
			O_NumberPlate numplate = new O_NumberPlate();
			numplate.Init();

			// String numPlate = Functions.randomNumberPlate();
			String timeStamp_in = Functions.randomTimeStamp();
			String camIN = ValueList.LocalInfo.CamIN;

			String IN_msg = F_ANPRDashboard.getANPRMessage(numplate.NumberPlate, timeStamp_in, camIN, "IN");
			F_ANPRDashboard.TriggerSimulator(IN_msg);
		}

		Assert.assertTrue(Result.Result, Result.Message);
	}

	
	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Add_New_Visitor() {
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
		F_ANPRSettings.goToPage.NumnberPlates(Browser);

		for (int i = 0; i < 10; i++) {
		O_NumberPlate numplate = new O_NumberPlate();
		numplate.Init();
		F_ANPRSettings.AddNewVisitorNumberPlate(Browser, numplate);
	//	F_GeneralVerification.verifyNumberPlate(Browser, numplate, Result);
	//	F_GeneralVerification.verifyNumberPlateDetails(Browser, numplate, Result, false);
		}
		Assert.assertTrue(Result.Result, Result.Message);
	}
	
		@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Add_Regulars() {

		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
		for (int i = 0; i < 50; i++) {
			F_ANPRSettings.goToPage.NumnberPlates(Browser);

			O_NumberPlate numplate = new O_NumberPlate();
			numplate.Init();
			// Without whitelist
			//numplate.Whitelist = null;
			F_ANPRSettings.AddNewRegularNumberPlate(Browser, numplate, true);
			
		}
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Add_New_Blacklist() {
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
		F_ANPRSettings.goToPage.Blacklist(Browser);
		for (int i = 0; i < 10; i++) {
		O_Blacklist numplate = new O_Blacklist();
		numplate.Init();
		F_ANPRSettings.AddBlacklist(Browser, numplate, true, 2);

		
		}
	}
		
	// Make EVENT IN AND OUT
	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Create_Many_IN_OUT_events() {
		String currentWindow = Browser.Driver.getWindowHandle();
		int loop = 1;
		// Login to Global
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);

		for (int i = 0; i < loop; i++) {

			F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);

			// Add a Number plate
			F_ANPRSettings.goToPage.NumnberPlates(Browser);
			O_NumberPlate numplate = new O_NumberPlate();
			numplate.Init();
			F_ANPRSettings.AddNewVisitorNumberPlate(Browser, numplate);

			String camIN = LocalInfo.CamIN;
			String camOUT = LocalInfo.CamOUT;
			Date eventTime1 = new Date();
			String timeStamp_in = Functions.randomTimeStamp(eventTime1);
			String IN_msg = F_ANPRDashboard.getANPRMessage(numplate.NumberPlate, timeStamp_in, camIN, "IN");

			// Login to Local
			// F_Navigation.loginLocal(Browser, Constants.LocalUsername,
			// Constants.Password);
			F_Navigation.goToPage(Browser, I_Global_Common.Tab_Dashboard);

			F_ANPRDashboard.TriggerSimulator(IN_msg);

			String expectedMsg_IN = MessageFormat.format(ValueList.Message.Accepted, numplate.NumberPlate);
		//	F_GeneralVerification.verifyANPRMessage(Browser, expectedMsg_IN, Result);

			Date eventTime2 = new Date();
		
			
			String timeStamp_out = Functions.randomTimeStamp(eventTime2);
			String expectedDuration = Functions.getDuration(eventTime1, eventTime2);
			String expectedMsg_OUT = MessageFormat.format(ValueList.Message.VehicleOut, numplate.NumberPlate,
					expectedDuration);

			// Create OUT event
			String OUT_msg = F_ANPRDashboard.getANPRMessage(numplate.NumberPlate, timeStamp_out, camOUT, "OUT");
			F_ANPRDashboard.TriggerSimulator(OUT_msg);
			Browser.Driver.switchTo().window(currentWindow);
		//	F_Navigation.goToPage(Browser, I_Global_Common.Tab_Dashboard);

		//	F_GeneralVerification.verifyANPRMessage(Browser, expectedMsg_OUT, Result);
		}
		Assert.assertTrue(Result.Result, Result.Message);
	}

	
	// MAKE EVENT IN
	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Create_ANPR_for_ALL_existing_Users() {
		
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		
		List<String> n = F_ANPRSettings.getListNumberPlate(Browser);
		F_Navigation.goToPage(Browser, I_Global_Common.Tab_Dashboard);
		//F_Navigation.loginLocal(Browser, Constants.LocalUsername, Constants.Password);
		for (int i = 0; i < n.size(); i++) {
			F_ANPRDashboard.createANPREvent(n.get(i), true);
			Functions.waitForSeconds(2);
		}
	}

	
	//MAKE EVENT OUT
	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Clean_current_Parking() {
		
		List<String> n = F_ANPRSettings.getListNumberPlateCurrentParking(Browser);
		for (int i = 0; i < n.size(); i++) {
			F_ANPRDashboard.createANPREvent(n.get(i), false);
			Functions.waitForSeconds(1);
		}
		Assert.assertTrue(Result.Result, Result.Message);
	}
	
	
	//@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Create_ANPR_set_date_yesterday() {
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		
		List<String> n = F_ANPRSettings.getListNumberPlate(Browser);
		F_Navigation.goToPage(Browser, I_Global_Common.Tab_Dashboard);
		//F_Navigation.loginLocal(Browser, Constants.LocalUsername, Constants.Password);
		
		for (int i = 0; i < 10; i++) {
			//F_Dashboard.createANPREvent(n.get(i), true);
			
			Date eventTime1 = new Date();
			// Reduce one day
			//eventTime1.setDate(10);
			String timeStamp_in = Functions.randomTimeStamp(eventTime1);
			String IN_msg = F_ANPRDashboard.getANPRMessage(n.get(i), timeStamp_in, "Cam 3", "IN");
			F_ANPRDashboard.TriggerSimulator(IN_msg);
			Functions.waitForSeconds(1);
		}
	}

	

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Test() {
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		
		F_ANPRDashboard.createANPREvent("AB-1234", false);
		
	}
	
	
		
	
	

}