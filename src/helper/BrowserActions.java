package helper;

import java.util.*;

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
	
	private static void setAuthCookieData() {
		Utils.waitForSeconds(1);
		@SuppressWarnings("deprecation")
		Cookie ck = new Cookie("comauth", "R18n8oPaZb2ApBTQci94Xyjl", ".noonstg.com", "/", new Date("Sat Dec 31 9999 20:27:55 GMT+0530"), true);
		driver.manage().addCookie(ck);
        driver.navigate().refresh();
	}
	
	public static void launchApp() {
		Utils.logger("Reading default data from Excel sheet");
		ExcelUtils.setDefaultData();
		String browser = Defaults.get("app", "browser");
		Utils.logger("Launching in " + browser + " at: " + Defaults.get("app", "baseAppUrl"));
		
		initialiseWebDriver(browser);
		openUrl(browser, Defaults.get("app", "baseAppUrl"));
//		setAuthCookieData();
	}
	
	public static void openUrl(String browser, String url){
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
		return (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.presenceOfElementLocated(selector));
	}
	
	public static boolean waitForOneOfElements(By selector1, By selector2) {
		return (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.or(ExpectedConditions.presenceOfElementLocated(selector1), ExpectedConditions.presenceOfElementLocated(selector2)));
	}
	
	public static WebElement waitForElementClickable(By selector) {
		return (new WebDriverWait(driver, 20))
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
	
	public static int getListItemCount(By selector){
		List<WebElement> elementList = driver.findElements(selector);
		return elementList.size();
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
