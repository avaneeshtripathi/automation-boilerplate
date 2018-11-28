package automationFramework;

import java.util.Date;
import org.openqa.selenium.*;
import org.testng.annotations.*;

public class Signup {
  WebDriver driver = Factory.getDriver("");
  
  @Test(priority=11)
  public void navigateToSignup() {
	  Factory.logger("Navigating to signup page");
	  WebElement headerLink = driver.findElement(By.cssSelector(".userIconContainer .userWrapper"));
	  headerLink.click();
	  
	  WebElement signupButton = driver.findElement(By.cssSelector(".userIconContainer .linkTextSignup"));
	  signupButton.click();
	  Factory.logger("Navigated successfully");
  }
  
  @Test(priority=12)
  @Parameters("signupEmail")
  public void userSignup(String signupEmail) {
	  
	  // SOME FUN STUFF TO GENERATE UNIQUE EMAIL AND PASSWORD
	  int index = signupEmail.indexOf('@');
	  Date date = new Date();
	  StringBuilder userEmail = new StringBuilder(signupEmail);
	  userEmail.insert(index, "+" + date.getTime());
	  String userPassword = Long.toString(date.getTime());
	  // DO NOT COMMENT ABOUT ANYTHING HERE
	 
	  Factory.logger("Initiating Signup");

	  driver.findElement(By.xpath("//input[@name='email'][@type='text']")).sendKeys(userEmail);
	  driver.findElement(By.xpath("//input[@name='email'][@type='password']")).sendKeys(userPassword);
	  driver.findElement(By.name("firstName")).sendKeys("Test");
	  driver.findElement(By.name("lastName")).sendKeys("Name");
	  driver.findElement(By.cssSelector("#formContainer > button")).click();
	  
	  WebElement userInfoBox = Factory.waitForElement(By.cssSelector(".userIconContainer .userWrapper .userName"));
	  String userName = userInfoBox.getText().replace("Hala ", "");
	  

	  if (userName.contains("Test")) {
		  Factory.logger("Hala " + userName + "! Signup successfull.");
		  Factory.setUserName(userName);
		  Factory.setEmail(userEmail.toString());
		  Factory.setPassword(userPassword);
	  } else {
		  Factory.logger("Signup unsuccessfull.");
	  }
  }
}
