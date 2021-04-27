package IB.Page;

import org.openqa.selenium.By;

public class HomePage {

	
	public By Logout_Link() {
		return By.xpath("//a[.='Log Out']");
	}
	
	public By Display_Name() {
		return By.xpath("//b[.='Welcome']/..");
	}
	
	public By Accounts_Overview_Txt() {
		return By.xpath("//h1[.='Accounts Overview']");
	}
	
	
	
	
	
}
