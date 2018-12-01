package helper;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.interactions.Actions;

import helper.Utils;

public class BrowserActions {
	private static WebDriver driver = null;
	
	public static void initialiseWebDriver(String browser) {
		if (driver == null) {
			Utils.logger("Initialising Web Driver");
			if (browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", "/Users/atripathi/avi/automation-boilerplate/drivers/geckodriver");
				driver = new FirefoxDriver();
			} else {
				System.setProperty("webdriver.chrome.driver", "/Users/atripathi/avi/automation-boilerplate/drivers/chromedriver");
				driver = new ChromeDriver();
			}
			Utils.logger("Web Driver Initialised");
		}
	}
	
	public static WebElement findElement(By selector) {
		return driver.findElement(selector);
	}
	
	public static List<WebElement> findElements(By selector) {
		return driver.findElements(selector);
	}
	
	public static WebElement waitForElement(By selector) {
		return (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(selector));
	}
	
	public static WebElement waitForElementClickable(By selector) {
		return (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.elementToBeClickable(selector));
	}
	
	public static void moveClickElement(WebElement element){
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}
	
	public static void moveClickElementByOffset(By selector, int x, int y){
		WebElement element = driver.findElement(selector);
		Actions action = new Actions(driver);
		action.moveToElement(element).moveByOffset(x, y).click().perform();
	}
	
	public static void openUrl(String url){
		driver.get(url);
	}
	
	public static void closeSession(){
		driver.close();
	}
}
