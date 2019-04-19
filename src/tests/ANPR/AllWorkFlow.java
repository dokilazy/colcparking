package tests.ANPR;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import interfaces.I_Global_Common;
import interfaces.I_Global_Settings;
import libraries.Constants;
import libraries.ValueList;
import libraries.TestConfig.Owner;
import libraries.TestConfig.TestSuite;
import libraries.generalFunctions.Functions;
import libraries.objects.O_NumberPlate;
import libraries.productFunctions.F_ANPRDashboard;
import libraries.productFunctions.F_Navigation;
import libraries.productFunctions.F_ANPRSettings;
import libraries.verificationFunctions.F_ANPRVerification;
import tests.DefaultAnnotations;

public class AllWorkFlow extends DefaultAnnotations {

	public void Add_Regular_And_verify_ANPR_event_for_new_regular() {
		F_Navigation.loginGlobal(Browser, Constants.GlobalUsername, Constants.Password);
		F_Navigation.goToPage(Browser, I_Global_Common.Tab_Settings);
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
		if (Result.Result == true) {
			F_ANPRVerification.verifyNumberPlateDetails(Browser, numplate, Result, true);

			F_Navigation.loginLocal(Browser, Constants.LocalUsername, Constants.LocalUserPass);
			F_ANPRDashboard.createANPREvent(numplate.NumberPlate, true);
			String expectedMsg = MessageFormat.format(ValueList.Message.Accepted, numplate.NumberPlate);
			F_ANPRVerification.verifyANPRMessage(Browser, expectedMsg, Result);
		}
		Assert.assertTrue(Result.Result, Result.Message);
	}

}
