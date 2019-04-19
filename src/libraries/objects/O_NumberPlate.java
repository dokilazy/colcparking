package libraries.objects;

import libraries.SeleniumBrowser;

import libraries.ValueList.LocalInfo;
import libraries.generalFunctions.Functions;
import interfaces.I_Global_Common;

public class O_NumberPlate {

	public  String NumberPlate;
	public  String DriverName;
	public  String VehicleName;
	public  String VehicleType;
	public  String VehicleDesc;
	public  String ParkingSite;
	public  String TimeProfile;
	public  String Whitelist;
	public  String DriverProfile;
	public  String LicenseType;
	public  String LicenseID;
	
	
	public void Init()
	{
		NumberPlate = Functions.randomNumberPlate();
		DriverName =  Functions.randomFullName();
		VehicleName = Functions.randomText(5);
		VehicleType = Functions.randomText(5);
		ParkingSite = LocalInfo.ParkingSite;
		TimeProfile = LocalInfo.TimeProfile;
		VehicleDesc = "This is a description _ for testing - ANPR";
		Whitelist = LocalInfo.Whitelist;
		
		LicenseType = Functions.randomText(3);
		LicenseID = Functions.randomString("", "");
	}
	 
}
