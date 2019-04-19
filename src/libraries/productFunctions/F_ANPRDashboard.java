package libraries.productFunctions;

import java.util.List;
//import java.awt.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.sikuli.script.ImagePath;

import interfaces.I_Dashboard;
import interfaces.I_Global_Common;
//import executionEngine.RunTestscript;
import interfaces.I_Global_Settings;
import libraries.Constants;
import libraries.SeleniumBrowser;
import libraries.ValueList.LocalInfo;
import libraries.generalFunctions.Functions;
import libraries.objects.O_ANPREvent;
import libraries.objects.O_Blacklist;
import libraries.objects.O_NumberPlate;
//import utility.Log;

public class F_ANPRDashboard {

	public static void clickSynCenter(SeleniumBrowser Browser) {
		try {

			Browser.click(I_Dashboard.ltxt_SyncCenter);
		} catch (NoSuchElementException e) {

		}
		
	}

	public static void createANPREvent(String numplate, Boolean type) {

		String timeStamp = Functions.randomTimeStamp();

		if (type == true) {
			String IN_msg = F_ANPRDashboard.getANPRMessage(numplate, timeStamp, LocalInfo.CamIN , "IN");
			F_ANPRDashboard.TriggerSimulator(IN_msg);
		} else {

			String OUT_msg = F_ANPRDashboard.getANPRMessage(numplate, timeStamp, LocalInfo.CamOUT, "OUT");
			F_ANPRDashboard.TriggerSimulator(OUT_msg);

		}
	}
	
	public static O_ANPREvent getEventDetails(SeleniumBrowser Browser, String numPlate, String timeStamp)
	{
		
		O_ANPREvent event = new O_ANPREvent();
		
		return event;
	}
	
	
	
	public static void verifyDetails(SeleniumBrowser Browser, O_NumberPlate nPlate, O_ANPREvent event) {
				
	}
	
	public static void createANPROUTForcurrentParking() {
		
	}

	public static String getANPRMessage(String numplate, String timeStamp, String camName, String Type) {
	
		String CamIP =  LocalInfo.CamIN_Ip;
		
		if (camName.contains(LocalInfo.CamOUT)) 
			CamIP = LocalInfo.CamOUT_Ip;
		
		String event_script = "<diesel version=\"0.3\">\r\n" + " <result>\r\n"
				+ "  <analysis channel=\"1\" name=\"DINPR Germany 50\" host=\"DAO9-00142313\" />\r\n"
				+ "  <camera channel=\"1\" cols=\"704\" rows=\"576\" depth=\"1\" name=\"" + camName + "\"   type=\""
				+ Type + "\" />\r\n" + "  <userevent message=\"" + numplate
				+ "\" confidence=\"84\" name=\"NUMBERPLATE\"   avrcharheight=\"32\" direction=\"2\" countrycode=\"D\" daVidAlarmID=\"8\"   timestamp=\""
				+ timeStamp + "\" />\r\n"
				+ "  <wraprect contype=\"rectangle\" top=\"495\" bottom=\"557\" left=\"478\"   right=\"683\" />\r\n"
				+ "  <image content=\"Image\" format=\"jpg\" path=\"@1415088726_0001\"   memptr=\"17B43050\" memfmt=\"raw\" cols=\"704\" rows=\"576\" depth=\"1\" size=\"405504\"   url=\""+CamIP+"/epic/C01/2014/12/11/09/1418285232_0001.jpg\">\r\n"
				+ "  </image>\r\n"
				+ "  <image content=\"Patch\" format=\"jpg\" path=\"@1415088726_0001p\"   memptr=\"1815D6B0\" memfmt=\"raw\" cols=\"208\" rows=\"62\" depth=\"1\" size=\"12896\"   url=\""+CamIP+"/epic/C01/2014/12/11/09/1418285232_0001p.jpg\">\r\n"
				+ "  </image>\r\n" + "  <alarm internal_id=\"202410\" state=\"begin\" timestamp=\"" + timeStamp
				+ "\"   eventtype=\"NUMBERPLATE\">\r\n" + "   <mysql autoid=\"67500\" hostid=\"3\" descid=\"4\" />\r\n"
				+ "   <filter first=\"deny\" second=\"allow\" deny=\"all:\"    allow=\"result:hwconled1:xmlout1:none3:\">\r\n"
				+ "    <deny entryidlist=\"-1,\r\n" + "\" />\r\n" + "    <allow reject=\"logical not,\r\n"
				+ "1:\" lastreject=\"logical not\"     entryidlist=\"-1,\r\n" + "1,\r\n" + "\" />\r\n"
				+ "    <info key=\"group_or_comment\" value=\"das ist der Kommentar\" />\r\n"
				+ "    <info key=\"info1\" value=\"Das ist der Infotext1\" />\r\n"
				+ "    <info key=\"info2\" value=\" Das ist der Infotext2\" />\r\n"
				+ "    <lastresult itemid=\"1\" filterid=\"11\" filtername=\"Post\"     listid=\"1\" listname=\"Post\" />\r\n"
				+ "   </filter>\r\n" + "  </alarm>\r\n" + " </result>\r\n" + "</diesel>\r\n" + "";
		//System.out.println(event_script);
		return event_script;
	}

	public static void TriggerSimulator(String event_script) {
	
	
		String btn_Simulator = "Imgs/Simulator.png";
		String btn_editXml = "Imgs/EditXML.png";
		String txt_Message = "Imgs/MessageText.png";
		String btn_Save = "Imgs/Save.png";
		String btn_Trigger = "Imgs/Run.png";
		String btn_Minimize = "Imgs/Minimize.png";
	
		Functions.clickBySikuli(btn_Simulator);
		Functions.clickBySikuli(btn_editXml);
	
		Functions.pasteValueBySikuli(txt_Message, event_script);
		
		Functions.clickBySikuli(btn_Save);
		Functions.clickBySikuli(btn_Trigger);
		//Functions.clickBySikuli(btn_Minimize);
		Functions.clickBySikuli(btn_Simulator);
	}
	
	

}
