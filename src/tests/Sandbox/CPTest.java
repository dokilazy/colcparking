package tests.Sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

import libraries.generalFunctions.Functions;
import libraries.productFunctions.F_CPContraventions;
import libraries.productFunctions.F_CP_common;

public class CPTest {

	// @Test()
	public void createVRMlist() {
		F_CP_common.randomListDVLA(1000);

	}

	@Test()
	public void createNewContravention() {
		// F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername,
		// Constants.Password);
		int n = 10;
		String user = "initUser";
		String encoderNo = "8";

		// Functions.clickBySikuli("Imgs/Simulator.png");
		for (int i = 0; i < n; i++) {
			Long timeStamp = Functions.getCurrentTimeinLong();
			encoderNo = F_CP_common.getRandomEncoderNo();
			F_CPContraventions.createNewContravention(encoderNo, user, timeStamp, null, false, "");
		}
		Assert.assertTrue(true, "");
	}

	// @Test()
	public void createVoidcontravention() {
		// F_Navigation.loginCoLcParking(Browser, Constants.GlobalUsername,
		// Constants.Password);
		int n = 1;
		String user = "initUser";
		String encoderNo = "8"; // F_CP_common.getRandomEncoderNo();
		Long timeStamp = Functions.getCurrentTimeinLong();
		F_CPContraventions.createNewContravention(encoderNo, user, timeStamp, null, true, "Void reason from SeMSY");
		Assert.assertTrue(true, "");
	}

	//@Test()
	public void createJson() {
		//F_CP_common.createVRMJson();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
