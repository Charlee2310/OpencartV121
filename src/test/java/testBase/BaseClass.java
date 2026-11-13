package testBase;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
public class BaseClass {
	
	public static WebDriver driver;
	public Logger logger;
	public Properties p;

	@BeforeClass(groups= {"Sanity","Regression"})
	@Parameters({"os","browser"})
	public void setup(String OS, String br) throws IOException
	{
	FileInputStream file = new FileInputStream("./src//test//resources//config.properties");
	p=new Properties();
	p.load(file);
	file.close();
	logger = LogManager.getLogger(this.getClass());
	
	if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
	{
		DesiredCapabilities cap = new DesiredCapabilities();
		
		if(OS.equalsIgnoreCase("windows"))
		{
			cap.setPlatform(Platform.WIN11);
		}
		
		else
		{
			System.out.println("No Matching OS");
		}
		
		switch (br.toLowerCase()) 
		{
		case "chrome": cap.setBrowserName("chrome");break;
		case "edge": cap.setBrowserName("MicrosoftEdge");break;
		default: System.out.println("No Matching Browser"); return;		
		}
		
		driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
	}
	
	if(p.getProperty("execution_env").equalsIgnoreCase("local"))
	{
		switch(br.toLowerCase())
		{
		case "chrome" :driver = new ChromeDriver(); break;
		case "edge" : driver = new EdgeDriver(); break;
		case "firefox" : driver = new FirefoxDriver(); break;
		default : System.out.println("Invalid Browser name"); return;
		}		
	}
	
	driver.manage().deleteAllCookies();
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	driver.get(p.getProperty("appURL"));
	driver.manage().window().maximize();
	}

	@AfterClass
	public void tearDown()
	{
		if (driver != null) {
	        driver.quit();
	    }
	}
	public String randomString()
	{
	String generatedString = RandomStringUtils.randomAlphabetic(5);
	return generatedString;
	}
	public String randomNumber()
	{
	String generatedNumber = RandomStringUtils.randomNumeric(10);
	return generatedNumber;
	}
	
	public String captureScreen(String testName) throws IOException {

	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
	            .format(new Date());

	    TakesScreenshot ts = (TakesScreenshot) driver;
	    File source = ts.getScreenshotAs(OutputType.FILE);

	    String targetPath = System.getProperty("user.dir")
	            + "/screenshots/" + testName + "_" + timeStamp + ".png";

	    File targetFile = new File(targetPath);
	    targetFile.getParentFile().mkdirs();

	    Files.copy(source.toPath(), targetFile.toPath(),
	            StandardCopyOption.REPLACE_EXISTING);

	    return targetPath;
	}
		}
