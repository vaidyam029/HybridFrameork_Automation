package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {
	
	public MyAccountPage(WebDriver driver) 
	{
		super (driver);
	}
	
	@FindBy (xpath = "//h2[normalize-space()='My Account']") WebElement msgHeading;  //Myaccount Page Heading txt capture
	
	@FindBy (xpath = "//a[@class='list-group-item'][normalize-space()='Logout']") WebElement lnkLogout;   //added in step number 6
	
	//we are not doing any validation here we are just check our text is displayed on page or not
	//in POM classes we are cant added validations
	//we verify Myaccount page is exist or not
	public boolean isMyaccountPageExists()
	{
		try
		{
			return (msgHeading.isDisplayed());
		}
		catch(Exception e)
		{
			return false;
		}
		
	}

	public void clickLogout()
	{
		lnkLogout.click();
	}
	
	
	
	
}
