package utilities;

import BaseClasses.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


import static utilities.Constants.extendReportPath;


public class ExtentManager extends BaseTest {

	static ExtentReports extent = ExtentManager.createInstance();

	public static ExtentReports createInstance() {
	        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + extendReportPath);

	        htmlReporter.config().setTheme(Theme.STANDARD);
	        htmlReporter.config().setDocumentTitle("Test Automation - Report");
	        htmlReporter.config().setEncoding("utf-8");
	        htmlReporter.config().setReportName("Test Cases Results");
	        
	        extent = new ExtentReports();
	        extent.attachReporter(htmlReporter);
	        extent.setSystemInfo("Automation Tester", "Ergi Lala");
	        extent.setSystemInfo("Organization", "LHIND TIA");


	        return extent;
	    }

	}
