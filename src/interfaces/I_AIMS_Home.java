package interfaces;
import org.openqa.selenium.By;
public class I_AIMS_Home {
	
	
	public static By  tab_Settings     = By.xpath("//i[@class='icons8-settings-3']");
	
	public static By  btn_AddIncident   =    By.xpath("//i[@ng-click='vmHeader.openNewIncident()']");
	public static By btn_addIncidentOnToolbar    =   By.xpath("//button[@class='btn btn-dlm-tb navbar-btn ng-binding']/i[@class='icons8-plus-math']");
	public static By  btn_Search   =    By.xpath("//div[@class='container-fluid']/div[1]/button[1]");
	public static By  ddl_IncidentType  =    By.xpath("//input[@class='ui-select-search input-xs ng-pristine ng-untouched ng-valid']");
	public static By  txt_IncidentCode   =    By.xpath("//span[@class='form-control incident-code ng-binding']");
	public static By  btn_HomeTab   =    By.xpath("//ul[@class='nav navbar-nav global-tab system-tabs ng-scope']/li[1]/a");
	public static By tbl_IncidentTable = By.xpath("//table[@id='inbox-list']");
	public static By div_SuccessMessage =  By.xpath("//body/div[@class='ui-notification ng-scope success']/div");
	public static By div_ErrorMessage =  By.xpath("//body/div[@class='ui-notification ng-scope error']/div");
	
	
	public static By div_AllOpeningTab =  By.xpath("//li[@ng-repeat='tab in vmTab.tabs' and @ui-sref-active='active']");
	public static By lbl_TabName = By.xpath("//li[@ng-repeat='tab in vmTab.tabs' and @ui-sref-active='active']/a/div[2]/span[2]");
	
	public static By btn_CloseCurrentTab =  By.xpath("//li[@ng-repeat='tab in vmTab.tabs' and @class='ng-scope active']/a/div[last()]");
	
}
