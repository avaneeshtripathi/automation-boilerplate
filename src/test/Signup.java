package test;

import org.openqa.selenium.*;
import org.testng.annotations.*;
import helper.*;

public class Signup {
  @Test(priority=11)
  public void navigateToSignup() {
	  Utils.logger("Navigating to signup page");
	  
	  BrowserActions.findElement(By.cssSelector(".userIconContainer .userWrapper")).click();
	  BrowserActions.findElement(By.cssSelector(".userIconContainer .linkTextSignup")).click();
	  
	  Utils.logger("Navigated successfully");
  }
  
  @Test(priority=12)
  public void userSignup() {
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
  }
}
