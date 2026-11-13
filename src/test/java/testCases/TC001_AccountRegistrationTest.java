	package testCases;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;


public class TC001_AccountRegistrationTest extends BaseClass{
	
	
	@Test(groups={"Regression"})
	public void verify_account_registration()	
	{
		logger.info("**** Starting TC001_Account Registration Test ****");
		
		try
		{
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Clicking on My Account");
		hp.clickRegister();
		logger.info("Clicking on Register");
		
		AccountRegistrationPage arPage = new AccountRegistrationPage(driver);
		
		logger.info("Entering Customer Details");
		arPage.setFirstname(randomString().toUpperCase());
		arPage.setLastname(randomString().toUpperCase());
		arPage.setEmail(randomString()+"@gmail.com");
		arPage.setTelephone(randomNumber());	
		
		arPage.setPassword("ch12345");
		arPage.setConfirmPassword("ch12345");
		
		arPage.setPrivacyPolicy();
		arPage.clickContinue();
		
		logger.info("Validating Registration Confirmation Message");
		String confmsg = arPage.getConfirmationMsg();		
		Assert.assertEquals(confmsg, "Your Account Has Been Created!");	
	}
		catch(Exception e)
		{
			logger.error("Test Failed");
			logger.debug("Debug logs..");
			Assert.fail();
		}
		
		logger.info("**** TC001_Account Registration Completed Successfully ****");
	}
}