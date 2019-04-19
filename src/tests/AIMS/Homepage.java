package tests.AIMS;

import java.io.File;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.Test;
import interfaces.I_AIMS_Home;
import interfaces.I_AIMS_Incident_Details;
import interfaces.I_Global_Common;
import interfaces.I_Global_Settings;

import libraries.Constants;
import libraries.Message;
import libraries.SeleniumBrowser;
import libraries.TCResult;
import libraries.ValueList;
import libraries.ValueList.AIMSMessage;
import libraries.ValueList.EvidenceType;
import libraries.ValueList.IncidentStatus;
import libraries.ValueList.LocalInfo;
import libraries.ValueList.PopupTitle;
import libraries.TestConfig.Owner;
import libraries.TestConfig.TestSuite;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Log;
import libraries.generalFunctions.Mouse;
import libraries.objects.O_Evidence;
import libraries.objects.O_ExportLog;
import libraries.objects.O_Incident;
import libraries.productFunctions.F_AIMS;
import libraries.productFunctions.F_Navigation;
import libraries.verificationFunctions.F_AIMSVerification;
import libraries.verificationFunctions.F_GeneralVerification;
import tests.DefaultAnnotations;

public class Homepage extends DefaultAnnotations {

	@Test(priority = 0, groups = { TestSuite.Smoke })
	public void Verify_Creation_new_Incidents() {

		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		O_Incident new_o = new O_Incident();
		new_o.createIncident(Browser);
		// F_GeneralVerification.verifyElementExist(Browser, "Notification Message",
		// I_AIMS_Home.div_SuccessMessage, Result);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.CreateIncidentSuccess, Result);

		Date now = new Date();

		F_GeneralVerification.verifyElementVisible(Browser, I_AIMS_Incident_Details.btn_DeclineWwarning,
				PopupTitle.DeclineWarning, Result);

		Assert.assertTrue(Result.Result, Result.Message);
		Browser.takeScreenshot("Homepage", "Verify_Creation_new_Incidents",
				"IncidentType and map at Incident Notifcation");

		F_AIMS.CloseIncident(Browser);

		// Check newly created Incident
		O_Incident result_o = new O_Incident();
		result_o.getIncidentData(Browser);

		F_GeneralVerification.verifyElementValue(Browser, "Incident Date", result_o.Datetime, new_o.Datetime, Result);
		F_GeneralVerification.verifyDatetime(Browser, "Incident Date", result_o.Datetime, now, 2, Result);
		F_GeneralVerification.verifyElementValue(Browser, "Type of Incident", result_o.Type, new_o.Type, Result);
		F_GeneralVerification.verifyDatetime(Browser, "Created Date", result_o.CreatedDate, now, 2, Result);
		F_GeneralVerification.verifyElementValue(Browser, "Reporter", result_o.ReportingOfficer, new_o.ReportingOfficer,
				Result);
		F_GeneralVerification.verifyElementValue(Browser, "Description", result_o.Desc, new_o.Desc, Result);
		F_GeneralVerification.verifyElementValue(Browser, "First Name", result_o.Firstname, new_o.Firstname, Result);
		F_GeneralVerification.verifyElementValue(Browser, "Last Name", result_o.Lastname, new_o.Lastname, Result);
		F_GeneralVerification.verifyElementValue(Browser, "Caller Phone", result_o.callerPhone, new_o.callerPhone,
				Result);
		F_GeneralVerification.verifyElementValue(Browser, "Status", result_o.Status, new_o.Status, Result);
		Assert.assertTrue(Result.Result, Result.Message);

	}

	@Test(priority = 1, groups = { TestSuite.Smoke })
	public void Verify_Upload_an_image() {
		String filename = "image 001.jpg";

		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);

		F_AIMS.OpenLatestIncident(Browser);
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);
		F_AIMS.UploadAEvidence(Browser, filename);
		F_AIMS.selectLocationForEvidence(Browser);

		Browser.click(I_AIMS_Incident_Details.Evidence.dd_Criticality);
		F_AIMS.selectDropDown_AIMS(Browser, I_AIMS_Incident_Details.Evidence.li_Criticality);

		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Save);
		// Browser.waitForElementNotVisible(I_AIMS_Incident_Details.Evidence.btn_Save);
		F_GeneralVerification.verifyElementExist(Browser, "Notification Message", I_AIMS_Home.div_SuccessMessage,
				Result);
		// Browser.waitForElementVisible(I_AIMS_Incident_Details.Evidence.tbl_Evidence);
		Functions.waitForSeconds(2);
		F_Navigation.RefreshPage(Browser);
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);

		long l = Functions.getLastModified(filename);
		Date observedOn = Functions.convertLongToDate(l);
		O_Evidence e = new O_Evidence(EvidenceType.jpeg, filename, observedOn);
		F_AIMSVerification.verifyEvidence(e, Browser, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	//@Test(priority = 2, groups = {TestSuite.Smoke })
	public void Verify_Export_evidence() {

		String filename = "";
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.OpenLatestIncident(Browser);
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);

		// Upload file if empty
		int r = Functions.countRow(Browser);
		if (r < 1) {
			List<String> uploadList = F_AIMS.UploadRandomEvidence(Browser, 3, false);

			while (filename == ValueList.ExportLog) {
				filename = uploadList.get(Functions.randomInterger(0, uploadList.size() - 1));
			}
			F_Navigation.RefreshPage(Browser);
			Browser.click(I_AIMS_Incident_Details.tab_Evidence);
			F_AIMS.SelectEvidence(Browser, filename, Result);
		}

		else
			filename = F_AIMS.SelectRandomEvidence(Browser);
		Browser.takeScreenshot("Homepage", "Verify_export_evidence", "Verify files selected");
		System.out.println("Start export file: " + filename);

		// Click on Export
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Export);
		F_AIMS.fillNicheID(Browser);
		Functions.waitForSeconds(2);

		// Verify downloaded file
		Browser.takeScreenshot("Homepage", "Verify_export_evidence", "file is downloaded..");
		System.out.println("Export is finished");
		Date now = new Date();
		O_Evidence e = new O_Evidence(EvidenceType.json, ValueList.ExportLog, now);

		F_AIMSVerification.verifyEvidence(e, Browser, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(priority = 3, groups = {TestSuite.Smoke })
	public void Verify_Upload_a_video_then_capture_an_image() {
		String filename = "VideoFile1.mp4";
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);

		F_AIMS.OpenLatestIncident(Browser);
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);
		F_AIMS.UploadAEvidence(Browser, filename);
		F_AIMS.selectLocationForEvidence(Browser);

		Browser.click(I_AIMS_Incident_Details.Evidence.dd_Criticality);
		F_AIMS.selectDropDown_AIMS(Browser, I_AIMS_Incident_Details.Evidence.li_Criticality);

		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Save);
		Browser.waitForElementNotVisible(I_AIMS_Incident_Details.Evidence.btn_Save);
		Browser.waitForElementVisible(I_AIMS_Incident_Details.Evidence.tbl_Evidence);

		Functions.waitForSeconds(2);
		F_Navigation.RefreshPage(Browser);
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);

		long l = Functions.getLastModified(filename);
		Date observedOn = Functions.convertLongToDate(l);
		O_Evidence e = new O_Evidence(EvidenceType.mp4, filename, observedOn);
		F_AIMSVerification.verifyEvidence(e, Browser, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		// Open video and capture image
		F_AIMS.openEvidence(Browser, filename, Result);

		// Browser.click(I_AIMS_Incident_Details.Evidence.btn_PlayPause);
		Functions.waitForSeconds(5);
		Mouse.MoveMouseToElement(Browser, I_AIMS_Incident_Details.Evidence.frm_Video);
		// Mouse.NavigateAndClick(Browser,
		// I_AIMS_Incident_Details.Evidence.btn_PlayPause);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_PlayPause);
		// Mouse.NavigateAndClick(Browser,
		// I_AIMS_Incident_Details.Evidence.btn_Capture);

		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Capture);
		Date now = new Date();

		F_GeneralVerification.verifyElementExist(Browser, "Notification Message", I_AIMS_Home.div_SuccessMessage,
				Result);

		Assert.assertTrue(Result.Result, Result.Message);

		Browser.click(I_AIMS_Incident_Details.tab_Evidence);
		DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm");
		O_Evidence o_CapturedImage = new O_Evidence(EvidenceType.png, mDateFormat.format(now).substring(0, 13), now);

		F_AIMSVerification.verifyEvidence(o_CapturedImage, Browser, Result);

		// Export the captured image
		F_AIMS.SelectEvidence(Browser, o_CapturedImage.Desc, Result);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Export);
		String NichiId = F_AIMS.fillNicheID(Browser);
		Date exportTime = new Date();
		Functions.waitForSeconds(1);

		// Open export log
		F_Navigation.RefreshPage(Browser);
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);
		F_AIMS.openEvidence(Browser, ValueList.ExportLog, Result);

		// Get latest log
		List<String> latestLog = Functions.getLastRow(Browser);

		// Verify log
		F_GeneralVerification.verifyElementValue(Browser, "User", latestLog.get(0), Constants.GlobalUsername, Result);
		F_GeneralVerification.verifyDatetime(Browser, "Date time", latestLog.get(1), exportTime, 2, Result);
		F_GeneralVerification.verifyElementValue(Browser, "Niche ID", latestLog.get(2), NichiId, Result);
		F_GeneralVerification.verifyElementContent(Browser, "Items", latestLog.get(4), o_CapturedImage.Desc, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		// After Test
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);
		F_AIMS.SelectEvidence(Browser, filename, Result);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Delete);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_confirmdelete);
	}

	//@Test(priority = 4, groups = {TestSuite.Smoke })
	public void Verify_Exportlog_Content() {

		// Select 1 file
		String filename = "";
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.OpenLatestIncident(Browser);
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);

		// Upload file if empty
		int r = Functions.countRow(Browser);
		if (r <= 1) {
			F_AIMS.UploadRandomEvidence(Browser, 2, false);
			F_Navigation.RefreshPage(Browser);
			Browser.click(I_AIMS_Incident_Details.tab_Evidence);
		}
		do {
			filename = F_AIMS.SelectRandomEvidence(Browser);
		} while (filename == ValueList.ExportLog);
		System.out.println("Selected file: " + filename);
		Assert.assertTrue(Result.Result, Result.Message);

		// Click on Export
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Export);
		Browser.waitForElementVisible(I_AIMS_Incident_Details.Evidence.btn_Exportdialog);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Exportdialog);
		Functions.waitForSeconds(1);
		F_GeneralVerification.verifyElementExist(Browser, "Niche ID input dialog",
				I_AIMS_Incident_Details.Evidence.btn_Exportdialog, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		String NichiId = F_AIMS.fillNicheID(Browser);
		Date now = new Date();
		Functions.waitForSeconds(1);

		// Open export log
		F_AIMS.openEvidence(Browser, ValueList.ExportLog, Result);

		// Get latest log
		List<String> latestLog = Functions.getLastRow(Browser);

		// Verify log
		F_GeneralVerification.verifyElementValue(Browser, "User", latestLog.get(0), Constants.GlobalUsername, Result);
		F_GeneralVerification.verifyDatetime(Browser, "Date time", latestLog.get(1), now, 2, Result);
		F_GeneralVerification.verifyElementValue(Browser, "Niche ID", latestLog.get(2), NichiId, Result);
		F_GeneralVerification.verifyElementValue(Browser, "Items", latestLog.get(4), filename, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	//@Test(priority = 6, groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Verify_Export_ExportLog() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.OpenLatestIncident(Browser);
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);
		F_AIMS.SelectEvidence(Browser, ValueList.ExportLog, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Export);
		String NichiId = F_AIMS.fillNicheID(Browser);
		Date now = new Date();
		Functions.waitForSeconds(1);

		// --- Try Delete the Export Log ---
		F_AIMS.SelectEvidence(Browser, ValueList.ExportLog, Result);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Delete);

		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_ErrorMessage,
				AIMSMessage.DeleteEvidenceError, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		// Open export log
		F_AIMS.openEvidence(Browser, ValueList.ExportLog, Result);

		// Get latest log
		List<String> latestLog = Functions.getLastRow(Browser);

		// Verify log
		F_GeneralVerification.verifyElementValue(Browser, "User", latestLog.get(0), Constants.GlobalUsername, Result);
		F_GeneralVerification.verifyDatetime(Browser, "Date time", latestLog.get(1), now, 2, Result);
		F_GeneralVerification.verifyElementValue(Browser, "Niche ID", latestLog.get(2), NichiId, Result);
		F_GeneralVerification.verifyElementValue(Browser, "Items", latestLog.get(4), "ExportLog.json", Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	//@Test(priority = 5, groups = {TestSuite.Smoke})
	public void Verify_export_all_evidences_more_than_6_evidences() {

		String evidenceName = "";
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.OpenLatestIncident(Browser);
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);

		// precondition
		int r = Functions.countRow(Browser);
		if (r <= 6) {
			List<String> uploadList = F_AIMS.UploadRandomEvidence(Browser, 6 - r, false);
		}

		// Get evidence name list
		List<String> evidenceList = Functions.getColumnData(Browser, 3);

		// Select all file
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_SelectAll);

		// Click on Export
		Browser.waitForElementEnabled(I_AIMS_Incident_Details.Evidence.btn_Export);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Export);
		String NichiId = F_AIMS.fillNicheID(Browser);
		Date now = new Date();
		Functions.waitForSeconds(3);

		F_AIMS.openEvidence(Browser, ValueList.ExportLog, Result);

		// Get latest log
		List<String> latestLog = Functions.getLastRow(Browser);

		// Verify log
		F_GeneralVerification.verifyElementValue(Browser, "User", latestLog.get(0), Constants.GlobalUsername, Result);
		F_GeneralVerification.verifyDatetime(Browser, "Date time", latestLog.get(1), now, 2, Result);
		F_GeneralVerification.verifyElementValue(Browser, "Niche ID", latestLog.get(2), NichiId, Result);

		for (int i = 0; i < evidenceList.size(); i++) {
			evidenceName = evidenceList.get(i);
			if (evidenceName.contains(ValueList.ExportLog))
				F_GeneralVerification.verifyElementContent(Browser, "Items", latestLog.get(4), "ExportLog.json",
						Result);
			else
				F_GeneralVerification.verifyElementContent(Browser, "Items", latestLog.get(4), evidenceList.get(i),
						Result);
		}
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(priority = 7, groups = { TestSuite.Smoke })
	public void Verify_Delete_an_evidence() {

		// Select 1 file
		String filename = "";
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_AIMS.OpenLatestIncident(Browser);
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);

		// Upload file if empty
		int r = Functions.countRow(Browser);
		if (r <= 2) {
			List<String> uploadList = F_AIMS.UploadRandomEvidence(Browser, 3, false);

			filename = uploadList.get(Functions.randomInterger(0, uploadList.size() - 1));
			
			F_Navigation.RefreshPage(Browser);
			Browser.click(I_AIMS_Incident_Details.tab_Evidence);
			F_AIMS.SelectEvidence(Browser, filename, Result);
		} else
			do {
				filename = F_AIMS.SelectRandomEvidence(Browser);
			} while (filename.trim() == ValueList.ExportLog);

		if (filename == "") {
			Browser.takeScreenshot("Homepage", "Verify_Delete_an_evidence", "Verify files selected or not");
			Result.SetResult(false);
			Result.SetMessage("Select Evident failed");

		}
		Assert.assertTrue(Result.Result, Result.Message);

		int numOfEvidence = F_AIMS.getNumberOfEvidence(Browser);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Delete);

		// F_GeneralVerification.verifyElementVisible(Browser,
		// I_AIMS_Incident_Details.Evidence.frm_DeleteConfirm, "Delete Confirm Dialog",
		// Result);

		Browser.click(I_AIMS_Incident_Details.Evidence.btn_confirmdelete);
		F_GeneralVerification.verifyElementExist(Browser, "Notification Message", I_AIMS_Home.div_SuccessMessage,
				Result);

		F_Navigation.RefreshPage(Browser);
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);

		By evidenceName = By.xpath("//table[@id='evidences-list']/tbody/tr/td[contains(text(),'" + filename + "')]");

		F_GeneralVerification.verifyElementInvisible(Browser, evidenceName, "Evidence deleted:" + filename, Result);

		int currentEvidenceNum = F_AIMS.getNumberOfEvidence(Browser);
		if ((numOfEvidence - 1) != currentEvidenceNum) {
			Result.Result = false;
			Result.Message = "Evidence Number before deleting is: " + numOfEvidence
					+ "\n Evidence Number after deleting is: " + currentEvidenceNum;
		}
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(priority = 8, groups = {TestSuite.Smoke })
	public void Verify_upload_file_without_input_metadata() {
		String filename = "image 001.jpg";
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);

		F_AIMS.OpenLatestIncident(Browser);
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);
		F_AIMS.UploadAEvidence(Browser, filename);

		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Save);
		Functions.waitForSeconds(1);

		F_GeneralVerification.verifyElementVisible(Browser, I_AIMS_Incident_Details.Evidence.frm_EvidenceError,
				"Coordinates Warning", Result);

		if (Result.Result == true) {
			String actualErrorMsg = Browser.captureInterface(I_AIMS_Incident_Details.Evidence.frm_EvidenceError)
					.getText();
			F_GeneralVerification.verifyElementValue(Browser, "Coordinates Warning Message", actualErrorMsg,
					Message.ErrorWarning.EvidenceCoordinateError, Result);
		}
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(priority= 14, groups = {TestSuite.Smoke })
	public void Verify_LookUp_in_Evidence_dialog() {
		String filename = "image 002.jpg";
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);

		F_AIMS.OpenLatestIncident(Browser);
		Browser.click(I_AIMS_Incident_Details.tab_Evidence);
		F_AIMS.UploadAEvidence(Browser, filename);

		F_GeneralVerification.verifyElementNotEnabled(Browser, "Look Up button",
				I_AIMS_Incident_Details.Evidence.btn_lookup, Result);

		Browser.enter(I_AIMS_Incident_Details.Evidence.txt_Street, ValueList.MapSearchKeyword);
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_lookup);

		Browser.waitForElementVisible(I_AIMS_Incident_Details.Evidence.dlg_Addresslist);
		F_GeneralVerification.verifyElementExist(Browser, "Address list", I_AIMS_Incident_Details.Evidence.dlg_Addresslist, Result);
		Assert.assertTrue(Result.Result, Result.Message);
		
		
		F_GeneralVerification.verifyElementNotEnabled(Browser, "OK button of Address stress dialog",
				I_AIMS_Incident_Details.Evidence.btn_AddressSubmit, Result);
		Browser.selectRandomDropdown(Browser, I_AIMS_Incident_Details.Evidence.ul_Addresslist, "li");

		Browser.click(I_AIMS_Incident_Details.Evidence.btn_AddressSubmit);

		//F_GeneralVerification.verifyElementInvisible(Browser, I_AIMS_Incident_Details.Evidence.dlg_Addresslist,"Addresses list", Result);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "Street address",
				I_AIMS_Incident_Details.Evidence.txt_Street, Result);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "City", I_AIMS_Incident_Details.Evidence.txt_City,
				Result);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "Zip code",
				I_AIMS_Incident_Details.Evidence.txt_ZipCode, Result);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "Coordinates",
				I_AIMS_Incident_Details.Evidence.txt_Coordinates, Result);

		Browser.click(I_AIMS_Incident_Details.Evidence.btn_Save);
		Browser.waitForElementNotVisible(I_AIMS_Incident_Details.Evidence.frm_AddEvidence);

		Functions.waitForSeconds(1);
		long l = Functions.getLastModified(filename);
		Date observedOn = Functions.convertLongToDate(l);
		O_Evidence e = new O_Evidence(EvidenceType.jpeg, filename, observedOn);
		F_AIMSVerification.verifyEvidence(e, Browser, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(priority = 13, groups = {TestSuite.Smoke })
	public void Verify_LookUp_in_Incident_Details() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);

		F_AIMS.OpenLatestIncident(Browser);
		Browser.enter(I_AIMS_Incident_Details.txt_callAddress, ValueList.MapSearchKeyword);
		Browser.click(I_AIMS_Incident_Details.btn_Lookup);
		Browser.waitForElementVisible(I_AIMS_Incident_Details.Evidence.dlg_Addresslist);
		F_GeneralVerification.verifyElementExist(Browser, "Address list dialog",
				I_AIMS_Incident_Details.Evidence.dlg_Addresslist, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_GeneralVerification.verifyElementNotEnabled(Browser, "OK button on Address stress dialog",
				I_AIMS_Incident_Details.Evidence.btn_AddressSubmit, Result);

		Browser.selectRandomDropdown(Browser, I_AIMS_Incident_Details.Evidence.ul_Addresslist, "li");
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_AddressSubmit);
		Functions.waitForSeconds(1);
		F_GeneralVerification.verifyElementNotExist(Browser, "Addresses list",
				I_AIMS_Incident_Details.Evidence.dlg_Addresslist, Result);

		F_GeneralVerification.verifyElementContentNotNull(Browser, "Street address",
				I_AIMS_Incident_Details.txt_callAddress, Result);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "City", I_AIMS_Incident_Details.txt_callCity,
				Result);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "Zip code", I_AIMS_Incident_Details.txt_callzipCode,
				Result);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "Coordinates",
				I_AIMS_Incident_Details.txt_callcoordinates, Result);

		String actualcallerAddress = Browser.captureInterface(I_AIMS_Incident_Details.txt_callAddress)
				.getAttribute("value");
		if (actualcallerAddress.length() < 7) {
			Result.SetResult(false);
			Result.SetMessage("Address is not auto filled");
		}

		Assert.assertTrue(Result.Result, Result.Message);

		// ---verify Incident location--
		Browser.enter(I_AIMS_Incident_Details.txt_incidentAddr, ValueList.MapSearchKeyword);
		Browser.click(I_AIMS_Incident_Details.btn_incidentLookup);
		Browser.waitForElementVisible(I_AIMS_Incident_Details.Evidence.dlg_Addresslist);
		F_GeneralVerification.verifyElementNotEnabled(Browser, "OK button on Address stress dialog",
				I_AIMS_Incident_Details.Evidence.btn_AddressSubmit, Result);

		Browser.selectRandomDropdown(Browser, I_AIMS_Incident_Details.Evidence.ul_Addresslist, "li");
		Browser.click(I_AIMS_Incident_Details.Evidence.btn_AddressSubmit);

		F_GeneralVerification.verifyElementContentNotNull(Browser, "Street address",
				I_AIMS_Incident_Details.txt_incidentAddr, Result);

		F_GeneralVerification.verifyElementContentNotNull(Browser, "City", I_AIMS_Incident_Details.txt_incidentCity,
				Result);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "Zip code",
				I_AIMS_Incident_Details.txt_incidentzipCode, Result);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "Coordinates",
				I_AIMS_Incident_Details.txt_incidentCoord, Result);

		String actualAddress = Browser.captureInterface(I_AIMS_Incident_Details.txt_incidentAddr).getAttribute("value");
		if (actualAddress.length() < 7) {
			Result.SetResult(false);
			Result.SetMessage("Address is not auto filled");
		}
		Assert.assertTrue(Result.Result, Result.Message);

	}

	@Test(priority = 12, groups = { TestSuite.Smoke })
	public void Verify_Incidence_Coordinates_after_selecting_map() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);

		F_AIMS.clickAddIncident(Browser);
		F_AIMS.selectMap(Browser);
		F_GeneralVerification.verifyElementContentNotNull(Browser, "Coordinates",
				I_AIMS_Incident_Details.txt_incidentCoord, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(priority = 10, groups = {TestSuite.Smoke })
	public void Verify_Update_metadata_and_discarding_changes() {
		String originalValue = "";

		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);

		F_AIMS.OpenLatestIncident(Browser);

		Functions.waitForSeconds(1);
		F_AIMS.scrollToEndOfDetailsPanel(Browser);

		// Get original value of text custom field
		WebElement input = Browser.Driver.findElement(By.xpath("//fieldset[@id='fieldsetCustomFields']/div[1]//input"));
		originalValue = input.getAttribute("value");

		// update to new value
		String randomText = "";
		if (input.getAttribute("type").contains("text") && input.getAttribute("ng-if") != null) {
			Browser.scrollToEle(input);
			randomText = Functions.randomText(15);
		}
		if (input.getAttribute("type").contains("number")) {
			randomText = Functions.randomNumberString(10);
		}
		input.sendKeys(randomText);

		// Close Incident
		F_AIMS.CloseIncident(Browser);
		F_GeneralVerification.verifyElementExist(Browser, "Close Tab dialog", I_AIMS_Incident_Details.dlg_CloseTab,
				Result);

		Assert.assertTrue(Result.Result, Result.Message);

		Browser.click(I_AIMS_Incident_Details.btn_DiscardChanges);

		// Verify value after discarding
		F_AIMS.OpenLatestIncident(Browser);
		String actualResult = Browser.Driver
				.findElement(By.xpath("//fieldset[@id='fieldsetCustomFields']/div[1]//input")).getAttribute("value");
		F_GeneralVerification.verifyElementContent(Browser, "CAD custom field value", actualResult, originalValue,
				Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(priority = 9, groups = { TestSuite.Smoke })
	public void Verify_Update_a_custom_fields() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);

		F_AIMS.OpenLatestIncident(Browser);

		Functions.waitForSeconds(1);
		F_AIMS.scrollToEndOfDetailsPanel(Browser);

		// Get original value of text custom field
		WebElement input = Browser.Driver.findElement(By.xpath("//fieldset[@id='fieldsetCustomFields']/div[1]//input"));

		// update to new value
		String randomText = "";
		if (input.getAttribute("type").contains("text") && input.getAttribute("ng-if") != null) {
			Browser.scrollToEle(input);
			randomText = Functions.randomText(15);
		}
		if (input.getAttribute("type").contains("number")) {
			randomText = Functions.randomNumberString(10);
		}
		input.clear();
		input.sendKeys(randomText);

		// Close Incident
		F_AIMS.CloseIncident(Browser);
		F_GeneralVerification.verifyElementExist(Browser, "Close Tab dialog", I_AIMS_Incident_Details.dlg_CloseTab,
				Result);

		Assert.assertTrue(Result.Result, Result.Message);

		Browser.click(I_AIMS_Incident_Details.btn_SaveChanges);

		// Verify value after discarding
		F_AIMS.OpenLatestIncident(Browser);
		String actualResult = Browser.Driver
				.findElement(By.xpath("//fieldset[@id='fieldsetCustomFields']/div[1]//input")).getAttribute("value");
		F_GeneralVerification.verifyElementContent(Browser, "CAD custom field value", actualResult, randomText, Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test(priority = 11, groups = {TestSuite.Smoke })
	public void Verify_Incident_Status() {
		String status = "";
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);

		// Create a new incident
		O_Incident new_o = new O_Incident();
		new_o.createIncident(Browser);
		Browser.clickJavascript(I_AIMS_Incident_Details.btn_DeclineWwarning);
		Browser.waitForElementNotVisible(I_AIMS_Incident_Details.btn_DeclineWwarning);
		F_AIMS.CloseIncident(Browser);
		List<String> firstRow = Functions.getFirstRow(Browser);
		status = firstRow.get(5);
		F_GeneralVerification.verifyElementValue(Browser, "Status", status, new_o.Status, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// Accept an incident - Update a text field
		F_AIMS.OpenLatestIncident(Browser);
		F_GeneralVerification.verifyElementNotEnabled(Browser, "Save button", I_AIMS_Incident_Details.btn_Save, Result);

		F_AIMS.scrollToEndOfDetailsPanel(Browser);
		WebElement input = Browser.Driver.findElement(By.xpath("//fieldset[@id='fieldsetCustomFields']/div[1]//input"));

		String randomText = "";
		if (input.getAttribute("type").contains("text") && input.getAttribute("ng-if") != null) {
			Browser.scrollToEle(input);
			randomText = Functions.randomText(15);
		}
		if (input.getAttribute("type").contains("number")) {
			randomText = Functions.randomNumberString(10);
		}
		input.clear();
		input.sendKeys(randomText);

		F_AIMS.SaveIncident(Browser);
		F_AIMS.CloseIncident(Browser);

		firstRow = Functions.getFirstRow(Browser);
		status = firstRow.get(5);

		F_GeneralVerification.verifyElementValue(Browser, "Status", status, IncidentStatus.Accepted, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		// Complete Incident
		F_AIMS.OpenLatestIncident(Browser);
		F_GeneralVerification.verifyElementNotEnabled(Browser, "Save button", I_AIMS_Incident_Details.btn_Review,
				Result);
		Browser.click(I_AIMS_Incident_Details.btn_Complete);
		F_AIMS.CloseIncident(Browser);
		F_Navigation.RefreshPage(Browser);

		firstRow = Functions.getFirstRow(Browser);
		status = firstRow.get(5);
		F_GeneralVerification.verifyElementValue(Browser, "Status", status, IncidentStatus.InReview, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		// Review Incident
		F_AIMS.OpenLatestIncident(Browser);
		F_GeneralVerification.verifyElementNotEnabled(Browser, "Save button", I_AIMS_Incident_Details.btn_Complete,
				Result);
		Browser.click(I_AIMS_Incident_Details.btn_Review);
		F_AIMS.CloseIncident(Browser);
		firstRow = Functions.getFirstRow(Browser);
		status = firstRow.get(5);
		F_GeneralVerification.verifyElementValue(Browser, "Status", status, IncidentStatus.Closed, Result);
		Assert.assertTrue(Result.Result, Result.Message);

	}

	@Test(groups = { TestSuite.Smoke })
	public void Create_two_Incident_continuously() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		O_Incident new_o = new O_Incident();
		new_o.createIncident(Browser);

		F_GeneralVerification.verifyElementExist(Browser, "Notification Message", I_AIMS_Home.div_SuccessMessage,
				Result);
		F_GeneralVerification.verifyElementVisible(Browser, I_AIMS_Incident_Details.btn_DeclineWwarning,
				PopupTitle.DeclineWarning, Result);
		Browser.click(I_AIMS_Incident_Details.btn_DeclineWwarning);
		Assert.assertTrue(Result.Result, Result.Message);

		F_AIMS.CloseIncident(Browser);

		O_Incident incident2_o = new O_Incident();
		incident2_o.createIncident(Browser);

		F_GeneralVerification.verifyElementExist(Browser, "Notification Message", I_AIMS_Home.div_SuccessMessage,
				Result);

		Assert.assertTrue(Result.Result, Result.Message);
	}

}
