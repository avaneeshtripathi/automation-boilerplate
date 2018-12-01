package test;

import org.openqa.selenium.*;
import org.testng.annotations.*;
import helper.*;

public class Logout {
	@Test(priority=81)
	@Parameters("homeUrl")
	public void logoutUser(String homeUrl) {
		Utils.logger("Navigating to home page");
		
		BrowserActions.openUrl(homeUrl);
		BrowserActions.waitForElement(By.cssSelector(".userIconContainer .userWrapper .userName"));

		Utils.logger("Navigated successfully");
		Utils.logger("Initiating user logout");
		
		BrowserActions.findElement(By.cssSelector(".userIconContainer .userWrapper")).click();
		BrowserActions.waitForElement(By.cssSelector(".userIconContainer .dropdownCtr .logOut")).click();
		BrowserActions.waitForElement(By.name("email"));
		
		Utils.logger("Logged out successfully");
		Utils.logger("Signing off");
		Utils.logger("Bye " + Info.getUserName() + " :)");
		
		BrowserActions.closeSession();
	}
}
