package qa.testng.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import qa.testng.Utilities.TestUtil;

public class BaseClass {
	public static WebDriver driver;
	protected static String timestamp = new SimpleDateFormat("yy.MM.dd.HH.mm.ss").format(new Date());
	protected static String filename = System.getProperty("user.dir")+"\\Reports\\reports"+timestamp+".html";
	public static ExtentReports report;
	public static ExtentTest logger;
	public static Properties prop;
	

	 static {
		 ExtentHtmlReporter extentReport = new ExtentHtmlReporter(filename);
		 report = new ExtentReports();
		 report.attachReporter(extentReport);
		 extentReport.config().setReportName("Automation Reports");
		 extentReport.config().setTheme(Theme.STANDARD);
		 extentReport.config().setDocumentTitle("Automation reports - INDIGO Travels");
		 extentReport.config().setTimeStampFormat("yy.MM.dd.HH.mm.ss");
		 report.setReportUsesManualConfiguration(true);		 
	 }
		
	// to pick the properties file
		public BaseClass() 
		{
			try {
				prop= new Properties();
				FileInputStream ip= new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\qa\\testng\\Configuration\\config.properties");
				prop.load(ip);
			}
			catch(FileNotFoundException e)
			{
			e.printStackTrace();	
			}
			catch(IOException e)
			{
			e.printStackTrace();	
			}
		}	
		
	// to initialize the browser 
		public static void intialization()
		{ 
	        String browserName =(prop.getProperty("Browser"));
			System.out.println("Opening application in"+" "+browserName+" "+"browser");
				
			if (browserName.equalsIgnoreCase("chrome")) 
				{			
				String browserpath =(prop.getProperty("chromepath"));
				System.setProperty("webdriver.chrome.driver", browserpath); // to invoke the browser through the application file present in local system
				driver = new ChromeDriver();
	}
			else if (browserName.equalsIgnoreCase("IE"))
				{	
					String browserpath =(prop.getProperty("IEpath"));
					System.out.println(browserpath);
					System.setProperty("webdriver.ie.driver", browserpath);
					driver = new InternetExplorerDriver();				
				}	
			else if (browserName.equalsIgnoreCase("firefox"))
				{			
					String browserpath =(prop.getProperty("firefoxpath"));
					System.setProperty("webdriver.firefox.marionette", browserpath);
					driver = new FirefoxDriver();
				}
			else
				{
					System.out.println("No browser found");
				}		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT,TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT,TimeUnit.SECONDS);	
		driver.get(prop.getProperty("URL"));// hits URL
		}	
		
	// open the browser	
		@BeforeClass
		public void setup() {	
		logger=report.createTest("Browser initialisation");
		try {
		intialization();	
		logger.log(Status.PASS, MarkupHelper.createLabel("Browser initialised successfully", ExtentColor.GREEN));
		 }catch(Exception e) {
        logger.log(Status.FAIL, MarkupHelper.createLabel("Browser initialisation unsuccessfull", ExtentColor.RED)); 
		 }
		}
		
	// closing the browser
		 @AfterClass 
		 public void teardown() throws IOException	 
		 {
			 driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT,TimeUnit.SECONDS); 
		logger=report.createTest("Browser closing");
		 try {
			 driver.quit();
		logger.log(Status.PASS, MarkupHelper.createLabel("Browser closed successfully", ExtentColor.GREEN));
		 }catch(Exception e) {
		logger.log(Status.FAIL, MarkupHelper.createLabel("Browser closing unsuccessfull", ExtentColor.RED)); 
		 }
		report.flush();
		 }
		// Screenshot for failed testcase
			@AfterMethod
			public void Screenshot(ITestResult result) throws IOException{
			if(ITestResult.FAILURE==result.getStatus())
			{
			try
			{
				String timeStamp;
				timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
				File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(src, new File(System.getProperty("user.dir")+"\\Screenshots\\"+result.getName()+" "+timeStamp+"screenshot.png"));
				System.out.println("TC - "+result.getName()+" - failed - Screenshot taken");
				}
			catch (Exception e){
				System.out.println(" Could not take Screenshot ");
				}
			}
			}
	// Page load 
			 public void waitForPageLoad() {
					try {
						for (int i = 1; i <= 60; i++) {
							i++;
							//System.out.println("loading");
							if (((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"))
								break;
							Thread.sleep(500);
						}
					} catch (Exception e) {
						Assert.fail("page not loaded");
					}
				}
		 
}

