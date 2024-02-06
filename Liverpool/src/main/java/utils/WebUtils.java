package utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebUtils {

	private static String OS = System.getProperty("os.name").toLowerCase();
    public static boolean IS_WINDOWS = (OS.indexOf("win") >= 0);
	
	public static boolean containsWords(String inputString, String[] items) {
		
	    boolean found = false;
	    
	    for (String item : items) {
	        if (inputString.toLowerCase().contains(item.toLowerCase())) {
	            found = true;
	            break;
	        }
	    }
	    
	    if(!found)
	    	System.out.println(inputString);
	    
	    return found;
	}
	
	public float fromPriceToNumber(String price)
	{
		return Float.parseFloat(price.substring(1).replaceAll(",", "")) / 100;
	}
	
	public List<HashMap<String, String>> getJsonData(String jsonFilePath) throws IOException 
	{
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
		
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, 
				new TypeReference<List<HashMap<String, String>>>() {} );
		
		return data;
	}
	
	public String getScreenshotPath(String testCaseName, WebDriver driver) throws IOException
	{
		TakesScreenshot screenshot =((TakesScreenshot)driver);
		File source=screenshot.getScreenshotAs(OutputType.FILE);
		
		String destination;
		
		if(IS_WINDOWS)
			destination = System.getProperty("user.dir") + "\\reports" + testCaseName + ".png";
		else
			destination = System.getProperty("user.dir") + "/reports" + testCaseName + ".png";
		
		FileUtils.copyFile(source, new File(destination));
		return destination;
	}
}
