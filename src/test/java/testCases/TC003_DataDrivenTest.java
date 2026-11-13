package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_DataDrivenTest extends BaseClass {
	
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class,groups="datadriven")
	public void verify_LoginDDT(String email, String pwd, String exp_Result)
	{
		logger.info("**** Starting TC003_DataDrivenTest ****");
		try {
			
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		LoginPage lp = new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		MyAccountPage map = new MyAccountPage(driver);
		boolean targetpage = map.isMyAccountExist();
		
		/*Data is Valid -  Login Success - Test Pass - Logout
		  				   Login Failed - Test Fail
		 
		  Data is Invalid -  Login Success - Test Fail - Logout
		 					 Login Failed - Test Pass 
		 */
		
		if(exp_Result.equalsIgnoreCase("Valid"))
		{
			if(targetpage==true)
			{
				map.clickLogout();
				Assert.assertTrue(true);
			}
			
			else
			{
				Assert.assertTrue(false);
			}
		}
			
			if(exp_Result.equalsIgnoreCase("Invalid"))
			{
				if(targetpage==true) {
					map.clickLogout();
					Assert.assertTrue(false);
				}
				
				else
				{
					Assert.assertTrue(true);
				}
			}
		
		}catch(Exception e)
			{
				Assert.fail();
			}
			
			logger.info("**** TC003_DataDrivenTest Completed Successfully");
		}
		
		
	}

	

	
