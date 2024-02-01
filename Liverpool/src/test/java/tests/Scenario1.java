package tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.ArticlePage;
import pageObjects.ArticlesPage;
import utils.BaseTest;

public class Scenario1 extends BaseTest {
	
	@Test(dataProvider="getData")
	public void SearchArticle(HashMap<String, String> input)
	{
		ArticlesPage articles = home.search(input.get("article"));
		
		Stack<String> titles = articles.getArticleTitles();
		
		String[] items = {"PlayStation", "Play Station", "PS5", "PS4", "Sony"};
		
		// Assert that the results matches at least one of the words above
		for( String title : titles ) {
			Assert.assertTrue(containsWords(title, items));
		}
		
		int id = -1;
		
		// If the article doesn't exist then assert fail
		try {
			id = articles.getArticleID(input.get("title"));
		} catch (Exception e) {
			Assert.fail("The title doesn't exist: " + e.getMessage());
		}
		
		// Get the id of the product base on its title and get its price from the articles page
		String price = articles.getArticlePrice(id);
		
		ArticlePage article = articles.goToArticle(id);
		
		// Assert that the title and price match with the ones from the results page
		Assert.assertEquals(article.getTitle(), input.get("title"));
		Assert.assertEquals(article.getPrice().replaceAll("\\s", ""), price);
	}
	
	@AfterMethod
	public void returnToHome() throws IOException
	{
		driver.navigate().to(property.getProperty("url"));
	}
	
	@DataProvider
	public Object[][] getData() throws IOException
	{	
		List<HashMap<String, String>> data = getJsonData(System.getProperty("user.dir") + "\\src\\test\\java\\data\\scenario1.json");
		
		Object[][] objData = new Object[data.size()][1];
		
		for(int i = 0; i < data.size(); i++) {
			objData[i][0] = data.get(i);
		}
		
		return objData;
	}
}
