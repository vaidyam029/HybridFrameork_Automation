package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
	
	
	public HomePage(WebDriver driver)
	{
		super(driver); //this driver we call from BasePage bcoz of reusability, why we write pagefatory method again and again
	}
	
	@FindBy (xpath = "//span[normalize-space()='My Account']") WebElement lnkMyaccount;
	
	@FindBy (xpath = "//a[normalize-space()='Register']") WebElement lnkRegister;
	
	@FindBy (linkText = "Login") WebElement linklogin;    //login link added in step 5
	
	
	public void clickMyAccount()
	{
		lnkMyaccount.click();
	}
	
	public void clickRegister()
	{
		lnkRegister.click();
	}
	
	public void clickLogin()
	{
		linklogin.click();
	}
	
	

}
