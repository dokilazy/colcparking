package interfaces;

import org.openqa.selenium.By;

public class I_AIMS_Incident_Details {

	public static By div_Officer = By.xpath("//div[@name='selectReportingOfficer']");
	
	public static By ddl_Officer = By.xpath("//div[@name='selectReportingOfficer']/ul/div/div/li");
	public static By txt_IncidentType = By.xpath("//div[@name='selectIncidentType']/div/input");
	
	public static By div_IncidentType = By.xpath("//div[@name='selectIncidentType']/ul/div/div");

	public static By txt_IncidentCode = By.xpath("//span[@class='form-control incident-code ng-binding']");
	public static By btn_pickerDate = By.xpath("//span[@class='input-group-addon']");
	public static By txt_DateTime = By.xpath("//input[@name='inputIncidentDateTime']");
	public static By txt_desc = By.xpath("//textarea[@name='textareaShortDescription']");
	
	public static By txt_firstName = By.xpath("//input[@ng-model='vmMasterData.incidentCopy.callerData.firstName']");
	public static By txt_lastName = By.xpath("//input[@ng-model='vmMasterData.incidentCopy.callerData.lastName']");
	public static By txt_callAddress = By.xpath("//input[@ng-model='vmMasterData.incidentCopy.callerData.address']");
	public static By txt_callCity = By.xpath("//input[@ng-model='vmMasterData.incidentCopy.callerData.city']");
	public static By txt_callzipCode = By.xpath("//input[@ng-model='vmMasterData.incidentCopy.callerData.zipCode']");
	public static By txt_callcoordinates = By.xpath("//input[@ng-model='vmMasterData.incidentCopy.callerData.location.coordinates']");
	public static By txt_callPhone = By.xpath("//input[@ng-model='vmMasterData.incidentCopy.callerData.phoneNumber']");
	public static By btn_Lookup = By.xpath("//button[@ng-click='vmMasterData.getCallerLocationByAddress()']");

	public static By txt_incidentAddr = By.xpath("//input[@name='inputIncidentStreetAddress']");
	public static By txt_incidentCity = By.xpath("//input[@ng-model='vmMasterData.incidentCopy.city']");
	public static By txt_incidentzipCode = By.xpath("//input[@ng-model='vmMasterData.incidentCopy.zipCode']");
	
	public static By txt_incidentCoord = By.xpath("//input[@name='inputIncidentCoordinates']");
	public static By btn_incidentLookup = By.xpath("//button[@ng-click='vmMasterData.getIncidentLocationByAddress()']");
	public static By btn_selMapIcon = By.xpath("//i[@class='icons8-crosshair']");
	public static By incident_maps = By.xpath("//div[@class='abs fl fl-vc fl-hc ng-scope']/div");

	// div[@class='abs map-wrapper']/div[1]
	public static By btn_zoomOutmap = By.xpath("//i[@class='icons8-minus-math--filled']");

	public static By btn_Save = By.xpath("//button[@ng-click='vmMasterData.saveIncident();']");
	public static By btn_Complete = By.xpath("//button[@ng-click='vmMasterData.completeIncident(vmMasterData.incidentCopy.id);']");
	public static By btn_Review = By.xpath("//button[@ng-click='vmMasterData.reviewIncident(vmMasterData.incidentCopy.id);']");
	
	public static By btn_closeActiveTab = By
			.xpath("//li[@ng-repeat='tab in vmTab.tabs' and @ui-sref-active='active']/a/div[last()]/i");
	public static By dlg_CloseTab = By.xpath("//div[@class='modal-content']");
	public static By btn_SaveChanges = By.xpath("//button[@ng-click='vmConfirmModal.option1();']");
	public static By btn_DiscardChanges = By.xpath("//button[@ng-click='vmConfirmModal.option2();']");
	
	public static By pnl_CustomFields = By.xpath("//fieldset[@id='fieldsetCustomFields']");
	public static By btn_DeclineWwarning = By.xpath("//div[@ng-click='vmNotifications.rejectAll();']");
	
	public static By popup_Messages = By.xpath("//div[@class='message ng-binding']");

	public static By tab_Evidence = By.xpath("//ul[@class='nav navbar-nav local-tab']/li[2]/a");

	//---- mandatary fileds  ----
	public static By div_OfficerValid = By.xpath("//form[@name='vmMasterData.basicInfoForm']/fieldset[1]/div");
	public static By div_IncidentTypeValid = By.xpath("//form[@name='vmMasterData.basicInfoForm']/fieldset[1]/div[2]");
	public static By div_descValid = By.xpath("//form[@name='vmMasterData.basicInfoForm']/fieldset[1]/div[5]");
	public static By div_incidentCoordVal = By.xpath("//fieldset[@id='fieldsetIncidentLocation']/div[4]");
	
	
	
	
	public static class Evidence {
		public static By btn_File = By.xpath("//div[@class='incident-visuals-toolbar']/nav/div/div[1]");
		public static By btn_Form = By.xpath("//div[@class='incident-visuals-toolbar']/nav/div/div[2]");
		public static By btn_Delete = By.xpath("//div[@class='incident-visuals-toolbar']/nav/div/div[4]");
		public static By frm_DeleteConfirm = By.xpath("//div[@class='modal-content']");
		public static By btn_confirmdelete = By.xpath("//div[@class='modal-footer-buttons']/button[1]");
		
		
		public static By btn_View = By.xpath("//div[@class='incident-visuals-toolbar']/nav/div/div[5]");
		public static By btn_Export = By.xpath("//button[@ng-click='vmAllEvidences.exportEvidences();']");
		public static By btn_SelectAll =   By.xpath("//label[@ng-click='vmAllEvidences.allSelected(vmAllEvidences.state);']");    
		public static By btn_gribView =   By.xpath("//label[@uib-btn-radio='vmAllEvidences.viewState.viewOptions[0]']");
		public static By btn_thumbnailView = By.xpath("//label[@uib-btn-radio='vmAllEvidences.viewState.viewOptions[1]']");

		public static By tbl_Evidence = By.id("evidences-list");
		
		// ---- Evidence Metadata Input Form -----
		public static By frm_AddEvidence = By.xpath("//form[@name='vmAddEvidence.propertiesForm']");
		public static By txt_Desc = By.xpath("//input[@ng-model='vmAddEvidence.evidence.description']");
		public static By dd_Criticality = By.xpath("//div[@ng-model='vmAddEvidence.evidence.criticality']");
		public static By li_Criticality = By.xpath(
				"//ul[@class='ui-select-choices ui-select-choices-content ui-select-dropdown dropdown-menu ng-scope ng-hide-remove']/li");
		public static By txt_Street = By.xpath("//input[@ng-model='vmAddEvidence.evidence.address']");
		public static By txt_City = By.xpath("//input[@ng-model='vmAddEvidence.evidence.city']");
		public static By txt_ZipCode = By.xpath("//input[@ng-model='vmAddEvidence.evidence.zipCode']");
		public static By txt_Coordinates = By.xpath("//input[@ng-model='vmAddEvidence.evidence.location.coordinates']");
		public static By btn_zoomOut = By.xpath("//div[@ng-click='mapCtrls.zoomOut();']");
		public static By btn_pickCood = By.xpath("//div[@ng-click='settingsCtrl.pickCoordinates();']");
		public static By lbl_mapInfo = By.xpath("//div[@class='leaflet-bottom leaflet-right']");

		public static By btn_lookup = By.xpath("//button[@ng-click='vmAddEvidence.getEvidenceLocationByAddress()']");
		
		public static By dlg_Addresslist = By.xpath("//div[@class='modal-dialog ']/div[@class='modal-content']/div[contains(@class, 'selectAddress')][1]");
		public static By ul_Addresslist = By.xpath("//div[@class='modal-dialog ']/div[@class='modal-content']/div[2]/div/div/ul[@class='list-group']");
		public static By btn_AddressSubmit = By.xpath("//button[@ng-click='vmSelectAddress.submit();']");
		
		
		public static By btn_Save = By.xpath("//button[@ng-click='vmAddEvidence.save();']");
		public static By btn_Cancel = By.xpath("//button[@ng-click='vmAddEvidence.cancel();']");

		public static By txt_NicheId = By.xpath("//input[@ng-model='vmExportIncident.nicheId']");
		public static By txt_OOF = By.xpath("//input[@ng-model='vmExportIncident.outOfForceId']");
		public static By btn_Exportdialog = By.xpath("//button[@ng-click='vmExportIncident.export();']");
		
		public static By frm_EvidenceError = By.xpath("//div[@class='form-group has-error']/div/div[@class='error-info']/span");
		
		//Playback 
		public static By btn_PlayPause = By.cssSelector("div.playback-btn.pause-play-circle") ;  
		
		public static By btn_Capture =  By.xpath("//a[@ng-click='vmVideoPlayer.captureImage($event)']");
		
		public static By frm_Video = By.xpath( "//div[@id='dlm-video-container']/div/video");
		
		
	}

	class Customs_Details {

	}

}
