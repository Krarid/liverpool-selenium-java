package utils;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	public WebDriver driver;
	
	@BeforeClass
	public void ConfigureDriver()
	{
		driver = WebDriverManager.edgedriver().create();
				
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		
		driver.get("https://www.liverpool.com.mx/tienda/home");
	}
	
	@AfterClass
	public void tearDown()
	{
		driver.quit();
	}
}