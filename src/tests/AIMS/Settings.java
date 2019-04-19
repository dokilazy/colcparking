package tests.AIMS;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import interfaces.I_AIMS_Home;
import interfaces.I_AIMS_Settings;
import libraries.Constants;
import libraries.TestConfig.Owner;
import libraries.TestConfig.TestSuite;
import libraries.ValueList.AIMSMessage;
import libraries.generalFunctions.Functions;
import libraries.generalFunctions.Mouse;
import libraries.productFunctions.F_AIMS;
import libraries.productFunctions.F_Navigation;
import libraries.verificationFunctions.F_AIMSVerification;
import libraries.verificationFunctions.F_GeneralVerification;
import tests.DefaultAnnotations;

public class Settings extends DefaultAnnotations {

	@Test(groups = { Owner.VuNguyen, TestSuite.BVT })
	public void Verify_Add_new_and_update_Incident_type() {

		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_Navigation.goToPage(Browser, I_AIMS_Home.tab_Settings);
		String name = Functions.randomString("TestType_", "");
		String code = Functions.randomNumberString(8);
		F_AIMS.AddNewIncident(Browser, name, code);

		F_GeneralVerification.verifyElementExist(Browser, "Notification Message", I_AIMS_Home.div_SuccessMessage,
				Result);
		Assert.assertTrue(Result.Result, Result.Message);

		Browser.enter(I_AIMS_Settings.txt_FilterIncidentType, name);
		Mouse.FreeMouse(Browser);
		Functions.waitForSeconds(2);
		List<String> firstRow = Functions.getFirstRow(Browser);

		F_GeneralVerification.verifyElementValue(Browser, "Incident Type name", firstRow.get(0), name, Result);
		F_GeneralVerification.verifyElementContent(Browser, "Incident Code", code, firstRow.get(1), Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_Navigation.goToPage(Browser, I_AIMS_Settings.tab_incidentTypeAssignment);
		Browser.scrollToEle(I_AIMS_Settings.li_EndItem);

		List<String> list = Functions.getDropdownList(Browser, I_AIMS_Settings.ol_UncategorizedList, "li");
		int flag = 0;
		String msg = "";
		for (String s : list) {
			msg += s + "\t";
			if (s.contains(name) == true) {
				flag = 1;
				break;
			}
		}
		if (flag == 0) {
			Result.SetResult(false);
			Result.SetMessage("Incident type list:" + msg + "not contain " + name);
		}

		Assert.assertTrue(Result.Result, Result.Message);

		// Update Incident type
		F_Navigation.RefreshPage(Browser);
		Browser.click(I_AIMS_Settings.tab_IncidentTypes);
		Browser.enter(I_AIMS_Settings.txt_FilterIncidentType, name);
		Mouse.FreeMouse(Browser);
		Browser.click(I_AIMS_Settings.tab_IncidentTypes);
		Functions.waitForSeconds(4);
		
		Browser.click(By.xpath("//tbody/tr[1]/td"));
		Browser.waitAndClick(I_AIMS_Settings.btn_EditIncidentType);
		name = name + "_up";
		
		Browser.enter(I_AIMS_Settings.txt_editIncidentType, name);
		Browser.click(I_AIMS_Settings.btn_SaveEdit);
		
		Browser.waitForElementVisible(I_AIMS_Home.div_SuccessMessage);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage, AIMSMessage.UpdateIncidentTypeSuccess, Result);
		F_AIMS.closeCurrentTab(Browser);

		F_Navigation.RefreshPage(Browser);
		F_Navigation.goToPage(Browser, I_AIMS_Home.tab_Settings);
		Browser.click(I_AIMS_Settings.tab_IncidentTypes);

		Browser.enter(I_AIMS_Settings.txt_FilterIncidentType, name);
		Mouse.FreeMouse(Browser);
		Functions.waitForSeconds(2);
		List<String> firstRow2 = Functions.getFirstRow(Browser);

		F_GeneralVerification.verifyElementValue(Browser, "Incident Type name", firstRow2.get(0), name, Result);
		F_GeneralVerification.verifyElementContent(Browser, "Incident Code", code, firstRow2.get(1), Result);
		Assert.assertTrue(Result.Result, Result.Message);

		F_Navigation.goToPage(Browser, I_AIMS_Settings.tab_incidentTypeAssignment);
		Browser.scrollToEle(I_AIMS_Settings.li_EndItem);
		List<String> updatedlist = Functions.getDropdownList(Browser, I_AIMS_Settings.ol_UncategorizedList, "li");

		for (String s : updatedlist) {
			if (s.contains(name) == true) {
				return;
			}
		}
		Result.SetResult(false);
		Result.SetMessage("New updated incident type:" + name + " does not in List");
		Assert.assertTrue(Result.Result, Result.Message);

	}

	@Test
	public void Verify_Add_new_Category_than_delete() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_Navigation.goToPage(Browser, I_AIMS_Home.tab_Settings);
		F_Navigation.goToPage(Browser, I_AIMS_Settings.tab_incidentTypeAssignment);

		Browser.click(I_AIMS_Settings.btn_addNewCategory);

		String categoryName = "New Category";
		WebElement Categories = Browser.Driver.findElement(I_AIMS_Settings.ol_CategoryTree);

		WebElement newdefaultCategory = Categories.findElement(By.xpath("//li[last()]/div/input"));

		String defaultName = newdefaultCategory.getAttribute("value");

		F_GeneralVerification.verifyElementValue(Browser, "Default CategoryName", defaultName, categoryName, Result);

		String randomName = Functions.randomText();

		newdefaultCategory.clear();
		newdefaultCategory.sendKeys(randomName);

		Browser.click(I_AIMS_Settings.btn_SaveCategory);
		//F_GeneralVerification.verifyElementExist(Browser, "Notification Message", I_AIMS_Home.div_SuccessMessage,Result);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.CreateCategorySuccess, Result);

		F_Navigation.RefreshPage(Browser);

		F_AIMSVerification.verifyCatelogExist(Browser, randomName, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		F_AIMS.deleteCategory(Browser, randomName);

		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.DeleteCategorySuccess, Result);

		By pcategory = By.xpath("//li/div/span[contains(text(),'" + randomName + "')]");
		F_GeneralVerification.verifyElementNotExist(Browser, randomName, pcategory, Result);
	
		Assert.assertTrue(Result.Result, Result.Message);
	}

	
	@Test
	public void Verify_Rename_a_category() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		F_Navigation.goToPage(Browser, I_AIMS_Home.tab_Settings);
		F_Navigation.goToPage(Browser, I_AIMS_Settings.tab_incidentTypeAssignment);
		
		//--- add a new Cat ---
		String randomName = F_AIMS.createNewCategory(Browser);
		F_AIMSVerification.verifyCatelogExist(Browser, randomName, Result);

		Assert.assertTrue(Result.Result, Result.Message);
		
		//---- Rename ---
		WebElement category = Browser.Driver.findElement(
				By.xpath("//div[@id='tree-root-categorized']/ol/li/div/span[contains(text(),'" + randomName + "')]"));
		Mouse.RightclickOnElement(Browser, category);
		String updateName = Functions.randomText();

		WebElement Categories = Browser.Driver.findElement(I_AIMS_Settings.ol_CategoryTree);
		WebElement inputFieldEle = Categories.findElement(By.xpath("//li/div/input"));
		inputFieldEle.clear();
		inputFieldEle.sendKeys(updateName);
		Browser.click(I_AIMS_Settings.btn_SaveCategory);
		F_GeneralVerification.verifyElementExist(Browser, "Notification Message", I_AIMS_Home.div_SuccessMessage,
				Result);
		F_Navigation.RefreshPage(Browser);
		F_AIMSVerification.verifyCatelogExist(Browser, updateName, Result);
		Assert.assertTrue(Result.Result, Result.Message);

		//---- Delete Category-----
		F_AIMS.deleteCategory(Browser, updateName);
	}

	@Test
	public void Verify_Dragdrop_IncidentType_to_Category() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);

		F_Navigation.goToPage(Browser, I_AIMS_Home.tab_Settings);
		F_Navigation.goToPage(Browser, I_AIMS_Settings.tab_incidentTypeAssignment);

		String categoryName = F_AIMS.createNewCategory(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.CreateCategorySuccess, Result);
		Browser.waitForElementNotVisible(I_AIMS_Home.div_SuccessMessage);
		
		F_AIMSVerification.verifyCatelogExist(Browser, categoryName, Result);

		WebElement targetCat = Browser.Driver.findElement(
				By.xpath("//div[@id='tree-root-categorized']/ol/li/div/span[contains(text(),'" + categoryName + "')]"));

		String lableName = targetCat.getText();
		F_GeneralVerification.verifyElementContent(Browser, "Incident Name", lableName, "(0)", Result);

		List<WebElement> incidentType = Functions.getElementDropdownList(Browser, I_AIMS_Settings.ol_UncategorizedList,
				"li");

		// Get random 1 in top 10 in list
		int index = Functions.randomInterger(0, 5);
		WebElement source = incidentType.get(index);
		String IncidentTypeName = source.getText();
		
		
		//--- Drag and drop to Category
		Mouse.DragDropElement(Browser, source, targetCat);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.AssignIncidentTypeSuccess, Result);
		Browser.waitForElementNotVisible(I_AIMS_Home.div_SuccessMessage);

		
		// ----- Check Incident type moved success---
		targetCat = Browser.Driver.findElement(
				By.xpath("//div[@id='tree-root-categorized']/ol/li/div/span[contains(text(),'" + categoryName + "')]"));
		lableName = targetCat.getText();
		F_GeneralVerification.verifyElementContent(Browser, "Incident Name", lableName, "(1)", Result);

		By assigment = By.xpath("//div[@id='tree-root-categorized']/ol/li/div/span[contains(text(),'" + categoryName
				+ "')]/../following-sibling::ol/li/div");
		
		F_GeneralVerification.verifyElementExist(Browser, "assigment Incident Type", assigment, Result);
		
		Assert.assertTrue(Result.Result, Result.Message);

		//--- Check in Incident Type list
		List<String> list = Functions.getDropdownList(Browser, I_AIMS_Settings.ol_UncategorizedList, "li");
		for (String s : list) {
			if (s.contains(IncidentTypeName) == true) {
				Result.SetResult(false);
				Result.SetMessage("Incident type list contains " + IncidentTypeName);
			}
		}
		Assert.assertTrue(Result.Result, Result.Message);

		// -----Delete Category which having an incident type----
		F_AIMS.deleteCategory(Browser, categoryName);
		
		//--- Check Error Message displays--
		
		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_ErrorMessage,
				AIMSMessage.DeleteCategoryError, Result);
		
		By pcategory = By
				.xpath("//div[@id='tree-root-categorized']/ol/li/div/span[contains(text(),'" + categoryName + "')]");
		F_GeneralVerification.verifyElementExist(Browser, categoryName, pcategory, Result);

		Assert.assertTrue(Result.Result, Result.Message);

		// Move back to Uncategorized then delete category
		F_AIMS.extendCatItems(Browser, categoryName);
		WebElement sourceEle = Browser.Driver.findElement(assigment);
		
		WebElement UncategorizedEle = Browser.captureInterface(By.xpath("//li[@ng-repeat='item in vmIncidentTypeAssignment.uncategorized']/div"));
		Mouse.DragDropElement(Browser, sourceEle, UncategorizedEle);
		F_GeneralVerification.verifyElementExist(Browser, "Notification Message", I_AIMS_Home.div_SuccessMessage,
				Result);
		Browser.waitForElementNotVisible(I_AIMS_Home.div_SuccessMessage);
		Functions.waitForSeconds(1);
		
		F_AIMS.deleteCategory(Browser, categoryName);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.DeleteCategorySuccess, Result);
		Assert.assertTrue(Result.Result, Result.Message);
	}

	@Test
	public void Verify_Create_Subcategory() {
		F_Navigation.loginAIMS(Browser, Constants.GlobalUsername, Constants.Password);
		
		F_Navigation.goToPage(Browser, I_AIMS_Home.tab_Settings);
		F_Navigation.goToPage(Browser, I_AIMS_Settings.tab_incidentTypeAssignment);
		
		//--- Create new category ---
		String categoryName  = F_AIMS.createNewCategory(Browser);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.CreateCategorySuccess, Result);
		
		F_AIMSVerification.verifyCatelogExist(Browser, categoryName, Result);
	
		Assert.assertTrue(Result.Result, Result.Message);
		
		F_Navigation.RefreshPage(Browser);
		F_AIMS.extendCatItems(Browser, categoryName);
		
		//---- Add new child category  ----
		String subcateName = F_AIMS.addnewSubcategory(Browser, categoryName);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.CreateCategorySuccess, Result);
		
		F_AIMSVerification.verifyCatelogExist(Browser, subcateName, Result);
		Assert.assertTrue(Result.Result, Result.Message);
		
		//--- Delete all --
		Browser.waitForElementNotVisible(I_AIMS_Home.div_SuccessMessage);
		F_AIMS.deleteCategory(Browser, subcateName);
		F_GeneralVerification.verifyNoticationMessage(Browser, I_AIMS_Home.div_SuccessMessage,
				AIMSMessage.DeleteCategorySuccess, Result);
		F_AIMS.deleteCategory(Browser, categoryName);
		Assert.assertTrue(Result.Result, Result.Message);
	}
	
	
	
	
}
