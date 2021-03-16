package qa.testng.Utilities;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import qa.testng.Utilities.TestUtil;
import qa.testng.Utilities.ExcelSheet;

import qa.testng.base.BaseClass;

public class CommonMethods extends BaseClass {
	public String text ="";
	public WebDriver childwindowTitle ;
	public String str="";
	
	public  String getPageTitle() {
		try {
		String pageTitle = driver.getTitle();
		System.out.println(pageTitle);
		logger.log(Status.PASS, MarkupHelper.createLabel("pageTitle :"+pageTitle, ExtentColor.GREEN));
		}
		catch (Exception e) {
		logger.log(Status.FAIL, MarkupHelper.createLabel("no title", ExtentColor.RED));
		}
		return null;
	}
	public void verifyPage(String str) {
		waitForPageLoad();
		try {
		if (!getPageTitle().contains(str)) {
			logger.log(Status.FAIL, MarkupHelper.createLabel("Page NOT loaded successfully", ExtentColor.RED));
			Assert.fail("specified page not loaded");}
		}catch(Exception e) {
			logger.log(Status.PASS, MarkupHelper.createLabel("Page loaded successfully", ExtentColor.GREEN));
		}
	}	
	public WebElement findElement(String locator) {
		try {
		if (driver.findElement(By.xpath(locator)).isDisplayed()) {
		}else {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("arguments[0].scrollIntoView(true);",locator); 
			driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT,TimeUnit.SECONDS);
			}			
		} catch (Exception e) {
		Assert.fail("Element not found");
		}
		return null;
	}
	public void checkElementDisplayed(String locator, String elementname) {
		try {
			if(driver.findElement(By.xpath(locator)).isDisplayed())
			logger.log(Status.PASS, MarkupHelper.createLabel(elementname+" present ", ExtentColor.GREEN));
		}catch(Exception e) {
			 logger.log(Status.FAIL, MarkupHelper.createLabel(elementname+" NOT present ", ExtentColor.RED));
			 Assert.fail("Element not found");
		}
	}
	public void mouseHover(String locator, String elementname) throws Exception {
		WebElement element= findElement(locator);
		try {
			findElement(locator);
			Actions actions = new Actions(driver);			
			actions.moveToElement(element).perform();
			logger.log(Status.PASS, MarkupHelper.createLabel("Hovered over "+elementname, ExtentColor.GREEN));
		 }catch(Exception e) {	
			 logger.log(Status.FAIL, MarkupHelper.createLabel("Hover over "+elementname+" is NOT-successfull ", ExtentColor.RED));
			 Assert.fail("Hover failed");
		 }
	}
	public void mouseHoverandClick (String locatorToHover, String locatorToClick, String elementname) {
		WebElement element = driver.findElement(By.xpath(locatorToHover));
		WebElement element2 = driver.findElement(By.xpath(locatorToClick));
		try {
			Actions actions = new Actions(driver);			
			actions.moveToElement(element).build().perform();
			actions.moveToElement(element2).click().perform();
			logger.log(Status.PASS, MarkupHelper.createLabel(elementname+" is clicked on ", ExtentColor.GREEN));
		 }catch(Exception e) {
			 logger.log(Status.FAIL, MarkupHelper.createLabel("UNsuccessfull attempt to click on "+elementname, ExtentColor.RED));
			 Assert.fail("Hover failed");
		 }
	}
	public void Clickaction(String locator, String elementname) {
	 	WebDriverWait wait= new WebDriverWait(driver,10);
		try {
			findElement(locator);
			//WebElement element = driver.findElement(By.xpath(locator));
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
			driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT,TimeUnit.SECONDS);
		element.click();		
		logger.log(Status.PASS, MarkupHelper.createLabel("Succesfully Clicked on "+elementname, ExtentColor.GREEN));		
	 }catch(Exception e) {
		e.printStackTrace();
		 logger.log(Status.FAIL, MarkupHelper.createLabel("Could not click "+elementname, ExtentColor.RED));		
		 Assert.fail("Could not Click");
	 }
	}	
	public void doubleclick (String locator, String elementname) {
		Actions actions = new Actions(driver);
		WebDriverWait wait= new WebDriverWait(driver,10);
		try {			
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		actions.doubleClick(element).perform();
		logger.log(Status.PASS, MarkupHelper.createLabel("Succesfully double Clicked on "+elementname, ExtentColor.GREEN));
	 }catch(Exception e) {
		 logger.log(Status.FAIL, MarkupHelper.createLabel("Could not double click "+elementname, ExtentColor.RED)); 
		 Assert.fail("Could not double Click");
	 }	
	}
	public void enterdata(String locator, String data, String field) {
		WebDriverWait wait= new WebDriverWait(driver,30);
		try {
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
			findElement(locator);
			element.sendKeys(data);			
		logger.log(Status.PASS, MarkupHelper.createLabel("Data "+" -- "+data+" -- "+"entered successfully in "+field, ExtentColor.GREEN));
	 }catch(Exception e) {
		 logger.log(Status.FAIL, MarkupHelper.createLabel("unsuccesfull attempt to enter data "+field, ExtentColor.RED));
		 Assert.fail("Could not input data");
		}
	}
	public void selectbyDropdownValue(String locator, String value, String field){
		try {
			findElement(locator);
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			Select element = new Select(driver.findElement(By.xpath(locator)));
			driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT,TimeUnit.SECONDS);
			element.selectByValue(value);
			jsExecutor.executeScript("arguments[0].scrollIntoView(true);",element);
			logger.log(Status.PASS, MarkupHelper.createLabel("Value - "+value+" selected successfully in "+field+ " field", ExtentColor.GREEN));
	}catch(Exception e) {
		logger.log(Status.FAIL, MarkupHelper.createLabel("unsuccesfull attempt to select- "+value+" in "+field+ " field", ExtentColor.RED));
		Assert.fail("Selection failed");
		}
		}

	public void multiple_checkbox_select( String Options) {
		try {
			System.out.println(Options);
			//Options="Summary,Status,SubCategory";
			for(int i=1;i<=Options.split(",").length;i++)
			{
				if(!(driver.findElement(By.xpath("//p[text()='"+Options.split(",")[i]+"']/..//input[@class='check']")).isSelected()))
				{
					driver.findElement(By.xpath("//p[text()='"+Options.split(",")[i]+"']/..//input[@class='check']")).click();
					}
				}
			logger.log(Status.PASS, MarkupHelper.createLabel("options - "+Options+ " are selected successfully", ExtentColor.GREEN));
			}catch(Exception e) {
				logger.log(Status.FAIL, MarkupHelper.createLabel("options - "+Options+ " are NOT selected successfully", ExtentColor.RED));
				Assert.fail("Selection failed");
				}
	}
	public void movetoElement (String locator) throws InterruptedException {
		WebElement knownElement=driver.findElement(By.xpath(locator));
		Actions builder = new Actions(driver);
		builder.moveToElement(knownElement,0,0).build().perform();
		Thread.sleep(2000);
		}
	public void dragandDrop (String sourceLocator, String targetLocator, String sourceicon) {
		try {
			driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT,TimeUnit.SECONDS);
			WebElement source = driver.findElement(By.xpath(sourceLocator));
			WebElement target = driver.findElement(By.xpath(targetLocator));
			Actions actions = new Actions(driver);
			Action dragAndDrop =actions.clickAndHold(source).moveToElement(target).release(target).build();
			Thread.sleep(1000);
			dragAndDrop.perform();
			logger.log(Status.PASS, MarkupHelper.createLabel("Succesfull attempt to drag - "+sourceicon, ExtentColor.GREEN));
			} catch (Exception e) {
				logger.log(Status.FAIL, MarkupHelper.createLabel("unsuccesfull attempt to drag - "+sourceicon, ExtentColor.RED));
				Assert.fail("Could not drag to the required position");
				}
	}
	public void selectValuefromDropDown_Xpath(String value) {
		try {
			driver.findElement(By.xpath("//*[text()='"+value+"']")).click();
			logger.log(Status.PASS, MarkupHelper.createLabel("Succesfull attempt to select - "+value, ExtentColor.GREEN));	
			}
		catch (Exception e){
			logger.log(Status.FAIL, MarkupHelper.createLabel("unsuccesfull attempt to select - "+value, ExtentColor.RED));
			Assert.fail("Selection failed");
			}
	}
	public void getListofElements (String locator) {
		try {
			List<WebElement> Alloptions =	driver.findElements(By.xpath(locator));
			for(WebElement Alloptionvalues : Alloptions)
				System.out.println(Alloptionvalues.getText());
			}
		catch (Exception e) {	}
		}
	public void uploadfile( String filename) {
		try {
			driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT,TimeUnit.SECONDS);
			StringSelection ss = new StringSelection(System.getProperty("user.dir")+ "\\TestFiles\\"+ filename);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
			Robot robot = new Robot();
			robot.delay(2500);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.delay(500);
			logger.log(Status.PASS, MarkupHelper.createLabel("Succesfull upload of file ", ExtentColor.GREEN));
			}
		catch (Exception e) {
			logger.log(Status.FAIL, MarkupHelper.createLabel("Unsuccessfull attempt to upload file", ExtentColor.RED));	
			Assert.fail("Upload failed");
		}
	}
	public String getTextAtLocation (String locator) {
		WebDriverWait wait= new WebDriverWait(driver,10);
		try {
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
			findElement(locator);
			driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
			text= element.getAttribute("innerHTML"); 
			System.out.println(text);
			logger.log(Status.PASS, MarkupHelper.createLabel("retrieved the text - "+text, ExtentColor.GREEN));
			}
		catch(Exception e) {
			logger.log(Status.FAIL, MarkupHelper.createLabel("Unsuccessfull attempt retrieve text", ExtentColor.RED));	
			Assert.fail("text retrieval failed");
			}
		return text.trim();
		}
	public void comparedata(String locator, String file, String sheetname, String row) throws IOException {
		String actualVal=getTextAtLocation(locator);
		String expectedVal=ExcelSheet.readexcel(prop.getProperty(file),sheetname,row);
		if(actualVal.equalsIgnoreCase(expectedVal)) {
			logger.log(Status.PASS, MarkupHelper.createLabel("Data entered and data displayed are same", ExtentColor.GREEN));
			}else {
				logger.log(Status.FAIL, MarkupHelper.createLabel("Data entered and data displayed are NOT same", ExtentColor.RED));
				}
		}
	public void searchforID(){
		String value=text;
		System.out.println(value);
		try {
			driver.findElement(By.xpath("//div[text()='"+value+"']")).isDisplayed();
			logger.log(Status.PASS, MarkupHelper.createLabel("The recently raised request is recorded in to the table", ExtentColor.GREEN));
			}
		catch(Exception e) {
		logger.log(Status.FAIL, MarkupHelper.createLabel("The recently raised request is NOT recorded in to the table", ExtentColor.RED));
		Assert.fail("raised requeust not found");
		}
	}
	public void Select_request_from_table_and_select() {
		String requestID=text;
		try {
			WebElement IDcheckbox=driver.findElement(By.xpath("//input[@id='"+requestID+"']"));
			IDcheckbox.click();
			logger.log(Status.PASS, MarkupHelper.createLabel("The recently raised request is selected", ExtentColor.GREEN));
			}catch (Exception e) {
				logger.log(Status.PASS, MarkupHelper.createLabel("Could not select the recently raised request", ExtentColor.RED));
				Assert.fail("Could not select the recently raised request");
			}
		}
	public void Sortcontents_ascendingOrder(String locator) {
		ArrayList<String> obtainedList = new ArrayList<String>(); 
		List<WebElement> elementList= driver.findElements(By.xpath(locator));
		for(WebElement we:elementList){
			obtainedList.add(we.getText());
			}
		ArrayList<String> sortedList = new ArrayList<String>();
		for(String s:obtainedList) {
			sortedList.add(s);
			}
		try {
			Collections.sort(sortedList);
			Assert.assertTrue(sortedList.equals(obtainedList));
			logger.log(Status.PASS, MarkupHelper.createLabel("The values in the table are sorted properly", ExtentColor.GREEN));
			}catch(Exception e) {
				logger.log(Status.FAIL, MarkupHelper.createLabel("The values in the table are NOT sorted properly", ExtentColor.RED));
				}
		}
	public void Sortcontents_decendingOrder(String locator) {
		ArrayList<String> obtainedList = new ArrayList<String>(); 
		List<WebElement> elementList= driver.findElements(By.xpath(locator));
		for(WebElement we:elementList){
			obtainedList.add(we.getText());
			}
		ArrayList<String> sortedList = new ArrayList<String>();
		for(String s:obtainedList){
			sortedList.add(s);
			}
		try {
			Collections.sort(sortedList,Collections.reverseOrder());
			Assert.assertTrue(sortedList.equals(obtainedList));
			logger.log(Status.PASS, MarkupHelper.createLabel("The values in the table are sorted properly", ExtentColor.GREEN));
			}catch(Exception e) {
				logger.log(Status.FAIL, MarkupHelper.createLabel("The values in the table are NOT sorted properly", ExtentColor.RED));
				}
		}
	public void SortDate_ascendingOrder(String locator) {
		ArrayList<String> obtainedList = new ArrayList<String>();
		List<WebElement> elementList= driver.findElements(By.xpath(locator));
		ArrayList<String> sortedList = new ArrayList<String>();
		for(String s:obtainedList){
			sortedList.add(s);
			}
		try {
			Collections.sort(sortedList);
			Assert.assertTrue(sortedList.equals(obtainedList));
			logger.log(Status.PASS, MarkupHelper.createLabel("The values in the table are sorted properly", ExtentColor.GREEN));
			}catch(Exception e) {
				logger.log(Status.FAIL, MarkupHelper.createLabel("The values in the table are NOT sorted properly", ExtentColor.RED));
				}
	}
	public void control_click(String locator) {
		try {
			WebElement element = driver.findElement(By.xpath(locator));
			Actions actions = new Actions(driver);
			actions.keyDown(Keys.CONTROL).click(element).keyUp(Keys.CONTROL).build().perform();
			String MainWindow=driver.getWindowHandle();	
			Set<String> s1=driver.getWindowHandles();
			Iterator<String> i1=s1.iterator();
			if(i1.hasNext())
			{
				String ChildWindow=i1.next();
				if(!MainWindow.equalsIgnoreCase(ChildWindow))
				{
					logger.log(Status.PASS, MarkupHelper.createLabel("opened in new tab", ExtentColor.GREEN));
					}
				}
			}catch(Exception e) {
				logger.log(Status.FAIL, MarkupHelper.createLabel("Could not open in new tab", ExtentColor.RED));
				}
		}
	public void Childwindow_handle(String locator, String titlename) {
		WebElement element = driver.findElement(By.xpath(locator));
		element.click();
		try {
			String MainWindow=driver.getWindowHandle();
			Set<String> s1=driver.getWindowHandles();
			Iterator<String> i1=s1.iterator();
			while(i1.hasNext())
			{
				String ChildWindow=i1.next();
				if(!MainWindow.equalsIgnoreCase(ChildWindow))
				{
					driver.switchTo().window(ChildWindow);
					childwindowTitle = driver.switchTo().window(ChildWindow);
					childwindowTitle.getTitle();
					}
				}
			logger.log(Status.PASS, MarkupHelper.createLabel(childwindowTitle +" is opened", ExtentColor.GREEN));
			}catch(Exception e) {
				logger.log(Status.FAIL, MarkupHelper.createLabel("Child window NOT opened", ExtentColor.RED));
				Assert.fail("Selection failed");
			}
	}
	public void isFileDownloaded(String downloadPath, String fileName) {
		try {
			File dir = new File(downloadPath);
			File[] dir_contents = dir.listFiles();
			for (int i = 0; i < dir_contents.length; i++) {
				if (dir_contents[i].getName().contains(fileName))
					logger.log(Status.PASS, MarkupHelper.createLabel("File downloaded successfully", ExtentColor.GREEN));
				}
			}catch(Exception e) {
				logger.log(Status.FAIL, MarkupHelper.createLabel("File NOT downloaded successfully", ExtentColor.RED));
				}
		}
	/*    public void Check_downloaded_file(String downloadPath) {
		    File getLatestFile = isFileDownloaded(downloadPath);
		    String fileName = getLatestFile.getName();
	    }*/

}
