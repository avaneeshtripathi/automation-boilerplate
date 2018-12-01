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

		String userEmail = "atripathi+1@noon.com";
		String userPassword = "avaneesh123";
		
		BrowserActions.waitForElement(By.name("email")).sendKeys(userEmail);
		BrowserActions.findElement(By.name("password")).sendKeys(userPassword);
		BrowserActions.findElement(By.cssSelector("#formContainer button")).click();

		WebElement userInfoBox = BrowserActions.waitForElement(By.cssSelector(".userIconContainer .userWrapper .userName"));
		String userName = userInfoBox.getText().replace("Hala ", "");

		Info.setUserName(userName);
		Info.setEmail(userEmail);
		Info.setPassword(userPassword);
		Utils.logger("Hala " + userName + "! Login successfull.");
	}
}
