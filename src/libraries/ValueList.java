package libraries;

public class ValueList {

	public static class WindowTitle {
		public static String NoteAndTask = "Notes and Task";
		public static String InsuranceBrokerTechnology = "Insurance Broker Technology";
	}

	public static final class PopupTitle {
		public static String DeclineWarning = "Decline Warning";
	}


	public static class Message {
		public static String Accepted = "{0} - NUMBER_ACCEPTED";
		public static String Unregister = "{0} - NUMBER_PLATE_UNREGISTERED";
		public static String OutTimeFrame = "{0} - OUT_OF_TIME_FRAME";
		public static String VehicleOut = "{0} - VEHICLE_OUT - Duration:{1}";
		public static String InBlacklist = "{0} - IN_BLACKLIST";
		public static String Save_Success = "{0} has been saved successfully";
	}
	
	public static class AIMSMessage{
		public static String CreateIncidentSuccess = "Incident has been successfully created";
		public static String CreateCategorySuccess = "Category has been successfully created";
		public static String AssignIncidentTypeSuccess = "Incident types have been assigned";
		public static String DeleteCategorySuccess = "Category has been successfully deleted";
		public static String DeleteCategoryError = "Cannot delete category";
		public static String DeleteEvidenceError = "Can not delete system file";
		public static String UpdateIncidentTypeSuccess = "Incident type has been successfully updated";
	}
	
	
	
	public static class LocalInfo{
		public static String ParkingSite = "Parking site 2";
		public static String TimeProfile = "TimeProfile 001";
		public static String Whitelist = "Whitelist 001";
		public static String CamIN = "Camera 001";
		public static String CamIN_Ip = "192.168.1.10";
		public static String CamOUT = "Camera 002";
		public static String CamOUT_Ip = "192.168.1.11";
	}
	
	public static class Incident{
		public static String city = "Karlsruhe";
		public static String zipCode =  "76133";
		public static String Reporter = "initUser, initUser";
		public static String IncidentType =  "TestType";
	}
	public static class IncidentStatus{
		public static String New =  "New";
		public static String Accepted =  "Accepted";
		public static String InReview =  "In review";
		public static String Closed =  "Closed";
	}
		
	public static class EvidenceType{
		public static String jpeg = "image/jpeg";
		public static String png =  "image/png";
		public static String mp4 = "video/mp4";
		public static String json =  "application/json";
	}
	public static class UploadType{
		public static String image = "IMAGE";
		public static String video = "VIDEO";
		public static String any = "ANY";
	}
	
	//public static final String ExportLog = "Export Log";
	public static String ExportLog = "Export Log";
	public static String MapSearchKeyword = "bac";
	
}
