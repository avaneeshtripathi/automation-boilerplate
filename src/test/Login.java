package test;

import org.openqa.selenium.By;
import org.testng.annotations.*;
import helper.*;

public class Login {
	private static By HEADER_MENU = By.cssSelector(".userIconContainer .userWrapper");
	private static By LOGIN_LINK = By.cssSelector(".btnContainer .dropdownBtn");
	private static By LOGIN_EMAIL_FIELD = By.name("email");
	private static By LOGIN_PASSWORD_FIELD = By.name("password");
	private static By SUBMIT_BUTTON = By.cssSelector("#formContainer > button");
	private static By USER_INFO_BOX = By.cssSelector(".userIconContainer .userWrapper .userName");
	
    @BeforeTest
    public void beforeTest() {
        BrowserActions.launchApp();
    }

	private static void navigateForLogin() {
		Utils.logger("Navigating for login");

		BrowserActions.waitForElement(HEADER_MENU);
		BrowserActions.click(HEADER_MENU);
		BrowserActions.click(LOGIN_LINK);

		Utils.logger("Navigated successfully");
	}
	
	@Test(priority=2)
	public static void authenticate() {
		navigateForLogin();
		
		Utils.logger("Initiating Login");
		
		BrowserActions.waitForElement(LOGIN_EMAIL_FIELD);
		BrowserActions.input(LOGIN_EMAIL_FIELD, Defaults.get("user", "email"));
		BrowserActions.input(LOGIN_PASSWORD_FIELD, Defaults.get("user", "password"));
		BrowserActions.click(SUBMIT_BUTTON);
		BrowserActions.waitForElement(USER_INFO_BOX);
		String userName = BrowserActions.getTextContent(USER_INFO_BOX).replace("Hala ", "");
		
		Utils.logger("Hala " + userName + "! Login successfull.");
	}
	
    @AfterTest
    public void afterTest() {
        BrowserActions.closeSession();
    }
}
