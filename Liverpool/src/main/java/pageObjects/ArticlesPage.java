package pageObjects;

import java.util.List;
import java.util.Stack;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ArticlesPage {
	
	WebDriver driver;
	
	@FindBy(css = "figure")
	private List<WebElement> articles;
	
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
}
