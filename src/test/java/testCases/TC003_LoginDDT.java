package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;


public class TC003_LoginDDT extends BaseClass {
	
	//here we add two parameter 1) data provider name 2) we create dataprovider class in different package thats why we should add  
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "Datadriven")   //getting data provider from different class
	public void verify_loginDDT(String email, String pwd, String exp)
	{
		
		logger.info("**** Starting TC003_LoginDDT ****");
		
		try
		{
		//HomePage
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//Login
		LoginPage lp = new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		//MyAccount 
		MyAccountPage macc = new MyAccountPage(driver);
		boolean targetPage = macc.isMyaccountPageExists();
		
		/* 1) Data is valid - login success- test pass - logout
		                      login failed - test failed 
		   
		   2)Data is invalid - login success - test fail - logout
		                       login failed - test pass
		*/
		
		if (exp.equalsIgnoreCase("Valid"))
		{
			if (targetPage==true)
			{
				macc.clickLogout();
				Assert.assertTrue(true);
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
		
		if (exp.equalsIgnoreCase("Invalid"))
		{
			if(targetPage==true)
			{
				macc.clickLogout();
				Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
		}
		catch(Exception e) 
		{
			Assert.fail();
		}
		
		logger.info("***** Finished TC003_LoginDDT *****");
		
	}
	
	
	

}
