package managers;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverManager {
	
	RemoteWebDriver webdriver ;
	
	
	public final void setDriver(String browser) {
		switch(browser) {
			case "chrome":
				System.setProperty("webdriver.chrome.driver", ".//Drivers//chromedriver.exe");
				webdriver = new ChromeDriver();
				break;
				
			case "ie":
				System.setProperty("webdriver.ie.driver", ".//Drivers//IEDriverServer.exe");
				webdriver = new InternetExplorerDriver();
				break;
			
		}
	}
	
	
	public RemoteWebDriver getDriver() {
		return webdriver;
	}
	
	
	
	
	

}
