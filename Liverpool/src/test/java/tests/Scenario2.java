package tests;

import java.util.HashMap;
import java.util.Stack;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.ArticlesPage;
import utils.BaseTest;

public class Scenario2 extends BaseTest {

	@Test(dataProvider="getData")
	public void scenario2(HashMap<String, String> input) throws InterruptedException
	{
		ArticlesPage articles = home.search(input.get("article"));
		
		// Sizes and prices filters are displayed
		Assert.assertTrue(articles.getSizeFilter().startsWith("Tama"));
		Assert.assertEquals(articles.getPriceFilter(), "Precios");
		
		articles.chooseSize(input.get("size"));
		articles.choosePrice(input.get("price"));
		articles.chooseBrand(input.get("brand"));
		
		Stack<String> titles = articles.getArticleTitles();
		Stack<String> prices = articles.getArticlePrices();
		
		// Assert that the results matches the three conditions: size, price, brand
		for( int i = 0; i < titles.size(); i++ ) {
			Assert.assertTrue(titles.get(i).contains(input.get("size").replaceAll("[^0-9]", "")));
			Assert.assertTrue(titles.get(i).toLowerCase().contains(input.get("brand").toLowerCase().substring(0, 2)));
			Assert.assertTrue( fromPriceToNumber(prices.get(i)) > Float.parseFloat( input.get("price").split("-")[0] ) );
		}
		
		String results = driver.findElement(By.xpath("//label[contains(text(), '" + input.get("brand") + "')]")).getText().replaceAll("[^0-9]", "");

		// Verify that the results in the brand filter matches with the results
		Assert.assertEquals(results, articles.getResult().replaceAll("[^0-9]", ""));
	}
}
