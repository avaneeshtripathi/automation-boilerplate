package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import helper.BrowserActions;
import helper.Info;
import helper.Utils;

public class Authenticate {
	@Test(priority=11)
	public void navigateToAuthenticate() {
		Utils.logger("Navigating for authentication");

		if (Info.newUser) {
			BrowserActions.findElement(By.cssSelector(".userIconContainer .userWrapper")).click();
			BrowserActions.findElement(By.cssSelector(".userIconContainer .linkTextSignup")).click();
		} else {
			BrowserActions.waitForElementClickable(By.cssSelector(".userIconContainer .userWrapper")).click();
			BrowserActions.findElement(By.cssSelector(".btnContainer .dropdownBtn")).click();
		}

		Utils.logger("Navigated successfully");
	}
	
	@Test(priority=12)
	public void authenticate() {
		if (Info.newUser) {
			String userEmail = Utils.getUniqueEmail(Info.baseEmail);
			String userPassword = Info.basePassword;

			Utils.logger("Initiating Signup");

			BrowserActions.findElement(By.xpath("//input[@name='email'][@type='text']")).sendKeys(userEmail);
			BrowserActions.findElement(By.xpath("//input[@name='email'][@type='password']")).sendKeys(userPassword);
			BrowserActions.findElement(By.name("firstName")).sendKeys(Info.baseFirstName);
			BrowserActions.findElement(By.name("lastName")).sendKeys(Info.baseLastName);
			BrowserActions.findElement(By.cssSelector("#formContainer > button")).click();
			
			WebElement userInfoBox = BrowserActions.waitForElement(By.cssSelector(".userIconContainer .userWrapper .userName"));
			String userName = userInfoBox.getText().replace("Hala ", "");
			  
			if (userName.contains("Test")) {
				Utils.logger("Hala " + userName + "! Signup successfull.");
				  
				Info.setUserName(userName);
				Info.setEmail(userEmail);
				Info.setPassword(userPassword);
			} else {
				Utils.logger("Signup unsuccessfull.");
			}
		} else {
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
}
