package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	WebDriver driver;
	Actions action;
	
	@FindBy(id = "mainSearchbar")
	private WebElement searchBar;
	
	@FindBy(xpath = "//span[contains(text(),'Categorías')]")
	private WebElement categoryIcon;
	
	@FindBy(css = "div.m-megamenu__category_menu-item span")
	private List<WebElement> categories;
	
	@FindBy(css = "div.category-menu li div a")
	private List<WebElement> subcategories;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		action = new Actions(driver);
		PageFactory.initElements(driver, this);
	}
	
	public ArticlesPage search(String article)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

		searchBar.sendKeys(article + Keys.ENTER);
				
		wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe("https://www.liverpool.com.mx/tienda/home")));
		
		return new ArticlesPage(driver);
	}
	
	public void chooseCategory(String category)
	{
		categoryIcon.click();
		
		for(WebElement c : categories ) {
			
			if( c.getText().equals(category) ) {
				c.click();
				break;
			}		
		}
	}
	
	public ArticlesPage chooseCategory(String category, String subcategory)
	{
		categoryIcon.click();
		
		for(WebElement c : categories ) {
			
			if( c.getText().equals(category) ) {
				
				action.moveToElement(c).build().perform();
				
				for( WebElement s: subcategories ) {
					
					if( s.getText().equals(subcategory) ) {
						s.click();
						
						return new ArticlesPage(driver);
					}
					
				}		
			}		
		}
		
		throw new NoSuchElementException(subcategory);
	}
}
