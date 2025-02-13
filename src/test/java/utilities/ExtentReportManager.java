package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repName;
	
	public void onStart (ITestContext testContext)
	{
		//create data time stemp
	/*	SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt = new Date();
		String currentdatetimestamp = df.format(dt);
		*/
		
		            //OR(above 3 statement combine in single statement both are the same
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());  //time stamp
		
		//This is report name (Test-Report-timestem.html)
		repName = "Test-Report-" +timeStamp +".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);    //specify location of the report name
		
		sparkReporter.config().setDocumentTitle("opencart Autoamtion Report");  //Title of Report
		sparkReporter.config().setReportName("opencart Functional Testing");    //Name of the Report
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		//These information are project related 
		extent.setSystemInfo("Application", "opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		
		//tester name and environment name capture
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		//Capture the Operating System name from xml file
		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		//Capture the Browser name from xml file
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		
		//getCurrentXmlTest( this method return the current xml file we run
		//getIncludedGroups() this method is return current include groups in grouping xml file and display in the report 
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty())   //check the in xml file groups are available are not if available the add information in the report
		{
			extent.setSystemInfo("Groups", browser);
		}
		
	}
	
	public void onTestSuccess(ITestResult result)
	{
		//getTestClass() capture the class name not the method name (ex. TC_OO1_AccountRegistrationPage))
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());   //to displayed groups in report
		test.log(Status.PASS, result.getName()+"got successfully executed");
		
	}
	
	public void onTestFailure (ITestResult result)
	{
		//capture the class name and create a new entry on report
		test = extent.createTest(result .getTestClass().getName());
		//capture the test method which we are executed and from that test method getting the groups
		//and those groups will attached to report as a category wise
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL, result.getName()+"got Failed");
		test.log(Status.INFO, result.getThrowable().getMessage());
		
		try//we add screenshot method inside the try catch block bcoz if supposed ss not taken properly or ss not available then it will thrown an exception(FileNotFoundException)
		{
			
			//created the BaseClass object thats why in the "BaseClass" we make driver static bcoz at the time of creating new object the driver will create new that why our code may be fail , thats why we make as a static
			//this line is capture the screenshot and path and that will be stored into imgPath variable 
			String imgPath = new BaseClass().captureScreen(result.getName());
			//then this method attached the screenshot on report
			test.addScreenCaptureFromPath(imgPath); 
		}
		catch(IOException e1)
		{
			e1.printStackTrace();
		}
	}
	
	public void onTestSkipped(ITestResult result)
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+"got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext testContext)
	{
		extent.flush();
		
		//This method open report automatically 
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport = new File(pathOfExtentReport);
		
		try
		{
			Desktop.getDesktop().browse(extentReport.toURI());
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	

}
