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
	
	// BROWSER UTILITIES
	private static void initialiseWebDriver(String browser) {
		if (driver == null) {
			Utils.logger("Initialising Web Driver");
			if (browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", "drivers/geckodriver");
				driver = new FirefoxDriver();
			} else {
				System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
				driver = new ChromeDriver();
			}
			Utils.logger("Web Driver Initialised");
		}
	}
	
	public static void launchApp(String browser) {
		Utils.logger("Reading default data from Excel sheet");
		ExcelUtils.readDefaultData();
		Utils.logger("Launching in " + browser + " at: " + Defaults.App.get("baseAppUrl"));
		openUrl(browser, Defaults.App.get("baseAppUrl"));
	}
	
	public static void openUrl(String browser, String url){
		initialiseWebDriver(browser);
		driver.get(url);
	}
	
	public static void closeSession(){
		driver.close();
	}
	
	public static String getCurrentUrl() {
		return driver.getCurrentUrl();
	}
	
	// BROWSER ACTIONS
	public static void click(By selector){
		driver.findElement(selector).click();
	}
	
	public static void input(By selector, String data){
		driver.findElement(selector).sendKeys(data);
	}
	
	public static void submit(By selector){
		driver.findElement(selector).sendKeys(Keys.ENTER);
	}
	
	public static WebElement waitForElement(By selector) {
		return (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfElementLocated(selector));
	}
	
	public static boolean waitForOneOfElements(By selector1, By selector2) {
		return (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.or(ExpectedConditions.presenceOfElementLocated(selector1), ExpectedConditions.presenceOfElementLocated(selector2)));
	}
	
	public static WebElement waitForElementClickable(By selector) {
		return (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.elementToBeClickable(selector));
	}
	
	public static String getTextContent(By selector){
		return waitForElement(selector).getText();
	}
	
	public static WebElement clickRandomInList(By selector){
		List<WebElement> elementList = driver.findElements(selector);
		int randomProductIndex = Utils.getRandomUpto(elementList.size());
		WebElement randomElement = elementList.get(randomProductIndex);
		clickWithAction(randomElement);
		return randomElement;
	}
	
	public static void clickWithAction(WebElement element){
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}
	
	public static boolean checkElementExists(By selector){
		try {
			driver.findElement(selector);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static void clickByOffset(By selector, int x, int y){
		WebElement element = driver.findElement(selector);
		Actions action = new Actions(driver);
		action.moveToElement(element).moveByOffset(x, y).click().perform();
	}
}
