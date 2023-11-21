package practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
 
public class Task {
	WebDriver driver;
	Properties pobj;
	@BeforeMethod
	public void launchbrowser() throws IOException
	{	 	 
	 	File f=new File("./src/test/resources/configuration");
		FileInputStream fis=new FileInputStream(f);
		pobj=new Properties();
		pobj.load(fis); 
		String browser=pobj.getProperty("browser");
		if(browser.equalsIgnoreCase("chrome"))
		{
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\pc\\Downloads\\chromedriver-win64 (1)\\chromedriver-win64\\chromedriver.exe");
	        driver=new ChromeDriver();
		}
		else if (browser.equalsIgnoreCase("Edge")) {

			System.setProperty("webdriver.edge.driver",
					"C:\\Users\\pc\\Desktop\\selenium\\edgedriver_win64(1)\\msedgedriver.exe");

			driver = new EdgeDriver();

		}
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20,TimeUnit.SECONDS);
		driver.get(pobj.getProperty("URL"));
		driver.manage().timeouts().pageLoadTimeout(20,TimeUnit.SECONDS);
	}
		@Test
		public void login() throws IOException
		{
		driver.findElement(By.id("txt-username")).sendKeys(pobj.getProperty("username"));
		driver.findElement(By.id("txt-password")).sendKeys(pobj.getProperty("password"));
		driver.findElement(By.id("btn-login")).click();
		Assert.assertEquals(driver.getCurrentUrl(),"https://katalon-demo-cura.herokuapp.com/#appointment");
	}
	@Test(dataProvider="readdata",dataProviderClass=Task.class)
	public void Dologin(String username,String password)
	{
		driver.findElement(By.id("txt-username")).sendKeys(username);
		driver.findElement(By.id("txt-password")).sendKeys(password);
		driver.findElement(By.id("btn-login")).click();
		Assert.assertEquals(driver.getCurrentUrl(),"https://katalon-demo-cura.herokuapp.com/#appointment");
	}
	@DataProvider
	public Object[][] readdata() throws EncryptedDocumentException, IOException
	{
		return Readdata.readDataFromExcel("Sheet1");
		
	}
	@AfterMethod
		    public void tearDown(ITestResult result) throws IOException {
		if(result.getStatus()==ITestResult.SUCCESS)
		{
			System.out.println(result.getName()+"successfull");
		}
		else if (result.getStatus() == ITestResult.FAILURE) {
		     TakesScreenshot ts = (TakesScreenshot) driver;
	         File source = ts.getScreenshotAs(OutputType.FILE);
             String methodName = result.getName();
             StringBuilder parametersString = new StringBuilder();
             Object[] parameters = result.getParameters();
             for (Object param : parameters) {
                 parametersString.append("_").append(param.toString());
             }
	         String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());    
	         File destination =new File("./screenshots/"+ methodName+"_"+timeStamp+"_"+parametersString.toString()+ ".png");
	         FileUtils.copyFile(source,destination);
	}
	         driver.quit();
    }
	 
}
	 
