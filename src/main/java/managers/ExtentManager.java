package managers;



import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.io.FileHandler;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.utils.FileUtil;

import Reusable_Methods.Utility;

public class ExtentManager {

	public static ExtentReports extent;
	public static ExtentHtmlReporter htmlreporter;
	
	static Utility util = new Utility();
	public static String  resultPath = "";
	
	
	public static String getPath() {
		return util.readProperty("Application");
	}

	public static String readCSS() throws IOException {
		return FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\SupportFiles\\Final_Support\\extent.css"), "utf-8");
	}

	public static String readJS() throws IOException {
		return FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\SupportFiles\\Final_Support\\Jquery.js"), "utf-8");
	}
	
	
	
	public static  synchronized ExtentReports ExtentReports_CreateInstance() throws IOException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MMM_YYYY");
		Date date = new Date();
		String name = sdf.format(date);
		resultPath = ".\\Results\\"+getPath()+"\\"+name;
		System.out.println(resultPath);
		File file = new File(resultPath);
		if(!(file.exists())) {
			file.mkdirs();
		}
		
		file = new File(resultPath+"\\SupportFiles\\flUhRq6tzZclQEJ-Vdg-IuiaDsNa.woff");
		if(!file.exists()) {
			System.out.println(resultPath+"\\SupportFiles\\flUhRq6tzZclQEJ-Vdg-IuiaDsNa.woff");
			File src = new File(System.getProperty("user.dir")+"\\SupportFiles\\Final_Support\\flUhRq6tzZclQEJ-Vdg-IuiaDsNa.woff");
			FileUtils.copyFile(src, file);
			
		}
		file = new File(resultPath+"\\SupportFiles\\6xK3dSBYKcSV-LCoeQqfX1RYOo3qOK7j.woff");
		if(!file.exists()) {
			System.out.println(resultPath+"\\SupportFiles\\6xK3dSBYKcSV-LCoeQqfX1RYOo3qOK7j.woff");
			File src = new File(System.getProperty("user.dir")+"\\SupportFiles\\Final_Support\\6xK3dSBYKcSV-LCoeQqfX1RYOo3qOK7j.woff");
			FileUtils.copyFile(src, file);
			
		}
		file = new File(resultPath+"\\SupportFiles\\6xKydSBYKcSV-LCoeQqfX1RYOo3i54rwlxdo.woff");
		if(!file.exists()) {
			System.out.println(resultPath+"\\SupportFiles\\6xKydSBYKcSV-LCoeQqfX1RYOo3i54rwlxdo.woff");
			File src = new File(System.getProperty("user.dir")+"\\SupportFiles\\Final_Support\\6xKydSBYKcSV-LCoeQqfX1RYOo3i54rwlxdo.woff");
			FileUtils.copyFile(src, file);
			
		}
		
		
		htmlreporter = new ExtentHtmlReporter(resultPath+"\\Execution_Report.html");
		htmlreporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlreporter.config().setChartVisibilityOnOpen(false);
		htmlreporter.config().setTheme(Theme.DARK);
		htmlreporter.config().setEncoding("utf-8");
		htmlreporter.config().setCSS(ExtentManager.readCSS());
		htmlreporter.config().setJS(ExtentManager.readJS());
		htmlreporter.setAppendExisting(true);
		extent = new ExtentReports();
		extent.attachReporter(htmlreporter);
		return extent;
	}

	
	
	
	
	
	
	
	
	
	
	
	
}
