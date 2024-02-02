package tests;

import java.util.HashMap;
import java.util.Stack;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.ArticlesPage;
import utils.BaseTest;

public class Scenario3 extends BaseTest {
	
	@Test(dataProvider="getData")
	public void scenario3(HashMap<String, String> input) throws InterruptedException
	{
		ArticlesPage articles = home.chooseCategory(input.get("category"), input.get("subcategory"));
		
		articles.chooseBrand(input.get("brand"));
		
		Stack<String> titles = articles.getArticleTitles();
		
		// Assert that all the results contain the Dior brand name
		for( String title : titles )
			Assert.assertTrue(title.toLowerCase().contains(input.get("brand").toLowerCase()));
	}
	
}
