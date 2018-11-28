package automationFramework;

import org.openqa.selenium.*;
import org.testng.annotations.*;

public class Logout {
	WebDriver driver = Factory.getDriver("");
  
	@Test(priority=81)
	@Parameters("homeUrl")
	public void logoutUser(String homeUrl) {
		Factory.logger("Navigating to home page");
		
		driver.get(homeUrl);
		Factory.waitForElement(By.cssSelector(".userIconContainer .userWrapper .userName"));

		Factory.logger("Navigated successfully");
		Factory.logger("Initiating user logout");
		
		driver.findElement(By.cssSelector(".userIconContainer .userWrapper")).click();
		Factory.waitForElement(By.cssSelector(".userIconContainer .dropdownCtr .logOut")).click();
		Factory.waitForElement(By.name("email"));
		
		Factory.logger("Logged out successfully");
		Factory.logger("Signing off");
		Factory.logger("Bye " + Factory.getUserName() + " :)");
		driver.close();
	}
}
