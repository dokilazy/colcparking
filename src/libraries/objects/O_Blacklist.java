package libraries.objects;

import libraries.SeleniumBrowser;

import libraries.generalFunctions.Functions;
import interfaces.I_Global_Common;

public class O_Blacklist {

	public  String NumberPlate;
	public  String DriverName;
	public  String VehicleName;
	public  String VehicleType;
	public  String VehicleDesc;
	public  String Period;
	public  String LicenseType;
	public  String LicenseID;
	
	public void Init()
	{
		NumberPlate = Functions.randomNumberPlate();
		DriverName =  Functions.randomFullName();
		VehicleName = Functions.randomText(5);
		VehicleType = Functions.randomText(5);
		LicenseType = Functions.randomText(3);
		LicenseID = Functions.randomString("", "");
	}
	 
}
