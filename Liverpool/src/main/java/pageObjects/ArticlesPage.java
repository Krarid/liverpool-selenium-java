package pageObjects;

import java.util.List;
import java.util.Stack;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ArticlesPage {
	
	WebDriver driver;
	
	@FindBy(css = "figure")
	private List<WebElement> articles;
	
	@FindBy(css = "label[title='Tamaño']")
	private WebElement sizeFilter;
	
	@FindBy(css = "label[title='Precios']")
	private WebElement priceFilter;
	
	@FindBy(css = "input[id$='pulgadas']")
	private List<WebElement> sizes;
	
	@FindBy(css = "input[id*='sortPrice']")
	private List<WebElement> prices;
	
	@FindBy(css = "input[id*='brand']")
	private List<WebElement> brands;
	
	@FindBy(css = "h1.searcherTitle-result")
	private WebElement result;
	
	@FindBy(id = "Tamao")
	private WebElement showMoreSizes;
	
	@FindBy(id = "Marcas")
	private WebElement showMoreBrands;
	
	public ArticlesPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public Stack<String> getArticleTitles()
	{
		Stack<String> titles = new Stack<String>();
		
		for( WebElement article : articles )
			titles.add( article.findElement(By.cssSelector("figure article h5")).getText() );
		
		return titles;
	}
	
	public Stack<String> getArticlePrices()
	{
		Stack<String> prices = new Stack<String>();
		
		for( WebElement article : articles )
			prices.add( article.findElement(By.cssSelector("figure p[class*='a-card']:last-of-type")).getText() );
		
		return prices;
	}
	
	public int getArticleID(String title) throws NoSuchElementException
	{
		String articleTitle;
		
		for( int i = 0; i < articles.size(); i++ ) {
			
			articleTitle = articles.get(i).findElement(By.cssSelector("figure article h5")).getText();
			
			if( articleTitle.equals(title) ) {
				return i;
			}
		}
		
		throw new NoSuchElementException(title);
	}
	
	public String getArticlePrice(int articleID)
	{
		return articles.get(articleID).findElement(By.cssSelector("p.a-card-discount")).getText();
	}
	
	public ArticlePage goToArticle(int articleID)
	{
		articles.get(articleID).click();
		
		return new ArticlePage(driver);
	}
	
	public String getSizeFilter()
	{
		return sizeFilter.getText();
	}
	
	public String getPriceFilter()
	{
		return priceFilter.getText();
	}
	
	public String getResult()
	{
		return result.getText();
	}
	
	public void chooseSize(String size) throws InterruptedException
	{
		try {
			showMoreSizes.click();
		} catch(NoSuchElementException e) {
			//
		}
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		for(WebElement s : sizes) {
			
			if( s.getAttribute("id").contains(size) ) {
				
				js.executeScript("arguments[0].scrollIntoView();", s);
				s.click();
				
				Thread.sleep(1000);
				
				break;
			}
		}
	}
	
	public void choosePrice(String price) throws InterruptedException
	{		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		for( WebElement p : prices ) {
			
			if( p.getAttribute("id").contains(price) ) {
				
				js.executeScript("arguments[0].scrollIntoView();", p);
				p.click();
				
				Thread.sleep(1000);
				break;
			}
		}
	}
	
	public void chooseBrand(String brand) throws InterruptedException
	{
		try {
			showMoreBrands.click();
		} catch(NoSuchElementException e) {
			//
		}
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		for( WebElement b : brands ) {
			
			if( b.getAttribute("id").contains(brand) ) {
				
				js.executeScript("arguments[0].scrollIntoView();", b);
				b.click();
				
				Thread.sleep(1000);
				
				break;
			}
		}
	}
}
