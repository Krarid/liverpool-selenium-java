package utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebUtils {

	public static boolean containsWords(String inputString, String[] items) {
		
	    boolean found = false;
	    
	    for (String item : items) {
	        if (inputString.contains(item)) {
	            found = true;
	            break;
	        }
	    }
	    
	    if(!found)
	    	System.out.println(inputString);
	    
	    return found;
	}
	
	public List<HashMap<String, String>> getJsonData(String jsonFilePath) throws IOException 
	{
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath), StandardCharsets.UTF_8);
		
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, 
				new TypeReference<List<HashMap<String, String>>>() {} );
		
		return data;
	}
}
