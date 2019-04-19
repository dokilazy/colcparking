package interfaces;
import org.openqa.selenium.By;
public class I_AIMS_Settings {
	
	
	public static By tab_IncidentTypes = By.xpath("//ul/li/a[@ui-sref='root.settings.incidentTypes']");
	public static By tab_Categories = By.xpath("//li/a[@ui-sref='root.settings.categories']");
	public static By tab_incidentTypeAssignment = By.xpath("//li/a[@ui-sref='root.settings.incidentTypeAssignment']");
	public static By tab_customFields = By.xpath("//li/a[@ui-sref='root.settings.customFields']");
	
	
	
	//--- Incident Type page -----
	public static By btn_addNewType = By.id("new-incident-type");
	public static By txt_InputName = By.xpath("//input[@name='incidentTypeName']");
	public static By txt_incidentCode = By.xpath("//input[@name='incidentTypeCode']");
	public static By btn_Save = By.xpath("//button[@ng-click='vmAddIncidentType.save(incidentTypeAddingForm.$valid);']");
	public static By btn_SaveEdit = By.xpath("//div[@class='navbar-item']/button");
	public static By txt_FilterIncidentType = By.xpath("//input[@ng-model='vmIncidentTypes.filterString']");
	public static By btn_EditIncidentType =  By.xpath("//div[@id='sidebar-right']/div[2]/div/button");
	public static By txt_editIncidentType =  By.xpath("//input[@ng-model='vmIncidentTypesEdit.incidentType.description']");
	
	
	//---Incident Catalog page
	public static By ol_UncategorizedList = By.xpath("//ol[@data-type='uncategorized' and @ng-model='item.items']");
	public static By li_EndItem = By.xpath("//ol[@data-type='uncategorized' and @ng-model='item.items']/li[last()]");
	public static By btn_addNewCategory = By.xpath("//button[@ng-click='vmIncidentTypeAssignment.addNewCategory();']");
	public static By ol_CategoryTree = By.xpath("//div[@id='tree-root-categorized']/ol"); 
	public static By btn_SaveCategory = By.xpath("//button[@ng-click='vmIncidentTypeAssignment.saveCategory()']");
	
	
	
	
	
}
