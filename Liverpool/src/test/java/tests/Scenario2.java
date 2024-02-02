package tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.ArticlesPage;
import utils.BaseTest;

public class Scenario2 extends BaseTest {

	@Test(dataProvider="getData")
	public void SearchArticle(HashMap<String, String> input) throws InterruptedException
	{
		ArticlesPage articles = home.search(input.get("article"));
		
		// Sizes and prices filters are displayed
		Assert.assertEquals(articles.getSizeFilter(), "Tamaño");
		Assert.assertEquals(articles.getPriceFilter(), "Precios");
		
		articles.chooseSize(input.get("size"));
		articles.choosePrice(input.get("price"));
		articles.chooseBrand(input.get("brand"));
		
		Stack<String> titles = articles.getArticleTitles();
		Stack<String> prices = articles.getArticlePrices();
		
		// Assert that the results matches the three conditions: size, price, brand
		for( int i = 0; i < titles.size(); i++ ) {
			Assert.assertTrue(titles.get(i).contains(input.get("size")));
			Assert.assertTrue(titles.get(i).toLowerCase().contains(input.get("brand").toLowerCase()));
			Assert.assertTrue( fromPriceToNumber(prices.get(i)) > Float.parseFloat( input.get("price").split("-")[0] ) );
		}
	}
	
	@AfterMethod
	public void returnToHome() throws IOException
	{
		driver.navigate().to(property.getProperty("url"));
	}
	
	@DataProvider
	public Object[][] getData() throws IOException
	{	
		List<HashMap<String, String>> data = getJsonData(System.getProperty("user.dir") + "\\src\\test\\java\\data\\scenario2.json");
		
		Object[][] objData = new Object[data.size()][1];
		
		for(int i = 0; i < data.size(); i++) {
			objData[i][0] = data.get(i);
		}
		
		return objData;
	}
}
