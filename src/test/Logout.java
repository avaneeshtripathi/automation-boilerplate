package test;

import org.openqa.selenium.*;
import org.testng.annotations.*;
import helper.*;

public class Logout {
	private static By HEADER_MENU = By.cssSelector(".userIconContainer .userWrapper");
	private static By LOGOUT_BUTTON = By.cssSelector(".userIconContainer .dropdownCtr .logOut");
	private static By LOGIN_EMAIL_FIELD = By.name("email");
	
	@BeforeTest
    public void beforeTest() {
        BrowserActions.launchApp();
		Login.authenticate();
    }
	
	@Test(priority=1)
	@Parameters("browser")
	public void logoutUser(String browser) {
		Utils.logger("Initiating user logout");

		if (!Defaults.get("app", "baseAppUrl").equalsIgnoreCase(BrowserActions.getCurrentUrl())) {
			BrowserActions.openUrl(browser, Defaults.get("app", "baseAppUrl"));
		}
		
		BrowserActions.waitForElement(HEADER_MENU);
		BrowserActions.click(HEADER_MENU);
		BrowserActions.waitForElement(LOGOUT_BUTTON);
		BrowserActions.click(LOGOUT_BUTTON);
		BrowserActions.waitForElement(LOGIN_EMAIL_FIELD);
		
		Utils.logger("Logged out successfully");
		Utils.logger("Signing off");
		Utils.logger("Bye " + Defaults.get("user", "firstName") + " :)");
	}
	
    @AfterTest
    public void afterTest() {
        BrowserActions.closeSession();
    }
}
