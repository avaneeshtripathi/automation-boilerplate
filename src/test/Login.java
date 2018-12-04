package test;

import org.openqa.selenium.*;
import org.testng.annotations.Test;
import helper.*;

public class Login {
	@Test(priority=21)
	public void navigateToLogin() {
		Utils.logger("Navigating to login page");
		
		BrowserActions.waitForElementClickable(By.cssSelector(".userIconContainer .userWrapper")).click();
		BrowserActions.findElement(By.cssSelector(".btnContainer .dropdownBtn")).click();
		
		Utils.logger("Navigated successfully");
	}
	
	@Test(priority=22)
	public void userLogin() {
		Utils.logger("Initiating Login");
		
		BrowserActions.waitForElement(By.name("email")).sendKeys(Info.baseEmail);
		BrowserActions.findElement(By.name("password")).sendKeys(Info.basePassword);
		BrowserActions.findElement(By.cssSelector("#formContainer button")).click();

		WebElement userInfoBox = BrowserActions.waitForElement(By.cssSelector(".userIconContainer .userWrapper .userName"));
		String userName = userInfoBox.getText().replace("Hala ", "");

		Info.setUserName(userName);
		Info.setEmail(Info.baseEmail);
		Info.setPassword(Info.basePassword);
		Utils.logger("Hala " + userName + "! Login successfull.");
	}
}
