 package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.commons.lang3.RandomStringUtils;
//every time whenever we upadte the logger class import the same package
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseClass {
	
	//this driver make static bcoz of ss use in "utilities\ExtentReportManager"
	//we make static bcoz this common driver use everywhere in project
	public static WebDriver driver;
	
	public Logger logger;  //Log4j
	
	public Properties p;
	
	@BeforeClass (groups = {"Sanity","Regression","Master","Datadriven"})
	@Parameters({"os","browser"})
	public void setup(String os, String br) throws IOException
	{
		//this is for config.properties file(loading config.properties file) 
		//here we use FileReader class but if we want the use use FileInputStream class also
		FileInputStream file = new FileInputStream("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);
		
		
		//this is for log file
		logger = LogManager.getLogger(this.getClass());
		
		
		//This code is for Selenium Grid concept
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			//os (os & browser name is coming from xml file so we add condition
			if(os.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("linux"))
			{
				capabilities.setPlatform(Platform.LINUX);
			}
			else if (os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No Matching os");
				return;
			}
			
			//browser
			switch (br.toLowerCase())
			{
			case "chrome" : capabilities.setBrowserName("chrome"); break;
			case "edge"   : capabilities.setBrowserName("MicrosoftEdge"); break;    //"MicrosoftEdge" add this exactly same name bcoz this is keyword od edge browser
			case "firefox": capabilities.setBrowserName("firefox"); break;
			default       : System.out.println("No Matching browser"); return;
			}
			
			//creating the driver and get the URL(launching the URL)
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
			
		}
		
		if (p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(br.toLowerCase())
			{
			case "chrome"     : driver = new ChromeDriver();break;
			case "edge"       : driver = new EdgeDriver(); break;
			case "firefox"    : driver = new FirefoxDriver(); break;
			default           : System.out.println("Invalid browser name..."); return;
			}
		}
		
		
		//Normally we follow this approach but we use grid thats why we use above approach
		//this is for parallel testing
/*		switch(br.toLowerCase())
		{
		case "chrome"     : driver = new ChromeDriver();break;
		case "edge"       : driver = new EdgeDriver(); break;
		case "firefox"    : driver = new FirefoxDriver(); break;
		default           : System.out.println("Invalid browser name..."); return;
		}
		*/
		
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(p.getProperty("appURL"));   //reading url from propertied file
		driver.manage().window().maximize();
		
	}
	
	@AfterClass (groups = {"Sanity","Regression","Master"})
	public void tearDown()
	{
		driver.quit();
	}
	
	//This is for Randomly generate the Name,Email,Password (Check TC_001_AccountRegistartionPage)
	public String randomString()   // this method automatically 5 character random string will be generated for email id
	{
		String generatedstring = RandomStringUtils.randomAlphabetic(5); //RandomStringUtils is not coming into the java, it is coming to common library 
		return generatedstring;
	}
	
	public String randomNumber()
	{
		String generatedNumber = RandomStringUtils.randomNumeric(10);
		return generatedNumber;
	}
	
	public String randomAlphaNumeric()
	{
		String generatedString = RandomStringUtils.randomAlphabetic(3);
		String generatednumber = RandomStringUtils.randomNumeric(3);
		return (generatedString+"@"+generatednumber);
	}
	
	//Capture the ScreenShot Method And add in Extent report
	//when this method is executed:- whenever our test method is got failed then this method will be executed
	//we add this method on "onTestfailuer" method
	public String captureScreen(String tname) throws IOException
	{
		//This line create the screenshot name every time new with timestamp  
		String timeStamp = new SimpleDateFormat("yyyMMddhhmmss").format(new Date());
		
		TakesScreenshot takescreentshot = (TakesScreenshot) driver;
		File sourceFile = takescreentshot.getScreenshotAs(OutputType.FILE);
		
		//we copy the source file into the target file with the tname+timeStamp+.png(tname_time.png) 
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\"+tname+ "_" +timeStamp + ".png ";
		File targetFile = new File (targetFilePath);
		
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;
	}
	
	
	

}
