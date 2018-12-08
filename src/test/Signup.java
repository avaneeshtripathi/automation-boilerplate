package test;

import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import helper.*;

public class Signup {
	private static By HEADER_MENU = By.cssSelector(".userIconContainer .userWrapper");
	private static By SIGNUP_LINK = By.cssSelector(".userIconContainer .linkTextSignup");
	private static By SIGNUP_EMAIL_FIELD = By.xpath("//input[@name='email'][@type='text']");
	private static By SIGNUP_PASSWORD_FIELD = By.xpath("//input[@name='email'][@type='password']");
	private static By SIGNUP_FIRSTNAME_FIELD = By.name("firstName");
	private static By SIGNUP_LASTNAME_FIELD = By.name("lastName");
	private static By SUBMIT_BUTTON = By.cssSelector("#formContainer > button");
	private static By USER_INFO_BOX = By.cssSelector(".userIconContainer .userWrapper .userName");
	
    @BeforeTest
    @Parameters("browser")
    public void beforeTest(String browser) {
        BrowserActions.launchApp(browser);
    }
    
	@Test(priority=1)
	public void navigateToSignup() {
		Utils.logger("Navigating for signup");

		BrowserActions.waitForElement(HEADER_MENU);
		BrowserActions.click(HEADER_MENU);
		BrowserActions.click(SIGNUP_LINK);

		Utils.logger("Navigated successfully");
	}
	
	@Test(priority=2)
	public void authenticate() {
		Utils.logger("Initiating Signup");

		BrowserActions.waitForElement(SIGNUP_EMAIL_FIELD);
		String userEmail = Utils.getUniqueEmail(Defaults.User.get("email"));
		String userPassword = Defaults.User.get("password");
		BrowserActions.input(SIGNUP_EMAIL_FIELD, userEmail);
		BrowserActions.input(SIGNUP_PASSWORD_FIELD, userPassword);
		BrowserActions.input(SIGNUP_FIRSTNAME_FIELD, Defaults.User.get("firstName"));
		BrowserActions.input(SIGNUP_LASTNAME_FIELD, Defaults.User.get("lastName"));
		BrowserActions.click(SUBMIT_BUTTON);
		BrowserActions.waitForElement(USER_INFO_BOX);
		String userName = BrowserActions.getTextContent(USER_INFO_BOX).replace("Hala ", "");
		
		Utils.logger("Hala " + userName + "! Signup successfull.");
	}
	
    @AfterTest
    public void afterTest() {
        BrowserActions.closeSession();
    }
}
