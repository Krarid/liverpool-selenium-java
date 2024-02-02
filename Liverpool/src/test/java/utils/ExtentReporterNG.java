package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG extends WebUtils {
	
	public static ExtentReports extent;
	
	public static ExtentReports getReporterObject()
	{
		String path;
		
		if(IS_WINDOWS)
			path = (System.getProperty("user.dir") + "\\reports\\index.html");
		else
			path = (System.getProperty("user.dir") + "/reports/index.html");
		
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");
		
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Javier Melendez");
		
		return extent;
	}
}
