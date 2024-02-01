package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjects.HomePage;

public class BaseTest extends WebUtils {
	
	public WebDriver driver;
	public HomePage home;
	public Properties property;
	
	@BeforeClass
	public void ConfigureDriver() throws IOException
	{
		property = new Properties();
		FileInputStream propertyFile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\resources\\data.properties");
		
		property.load(propertyFile);
		
		driver = WebDriverManager.edgedriver().create();
				
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		driver.manage().window().maximize();
		
		driver.get(property.getProperty("url"));
		
		home = new HomePage(driver);
	}
	
	@AfterClass
	public void tearDown()
	{
		driver.quit();
	}
}