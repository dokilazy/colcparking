package tests.AIMS;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import interfaces.I_AIMS_Incident_Details;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.TestConfig.TestSuite;
import libraries.generalFunctions.Functions;
import libraries.productFunctions.F_AIMS;
import libraries.productFunctions.F_Navigation;
import libraries.verificationFunctions.F_AIMSVerification;
import libraries.verificationFunctions.F_GeneralVerification;
import tests.DefaultAnnotations;

public class Homepage_P2 extends DefaultAnnotations {

	@Test(priority = 0, groups = { TestSuite.BVT })
	public void Verify_Mandatary_fields() {

		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.clickAddIncident(Browser);
		F_GeneralVerification.verifyElementNotEnabled(Browser, "Save button", I_AIMS_Incident_Details.btn_Save, Result);
		Browser.enter(I_AIMS_Incident_Details.txt_callPhone,  Functions.randomNumberString(8));
		F_GeneralVerification.verifyElementEnabled(Browser, "Save button", I_AIMS_Incident_Details.btn_Save, Result);
		F_AIMS.SaveIncident(Browser);
		
		F_AIMSVerification.verifyMandataryFieldsError( Browser, "Officer Reporting", I_AIMS_Incident_Details.div_OfficerValid , Result) ;
		F_AIMSVerification.verifyMandataryFieldsError( Browser, "Incident Type", I_AIMS_Incident_Details.div_IncidentTypeValid , Result) ;
		F_AIMSVerification.verifyMandataryFieldsError( Browser, "Description", I_AIMS_Incident_Details.div_descValid , Result) ;
		F_AIMSVerification.verifyMandataryFieldsError( Browser, "Incident Location", I_AIMS_Incident_Details.div_incidentCoordVal , Result) ;
		
		Assert.assertTrue(Result.Result, Result.Message);
	}

}
