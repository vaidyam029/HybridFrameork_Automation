package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	
	@Test(groups = {"Sanity", "Master"})
	public void verify_login()
	{
		logger.info("**** Starting TC002_LoginTest");
		
		try
		{
		//we are creating the Homepage object bcoz we should click on login button
		HomePage hp = new HomePage(driver);  //this driver is coming from base class
		hp.clickMyAccount();
		hp.clickLogin();
		
		//this is Login page
		LoginPage lp = new LoginPage(driver);
		lp.setEmail(p.getProperty("email"));      //this email & password we capture from config.properties file
		lp.setPassword(p.getProperty("password"));  //we create a Properties class inside the BaseClass
		lp.clickLogin();
		
		//Myaccount page
		MyAccountPage macc = new MyAccountPage(driver);
		boolean targetpage = macc.isMyaccountPageExists();
		
		//Validation apply on MyAccount page
	//	Assert.assertEquals(targetpage, true, "Login failed");  //first two parameter is validation and 3rd("Login failed") is if supposed assertion is failed then this "Login failed" message is print in console
		          //OR
		Assert.assertTrue(targetpage);
		}
		catch(Exception e)    //we put all code in try catch block bcoz if supposed some exception is coming the it will handle it and our test is fail
		{
			Assert.fail();
		}
		logger.info("***** Finished TC_002_LoginTest ******");
		
	}

}
