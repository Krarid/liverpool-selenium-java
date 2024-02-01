package pageObjects;

import java.time.Duration;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	WebDriver driver;
	
	@FindBy(id = "mainSearchbar")
	private WebElement searchBar;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public ArticlesPage search(String article)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

		searchBar.sendKeys(article + Keys.ENTER);
				
		wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe("https://www.liverpool.com.mx/tienda/home")));
		
		return new ArticlesPage(driver);
	}
}
