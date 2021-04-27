package IB.Test_Functions;

import java.util.Hashtable;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.codoid.products.exception.FilloException;

import IB.Page.HomePage;
import IB.Page.LoginPage;
import Reusable_Methods.Utility;
import managers.DriverManager;




public class Function {

	LoginPage Login = new LoginPage();
	HomePage homepage = new HomePage();
	Utility util = new Utility();
	DriverManager driver = new DriverManager();
	
	
	
	public void Login() throws FilloException, InterruptedException {
		System.out.println("Login called");
		System.out.println(util.DataLogin.get("URL"));
		driver.setDriver(util.DataLogin.get("Browsername"));
		System.out.println(util.DataLogin.get("URL"));
		driver.getDriver().get(util.DataLogin.get("URL"));
		Thread.sleep(2000);
		util.Pass(driver.getDriver(),"Successfully google has been opened....");
		driver.getDriver().manage().window().maximize();
		
		util.Press_Enter();
		util.Info(driver.getDriver(),"London searched....");
			
	}
	
	public void Logout() throws Exception {
		util.Info(driver.getDriver(),"Logout method..");
	
		System.out.println("Logout called");
		util.Click(driver.getDriver(), homepage.Display_Name(), 4);
		driver.getDriver().quit();
	}
	
	public void Method_1() throws FilloException, InterruptedException {
		Login();
		util.Info(driver.getDriver(),"Logout method..");
		System.out.println("Logout called"+util.Data.get("AccountNumber"));
	}
	
	
	
}
