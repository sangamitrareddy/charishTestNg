package qa.testng;

import java.io.IOException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import qa.testng.PageActions.HomePage_Actions;
import qa.testng.base.BaseClass;

public class HomePage_TC extends BaseClass {
	
HomePage_Actions HomePage_Actions= new HomePage_Actions(driver);

	
	@Test
	public final void tc_verifyPagetitle() {
		logger=report.createTest("Home Page validation");

		try {
			HomePage_Actions.get_pagetitle();
			//HomePage_Actions.verify_HomepageTitle();;
			HomePage_Actions.click_signIn();
			HomePage_Actions.verify_loginpageTitle();
			
			//logger.log(Status.PASS, MarkupHelper.createLabel("navigated to corect page", ExtentColor.GREEN));
		} catch (IOException e) {
			//logger.log(Status.FAIL, MarkupHelper.createLabel("navigated to Wrong page", ExtentColor.RED));
			System.out.println(e);;
		}
	}
	
}
