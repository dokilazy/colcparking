package tests.ANPR;

import java.text.MessageFormat;
import java.util.Date;
import org.testng.Assert;
import org.testng.annotations.Test;
import interfaces.I_Global_Common;
import libraries.Constants;
import libraries.ValueList;
import libraries.TestConfig.Owner;
import libraries.TestConfig.TestSuite;
import libraries.ValueList.LocalInfo;
import libraries.generalFunctions.Functions;
import libraries.objects.O_NumberPlate;
import libraries.productFunctions.F_ANPRDashboard;
import libraries.productFunctions.F_Navigation;
import libraries.verificationFunctions.F_ANPRVerification;
import libraries.productFunctions.F_ANPRSettings;
import tests.DefaultAnnotations;

public class DashBoard extends DefaultAnnotations {

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Verify_Accepted_ANPR() {
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		//F_Navigation.loginGlobal(Browser, "initUser", "initUser");
		F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);

		F_ANPRSettings.goToPage.NumnberPlates(Browser);

		// Add a Number plate
		O_NumberPlate numplate = new O_NumberPlate();
		numplate.Init();
		F_ANPRSettings.AddNewVisitorNumberPlate(Browser, numplate);
		F_Navigation.loginLocal(Browser, Constants.LocalUsername, Constants.LocalUserPass);
		F_ANPRDashboard.createANPREvent(numplate.NumberPlate, true);
		String expectedMsg = MessageFormat.format(ValueList.Message.Accepted, numplate.NumberPlate);
		F_ANPRVerification.verifyANPRMessage(Browser, expectedMsg, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Verify_Unregistered_ANPR() {
		O_NumberPlate numplate = new O_NumberPlate();
		numplate.Init();
		F_Navigation.loginLocal(Browser, Constants.LocalUsername, Constants.LocalUserPass);
		F_ANPRDashboard.createANPREvent(numplate.NumberPlate, true);
		String expectedMsg = MessageFormat.format(ValueList.Message.Unregister, numplate.NumberPlate);
		F_ANPRVerification.verifyANPRMessage(Browser, expectedMsg, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Verify_ANPR_OUT_AT_Local() {
		String currentWindow = Browser.Driver.getWindowHandle();

		// Login to Global
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
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
		F_Navigation.loginLocal(Browser, Constants.LocalUsername, Constants.LocalUserPass);
		F_Navigation.goToPage(Browser, I_Global_Common.Tab_Dashboard);

		F_ANPRDashboard.TriggerSimulator(IN_msg);

		String expectedMsg_IN = MessageFormat.format(ValueList.Message.Accepted, numplate.NumberPlate);
		F_ANPRVerification.verifyANPRMessage(Browser, expectedMsg_IN, Result);

		Date eventTime2 = new Date();
		String timeStamp_out = Functions.randomTimeStamp(eventTime2);
		String expectedDuration = Functions.getDuration(eventTime1, eventTime2);
		String expectedMsg_OUT = MessageFormat.format(ValueList.Message.VehicleOut, numplate.NumberPlate,
				expectedDuration);

		// Create OUT event
		String OUT_msg = F_ANPRDashboard.getANPRMessage(numplate.NumberPlate, timeStamp_out, camOUT, "OUT");
		F_ANPRDashboard.TriggerSimulator(OUT_msg);

		Browser.Driver.switchTo().window(currentWindow);
		F_ANPRVerification.verifyANPRMessageForEventOut(Browser, expectedMsg_OUT, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	/*
	 * @Test(groups = { Owner.VuNguyen, TestSuite.BVT }) public void
	 * Verify_Duration_is_more_than_1day() { //String currentWindow =
	 * Browser.Driver.getWindowHandle();
	 * 
	 * // Login to Global F_Navigation.loginGlobal(Browser,
	 * Constants.GlobalUsername, Constants.Password); F_Navigation.goToPage(Browser,
	 * I_Global_Common.Tab_Settings);
	 * 
	 * // Add a Number plate F_ANPRSettings.goToPage.NumnberPlates(Browser);
	 * O_NumberPlate numplate = new O_NumberPlate(); numplate.Init();
	 * F_ANPRSettings.AddNewVisitorNumberPlate(Browser, numplate);
	 * 
	 * // ANPR event for new NP String camIN = LocalInfo.CamIN; String camOUT =
	 * LocalInfo.CamOUT; Date eventTime1 = new Date();
	 * eventTime1.setDate(eventTime1.getDate() - 1); String timeStamp_in =
	 * Functions.randomTimeStamp(eventTime1); String IN_msg =
	 * Functions.getANPRMessage(numplate.NumberPlate, timeStamp_in, camIN, "IN");
	 * 
	 * // Login to Local F_Navigation.loginLocal(Browser, Constants.LocalUsername,
	 * Constants.LocalUserPass); F_Navigation.goToPage(Browser,
	 * I_Global_Common.Tab_Dashboard);
	 * 
	 * Functions.TriggerSimulator(IN_msg);
	 * 
	 * String expectedMsg_IN = MessageFormat.format(ValueList.Message.Accepted,
	 * numplate.NumberPlate); F_GeneralVerification.verifyANPRMessage(Browser,
	 * expectedMsg_IN, Result);
	 * 
	 * Date eventTime2 = new Date(); String timeStamp_out =
	 * Functions.randomTimeStamp(eventTime2); String expectedDuration =
	 * Functions.getDuration(eventTime1, eventTime2); String expectedMsg_OUT =
	 * MessageFormat.format(ValueList.Message.VehicleOut, numplate.NumberPlate,
	 * expectedDuration);
	 * 
	 * // Create OUT event String OUT_msg =
	 * Functions.getANPRMessage(numplate.NumberPlate, timeStamp_out, camOUT, "OUT");
	 * Functions.TriggerSimulator(OUT_msg);
	 * 
	 * //Browser.Driver.switchTo().window(currentWindow);
	 * F_GeneralVerification.verifyANPRMessage(Browser, expectedMsg_OUT, Result);
	 * 
	 * Assert.assertTrue(Result.Result, Result.Message); }
	 * 
	 */
	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Verify_ANPR_OUT_AT_Global() {
		String currentWindow = Browser.Driver.getWindowHandle();

		// Login to Global
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
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

		F_Navigation.goToPage(Browser, I_Global_Common.Tab_Dashboard);

		F_ANPRDashboard.TriggerSimulator(IN_msg);

		String expectedMsg_IN = MessageFormat.format(ValueList.Message.Accepted, numplate.NumberPlate);
		F_ANPRVerification.verifyANPRMessage(Browser, expectedMsg_IN, Result);

		Date eventTime2 = new Date();
		String timeStamp_out = Functions.randomTimeStamp(eventTime2);
		String expectedDuration = Functions.getDuration(eventTime1, eventTime2);
		String expectedMsg_OUT = MessageFormat.format(ValueList.Message.VehicleOut, numplate.NumberPlate,
				expectedDuration);

		// Create OUT event
		String OUT_msg = F_ANPRDashboard.getANPRMessage(numplate.NumberPlate, timeStamp_out, camOUT, "OUT");
		F_ANPRDashboard.TriggerSimulator(OUT_msg);

		Browser.Driver.switchTo().window(currentWindow);
		F_ANPRVerification.verifyANPRMessageForEventOut(Browser, expectedMsg_OUT, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Create_Many_IN_OUT_events() {
		// String currentWindow = Browser.Driver.getWindowHandle();
		int loop = 2;

		for (int i = 0; i < loop; i++) {

			// Login to Global
			F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);

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
			F_Navigation.loginLocal(Browser, Constants.LocalUsername, Constants.LocalUserPass);
			F_Navigation.goToPage(Browser, I_Global_Common.Tab_Dashboard);

			F_ANPRDashboard.TriggerSimulator(IN_msg);

			String expectedMsg_IN = MessageFormat.format(ValueList.Message.Accepted, numplate.NumberPlate);
			F_ANPRVerification.verifyANPRMessage(Browser, expectedMsg_IN, Result);

			Date eventTime2 = new Date();
			String timeStamp_out = Functions.randomTimeStamp(eventTime2);
			String expectedDuration = Functions.getDuration(eventTime1, eventTime2);
			String expectedMsg_OUT = MessageFormat.format(ValueList.Message.VehicleOut, numplate.NumberPlate,
					expectedDuration);

			// Create OUT event
			String OUT_msg = F_ANPRDashboard.getANPRMessage(numplate.NumberPlate, timeStamp_out, camOUT, "OUT");
			F_ANPRDashboard.TriggerSimulator(OUT_msg);

			// Browser.Driver.switchTo().window(currentWindow);
			F_ANPRVerification.verifyANPRMessageForEventOut(Browser, expectedMsg_OUT, Result);

		}
		Assert.assertTrue(Result.Result, Result.Message);
	}

	// @Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Verify_Details_Of_ANPR_Event() {

	}

	// @Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Verify_Current_Parking_And_Occupied_Chart() {

	}

}
