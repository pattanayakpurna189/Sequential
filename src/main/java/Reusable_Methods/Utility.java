package Reusable_Methods;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import managers.ExtentManager;

public class Utility {
	public static ThreadLocal<ExtentTest> parentTest=new ThreadLocal<ExtentTest>();
	ExtentManager ext_man = new ExtentManager();
	public static Hashtable<String,String> Data=new Hashtable<String,String>();
	public static Hashtable<String,String> DataLogin=new Hashtable<String,String>();
	
	
	public String readProperty(String key) {
		String value = null;
		try{
			
			FileInputStream stream = new FileInputStream(".//ProjectSetting.properties");
			Properties pro = new Properties();
			pro.load(stream);
			value = pro.getProperty(key);
			
		}catch(Exception e) {
			
		}
		
		return value;
		
	}
	
	public void Press_Enter() {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		}catch(Exception e) {
			
		}
		
	}
	
	/*public void Click(WebDriver driver,Locators locator, String object, int timeToWait) throws InterruptedException {
		int my_time = 0;
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			while(my_time<timeToWait) {
				my_time = my_time+1;
				WebElement ele = null;
				try {
					switch(locator) {
						case xpath:
							System.out.println("Inside switch ccase xpath");
							 ele = driver.findElement(By.xpath(object));
							break;
						case id:
							 ele = driver.findElement(By.id(object));
							break;
						case name:
							 ele = driver.findElement(By.name(object));
							break;
					}
					if(ele!=null) {
						if(ele.isDisplayed()) {
							ele.click();
							break;
							
						}else {
							Thread.sleep(1000);
						}
					}else {
						Thread.sleep(1000);
					}
					
				}catch(Exception e) {
					System.out.println("Exception in catch block");
					Thread.sleep(1000);
				}
			}
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	}*/
	
	/*public void Sendkeys(WebDriver driver,Locators locator, String object,String data, int timeToWait) throws InterruptedException {
		int my_time = 0;
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			while(my_time<timeToWait) {
				my_time = my_time+1;
				WebElement ele = null;
				try {
					switch(locator) {
						case xpath:
							 ele = driver.findElement(By.xpath(object));
							break;
						case id:
							 ele = driver.findElement(By.id(object));
							break;
						case name:
							 ele = driver.findElement(By.name(object));
							break;
					}
					if(ele!=null) {
						if(ele.isDisplayed()) {
							ele.click();
							ele.sendKeys(data);
							break;
						}else {
							Thread.sleep(1000);
						}
					}else {
						Thread.sleep(1000);
					}
					
				}catch(Exception e) {
					Thread.sleep(1000);
				}
			}
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	}*/
	
		
	public ArrayList<String> get_Driver_TestCaseID(String SheetName) throws FilloException {
		
		
		String Application = readProperty("Application");
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(".//Database//"+Application+"//Driver.xlsx");
		String TestCases_Query = "SELECT * FROM "+SheetName+" Where Control ='1' Order By Sno";
		Recordset TestCases_Rs = connection.executeQuery(TestCases_Query);
		ArrayList<String> columns = new ArrayList<String>();
		while(TestCases_Rs.next()) {
			columns.add(TestCases_Rs.getField("TestCase_ID").toString());
		}
		
		connection.close();
		return columns;
		
	}
	
	
	public ArrayList<String> getTestData_SeqNos(String Databinder,String SheetName) throws FilloException
	{
		String Application = readProperty("Application");
		Fillo fillo=new Fillo();
		Connection connection = fillo.getConnection(".//Database//"+Application+"//Driver.xlsx");
		String DataQuery="SELECT * FROM "+SheetName+" Where  Control ='1' and TestCaseID='"+Databinder+"'";
		Recordset Data_RS=connection.executeQuery(DataQuery);
		ArrayList<String> columns=new ArrayList<String>();
		
		while(Data_RS.next()) {
			columns.add(Data_RS.getField("Sl_No").toString());
		}
		connection.close();
		return columns;
		
	}
	
	
	
	
	public Hashtable<String,String> get_Keywords_Databindings(String TestCase_ID,String SheetName,String Seq_No) throws FilloException
	{
			String Application = readProperty("Application");
			Fillo fillo=new Fillo();
			Connection connection=fillo.getConnection(".//Database//"+Application+"//Driver.xlsx");
			Hashtable<String,String> data_values=new Hashtable<String,String>();
			String DataQuery="SELECT * FROM "+SheetName+" Where TestCaseID='"+TestCase_ID+"' and Sl_No='"+Seq_No+"'";
			Recordset Data_RS=connection.executeQuery(DataQuery);
			ArrayList<String> columns=new ArrayList<String>();
			columns=Data_RS.getFieldNames();
			//System.out.println("columns:"+columns);
			for(int i=1 ;i<columns.size();i++) {
				Data_RS.next();
				String columnName=columns.get(i);
				String columnValue=Data_RS.getField(columnName);
				data_values.put(columnName.trim().toString(), columnValue.trim().toString());
			
			}
			connection.close();
			return data_values;
	}
	
	public Hashtable<String,String> get_Driver_Details(String TestCase_ID) throws FilloException
	{
			String Application = readProperty("Application");
			Fillo fillo=new Fillo();
			Connection connection=fillo.getConnection(".//Database//"+Application+"//Driver.xlsx");
			Hashtable<String,String> data_values=new Hashtable<String,String>();
			String DataQuery="SELECT * FROM TestCase_Control Where TestCase_ID='"+TestCase_ID+"'";
			Recordset Data_RS=connection.executeQuery(DataQuery);
			ArrayList<String> columns=new ArrayList<String>();
			columns=Data_RS.getFieldNames();
			//System.out.println("columns:"+columns);
			for(int i=1 ;i<columns.size();i++) {
				Data_RS.next();
				String columnName=columns.get(i);
				String columnValue=Data_RS.getField(columnName);
				data_values.put(columnName.trim().toString(), columnValue.trim().toString());
			
			}
			connection.close();
			return data_values;
	}
	
	
	public Hashtable<String,String> get_TestData(String DataBinding, String SheetName) throws FilloException
	{
			String Application = readProperty("Application");
			Fillo fillo=new Fillo();
			Connection connection=fillo.getConnection(".//Database//"+Application+"//TestData.xlsx");
			Hashtable<String,String> data_values=new Hashtable<String,String>();
			String DataQuery="SELECT * FROM "+SheetName+" Where DataBinding='"+DataBinding+"'";
			Recordset Data_RS=connection.executeQuery(DataQuery);
			ArrayList<String> columns=new ArrayList<String>();
			columns=Data_RS.getFieldNames();
			//System.out.println("columns:"+columns);
			for(int i=1 ;i<columns.size();i++) {
				Data_RS.next();
				String columnName=columns.get(i);
				String columnValue=Data_RS.getField(columnName);
				data_values.put(columnName.trim().toString(), columnValue.trim().toString());
			
			}
			connection.close();
			return data_values;
	}
	
	
	
	/*public String CaptureScreenshot()
	{
		SimpleDateFormat formatter =new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
		Date date=new Date();
		String imageName="img"+formatter.format(date)+".png";
		System.out.println(ExtentManager.resultPath);
		String screenshot_path = ExtentManager.resultPath+"\\Screenshots";
		//String screenshot_path ="./Screenshots";
		File file = new File(screenshot_path);
		if(!(file.exists())) {
			file.mkdirs();
		}
		try{
			try {
				Thread.sleep(300);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Robot robot =new Robot();
			BufferedImage screenshotimg=robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		try
			{
				ImageIO.write(screenshotimg,"png",new File(screenshot_path+"\\"+imageName));
			}catch(IOException e) {
				e.printStackTrace();
			}
		}catch(AWTException e) {
			e.printStackTrace();	
		}
		
		return ".\\Screenshots\\"+imageName;
	}*/
	
	public synchronized String CaptureScreenshot(RemoteWebDriver driver)
	{
		SimpleDateFormat formatter =new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
		Date date=new Date();
		//File SrcFile= null;
		String imageName="img"+formatter.format(date)+".png";
		//System.out.println(ExtentManager.resultPath);
		String screenshot_path = ExtentManager.resultPath+"\\Screenshots";
		//String screenshot_path ="./Screenshots";
		File file = new File(screenshot_path);
		if(!(file.exists())) {
			file.mkdirs();
		}
		try{
			TakesScreenshot scrShot =((TakesScreenshot)driver);
			 // SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
			
			try {
				byte[] image = scrShot.getScreenshotAs(OutputType.BYTES);
				File screenshot = new File(screenshot_path+"\\"+imageName);
				FileOutputStream fos = new FileOutputStream(screenshot);
				fos.write(image);
				fos.close();
						
			}catch(Exception e) {
				byte[] image = scrShot.getScreenshotAs(OutputType.BYTES);
				File screenshot = new File(screenshot_path+"\\"+imageName);
				FileOutputStream fos = new FileOutputStream(screenshot);
				fos.write(image);
				fos.close();
			}
			
		}catch(Exception e) {
			e.printStackTrace();	
		}
		
		return ".\\Screenshots\\"+imageName;
	}
	
	
	public synchronized void Pass(RemoteWebDriver driver,String Details) {
		try {
			parentTest.get().pass(Details,MediaEntityBuilder.createScreenCaptureFromPath(CaptureScreenshot(driver)).build());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public synchronized void Fail(RemoteWebDriver driver,String Details) {
		try {
			parentTest.get().fail(Details,MediaEntityBuilder.createScreenCaptureFromPath(CaptureScreenshot(driver)).build());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public synchronized void Info(RemoteWebDriver driver,String Details) {
		try {
			parentTest.get().info(Details,MediaEntityBuilder.createScreenCaptureFromPath(CaptureScreenshot(driver)).build());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public synchronized WebElement get_Element(RemoteWebDriver driver,By locator) {
		WebElement ele = null;
		try {
			 ele = driver.findElement(locator);
			
		}catch(Exception e ) {
			
		}
		
		return ele;
	}
	
	public synchronized boolean Contains_Text(RemoteWebDriver driver,By locator, String Expected_Text, int timeToWait) throws Exception {
		int my_time = 0;
		boolean flag = false;
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			while(my_time<timeToWait) {
				my_time = my_time+1;
				WebElement ele = null;
				try {
					ele = get_Element(driver, locator);
					if(ele!=null) {
						if(ele.isDisplayed()) {
							String Text = ele.getText();
							if(Text!=null) {
								if(!Text.trim().isEmpty()) {
									if(Text.trim().contains(Expected_Text)) {
										flag = true;
									}
								}
							}
							break;
							
						}else {
							Thread.sleep(1000);
						}
					}else {
						Thread.sleep(1000);
					}
					
				}catch(Exception e) {
					System.out.println("Exception in catch block");
					Thread.sleep(1000);
				}
			}
			if(my_time>=timeToWait) {
				ThrowException( driver, locator);
			}
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			return flag;
	}
	
	public synchronized void Click( RemoteWebDriver driver,By locator, int timeToWait) throws Exception {
		int my_time = 0;
			driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			while(my_time<timeToWait) {
				my_time = my_time+1;
				WebElement ele = null;
				try {
					ele = get_Element(driver, locator);
					if(ele!=null) {
						if(ele.isDisplayed()) {
							ele.click();
							break;
							
						}else {
							Thread.sleep(1000);
						}
					}else {
						Thread.sleep(1000);
					}
					
				}catch(Exception e) {
					System.out.println("Exception in catch block");
					Thread.sleep(1000);
				}
			}
			if(my_time>=timeToWait) {
				ThrowException( driver, locator);
			}
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	}
	
	public synchronized void Sendkeys(RemoteWebDriver driver,By locator,String data, int timeToWait) throws Exception  {
		int my_time = 0;
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			while(my_time<timeToWait) {
				my_time = my_time+1;
				WebElement ele = null;
				try {
					ele = get_Element(driver, locator);
					if(ele!=null) {
						if(ele.isDisplayed()) {
							ele.click();
							Thread.sleep(100);
							ele.clear();
							Thread.sleep(100);
							ele.sendKeys(data);
							break;
						}else {
							Thread.sleep(1000);
						}
					}else {
						Thread.sleep(1000);
					}
					
				}catch(Exception e) {
					Thread.sleep(1000);
				}
			}
			
			if(my_time>=timeToWait) {
				ThrowException( driver, locator);
			}
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	}
	
	public synchronized void wait_for_Element( RemoteWebDriver driver,By locator, int timeToWait) throws Exception {
		int my_time = 0;
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			while(my_time<timeToWait) {
				my_time = my_time+1;
				WebElement ele = null;
				try {
					ele = get_Element(driver, locator);
					if(ele!=null) {
						if(ele.isDisplayed()) {
							break;
							
						}else {
							Thread.sleep(1000);
						}
					}else {
						Thread.sleep(1000);
					}
					
				}catch(Exception e) {
					System.out.println("Exception in catch block");
					Thread.sleep(1000);
				}
			}
			if(my_time>=timeToWait) {
				ThrowException( driver, locator);
				
				
			}
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	}
	
	public synchronized void wait_for_Element_WithOutThrowing_Exception(RemoteWebDriver driver,By locator, int timeToWait) throws Exception {
		int my_time = 0;
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			while(my_time<timeToWait) {
				my_time = my_time+1;
				WebElement ele = null;
				try {
					ele = get_Element(driver, locator);
					if(ele!=null) {
						if(ele.isDisplayed()) {
							break;
							
						}else {
							Thread.sleep(1000);
						}
					}else {
						Thread.sleep(1000);
					}
					
				}catch(Exception e) {
					System.out.println("Exception in catch block");
					Thread.sleep(1000);
				}
			}
			
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	}
	
	public synchronized boolean isDisplayed(RemoteWebDriver driver,By locator) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		boolean flag = false;
			WebElement ele = null;
				try {
					ele = get_Element(driver, locator);
					if(ele!=null) {
						if(ele.isDisplayed()) {
							ele.click();
							flag = true;
						}
					}
					
				}catch(Exception e) {
					
				}
			
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			return flag;
	}
	
	public synchronized String getText(RemoteWebDriver driver,By locator) throws Exception {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		String Data = "";
		WebElement ele = null;
				try {
					ele = get_Element(driver, locator);
					if(ele!=null) {
						if(ele.isDisplayed()) {
							String text = ele.getText();
							if(text!=null) {
								if(!text.trim().isEmpty()) {
									Data = text.trim(); 
								}
							}
							
						}
					}
					
				}catch(Exception e) {
					driver.quit();
					throw new Exception();
					
				}
			
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			return Data;
	}
	
	public void ThrowException(RemoteWebDriver driver,By locator) throws Exception {
		
		
		Fail(driver, "Failed : As Element not found "+locator.toString());
		driver.quit();
		throw new Exception();


	}
	
}
