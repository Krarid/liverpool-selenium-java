package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ArticlePage {
	
	WebDriver driver;
	
	@FindBy(css = "h1.a-product__information--title")
	private WebElement title;
	
	@FindBy(xpath = "//main//p[contains(@class, 'Price')][last()]")
	private WebElement price;
	
	public ArticlePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public String getTitle()
	{
		return title.getText();
	}
	
	public String getPrice()
	{
		return price.getText();
	}
}
