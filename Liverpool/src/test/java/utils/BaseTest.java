package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

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
		
		driver = WebDriverManager.chromedriver().create();
				
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		driver.manage().window().maximize();
		
		driver.get(property.getProperty("url"));
		
		home = new HomePage(driver);
	}
	
	@DataProvider
	public Object[][] getData(Method m) throws IOException
	{	
		List<HashMap<String, String>> data = getJsonData(System.getProperty("user.dir") + "\\src\\test\\java\\data\\" + m.getName() + ".json");
		
		Object[][] objData = new Object[data.size()][1];
		
		for(int i = 0; i < data.size(); i++) {
			objData[i][0] = data.get(i);
		}
		
		return objData;
	}
	
	@AfterMethod
	public void returnToHome() throws IOException
	{
		driver.navigate().to(property.getProperty("url"));
	}
	
	@AfterClass
	public void tearDown()
	{
		driver.quit();
	}
}