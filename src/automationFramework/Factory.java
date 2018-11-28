package automationFramework;

import org.testng.annotations.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class Factory {
	// INITIALISATIONS FOR FACTORY DATA MEMBERS
	private static WebDriver driver = null;
	private static String userName = "";
	private static String email = "";
	private static String password = "";
	
	
	// GETTER AND SETTER METHODS FOR LOGGED USER
	public static void setUserName(String text) {
		userName = text;
		logger("Username <" + userName + "> set to factory.");
	}
	
	public static void setEmail(String text) {
		email = text;
		logger("Email <" + email + "> set to factory.");
	}
	
	public static void setPassword(String text) {
		password = text;
		logger("Password <" + password + "> set to factory.");
	}
	
	public static String getUserName() {
		return userName;
	}
	
	public static String getEmail() {
		return email;
	}
	
	public static String getPassword() {
		return password;
	}
	
	
	// INITIALISATIONS
	@Parameters("browser")
	public static WebDriver getDriver(String browser) {
		if (driver == null) {
			logger("Initialising Web Driver");
			if (browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", "/Users/atripathi/avi/automation-boilerplate/drivers/geckodriver");
				driver = new FirefoxDriver();
			} else {
				System.setProperty("webdriver.chrome.driver", "/Users/atripathi/avi/automation-boilerplate/drivers/chromedriver");
				driver = new ChromeDriver();
			}
			logger("Web Driver Initialised");
		}
		return driver;
	}
	
	
	// UTILITY METHODS
	public static void logger(String text) {
		System.out.println(text);
		System.out.println("========================================================");
	}
	
	public static WebElement waitForElement(By selector) {
		return (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(selector));
	}
	
	public static WebElement waitForElementClickable(By selector) {
		return (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.elementToBeClickable(selector));
	}
	
	public static int getRandomUpto(int max){
		return (int) (Math.random()*max);
	}
	
	public static void clickElementByOffset(By selector, int x, int y){
		WebElement element = driver.findElement(selector);
		Actions action = new Actions(driver);
		action.moveToElement(element).moveByOffset(x, y).click().perform();
	}
}