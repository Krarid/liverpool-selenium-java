package tests;

import java.util.HashMap;
import java.util.Stack;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.ArticlePage;
import pageObjects.ArticlesPage;
import utils.BaseTest;

public class Scenario1 extends BaseTest {
	
	@Test(dataProvider="getData")
	public void scenario1(HashMap<String, String> input)
	{
		ArticlesPage articles = home.search(input.get("article"));
		
		Stack<String> titles = articles.getArticleTitles();
		
		String[] items = {"PlayStation", "Playstation", "Play Station", "PS5", "PS4", "Sony"};
		
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
}
