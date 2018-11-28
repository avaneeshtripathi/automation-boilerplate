package automationFramework;

import org.openqa.selenium.*;
import org.testng.annotations.Test;

public class Login {
	WebDriver driver = Factory.getDriver("");
  
	@Test(priority=21)
	public void navigateToLogin() {
		Factory.logger("Navigating to login page");
		
		Factory.waitForElementClickable(By.cssSelector(".userIconContainer .userWrapper")).click();
		driver.findElement(By.cssSelector(".btnContainer .dropdownBtn")).click();
		
		Factory.logger("Navigated successfully");
	}
	
	@Test(priority=22)
	public void userLogin() {
		Factory.logger("Initiating Login");

		String userEmail = "atripathi+1@noon.com";
		String userPassword = "avaneesh123";
		
		Factory.waitForElement(By.name("email")).sendKeys(userEmail);
		driver.findElement(By.name("password")).sendKeys(userPassword);
		driver.findElement(By.cssSelector("#formContainer button")).click();

		WebElement userInfoBox = Factory.waitForElement(By.cssSelector(".userIconContainer .userWrapper .userName"));
		String userName = userInfoBox.getText().replace("Hala ", "");

		Factory.setUserName(userName);
		Factory.setEmail(userEmail);
		Factory.setPassword(userPassword);
		Factory.logger("Hala " + userName + "! Login successfull.");
	}
}
