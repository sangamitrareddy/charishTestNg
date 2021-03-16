package qa.testng.PageActions;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import qa.testng.Utilities.CommonMethods;
import qa.testng.Utilities.ExcelSheet;
import qa.testng.PageObjects.Homepage;
import qa.testng.base.BaseClass;

public class HomePage_Actions extends Homepage {
	
	 CommonMethods CommonMethod;
	 String DataTable_filepath=prop.getProperty("excelpath");

	public HomePage_Actions(WebDriver driver) {
		BaseClass.driver=driver;
		 CommonMethod = new CommonMethods();
	}
	
	public void get_pagetitle() {
		CommonMethod.getPageTitle();		
	}
	public void verify_HomepageTitle() throws IOException {
		CommonMethod.verifyPage(ExcelSheet.readexcel(DataTable_filepath, "SignIn", "PageTitle"));
	}
	public void click_signIn() {
		CommonMethod.Clickaction(Signbutton, "Sign In button");
	}
	public void verify_loginpageTitle() throws IOException {
		CommonMethod.verifyPage(ExcelSheet.readexcel(DataTable_filepath, "SignIn", "PageTitle"));
	}
	
}
