package practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class BrokenLinks {
 
WebDriver driver;
@Test
public void brokenlink() throws IOException
{	 	 
 	File f=new File("./src/test/resources/configuration");
	FileInputStream fis=new FileInputStream(f);
	Properties pobj=new Properties();
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
	driver.get(pobj.getProperty("url"));
/**	driver.findElement(By.name("emailid")).sendKeys("sureshbabuemp@gmail.com");
	driver.findElement(By.id("pword")).sendKeys("ZPMHQHIA");
	driver.findElement(By.xpath("//button[contains(@class,'green_btn')]")).click();**/
	WebDriverWait wait = new WebDriverWait(driver, 10);
	wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("a")));
	//getting all links in webpage
     List<WebElement> links=driver.findElements(By.tagName("a"));
     //count of links in webpage
     System.out.println(links.size());
     for (WebElement link : links) {
    	 //getting the value in href attribute
         String href = link.getAttribute("href");
         if (href != null && !href.isEmpty()) {
        	 try {
        		 //getting url from href attribute
        		 URL url=new URL(href);
        		// creating connection using url object
        		  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        		  //connecting
        		  connection.connect();
        		  //getting response code
        		  int responseCode=connection.getResponseCode();
                 if (responseCode > 400)
                 {
                     System.out.println("Broken link: " + href + " (Status code: " + responseCode + ")"+connection.getResponseMessage());
                     connection.disconnect();
             }} catch (Exception e) {
                 System.out.println("Error checking link: " + href + " (" + e.getMessage() + ")");
             }
        	 
         }
     }
 }
}

