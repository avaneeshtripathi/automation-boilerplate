package helper;

import java.io.*;
import java.text.SimpleDateFormat;
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
		
		initialiseWebDriver(browser);
		openUrl(browser, Defaults.App.get("baseAppUrl"));
//		getAuthCookieData(browser);
	}
	
	public static void openUrl(String browser, String url){
		driver.get(url);
		setAuthCookieData(); // ENABLE THIS TO USE COOKIES FOR THE THINGS BUT THEY FIRST NEED TO BE SET USING getAuthCookieData()
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
	
	// PRIVATE UTILITY METHODS TO GET COOKIE FROM ONE SESSIO  TO ANOTHER
	// USE GET AUTH COOKIE DATA TO SET THE COOKIE FROM FILE TO SESSION
	// USE SET AUTH COOKIE DATA TO STORE THE COOKIES TO A FILE
	private static void getAuthCookieData(String browser) {
		openUrl(browser, "https://misc-team.noonstg.team/com-auth");
		BrowserActions.waitForElement(By.id("identifierId"));
		BrowserActions.input(By.id("identifierId"), "xyz@noon.com"); // OFFICIAL EMAIL ID
		BrowserActions.click(By.id("identifierNext"));
		BrowserActions.waitForElement(By.id("user_email"));
		BrowserActions.input(By.id("user_email"), "xyz@noon.com"); // OFFICIAL EMAIL ID
		BrowserActions.input(By.id("user_password"), "xyzxyzxyz123"); // ONE LOGIN PASSWORD
		BrowserActions.click(By.id("user_submit"));
		Utils.logger("Start wait for 100 seconds. Expecting you to login successfully in 100 seconds");
		Utils.waitForSeconds(100);
		Utils.logger("Waited for 100 seconds. I'll read the cookies and save them to Cookies.txt now");
		File file = new File("Cookies.txt");
		
		try	{	  
            // Delete old file if exists
			file.delete();		
            file.createNewFile();			
            FileWriter fileWrite = new FileWriter(file);							
            BufferedWriter Bwrite = new BufferedWriter(fileWrite);							
            // loop for getting the cookie information 		
            	
            // loop for getting the cookie information 		
            for(Cookie ck : driver.manage().getCookies())							
            {
                Bwrite.write((ck.getName()+";"+ck.getValue()+";"+ck.getDomain()+";"+ck.getPath()+";"+ck.getExpiry()+";"+ck.isSecure()));																									
                Bwrite.newLine();             
            }			
            Bwrite.close();			
            fileWrite.close();
    		Utils.logger("Cookies recorded successfully.");
        } catch(Exception ex) {		
            ex.printStackTrace();			
        }
	}
	
	private static void setAuthCookieData() {
		try {
			Utils.waitForSeconds(2);
	        File file = new File("Cookies.txt");							
	        FileReader fileReader = new FileReader(file);							
	        BufferedReader Buffreader = new BufferedReader(fileReader);							
	        String strline;			
	        while((strline=Buffreader.readLine())!=null){									
	        	StringTokenizer token = new StringTokenizer(strline,";");
	        	while(token.hasMoreTokens()){
			        String name = token.nextToken();					
			        String value = token.nextToken();				
			        String domain = token.nextToken();					
			        String path = token.nextToken();					
			        Date expiry = null;
			        
					if(value.contains(".com")) {
						token.nextToken();
						continue;
					}
			        
					String val;			
			        if(!(val=token.nextToken()).equals("null")){
			        	Date parsed;
			        	try {
			        	    SimpleDateFormat format =
			        	        new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
			        	    parsed = format.parse(val);
			        	}
			        	catch(Exception pe) {
			        	    throw new IllegalArgumentException(pe);
			        	}
			        	expiry = parsed;					
			        }		
			        Boolean isSecure = new Boolean(token.nextToken()).								
			        		booleanValue();		
			        Cookie ck = new Cookie(name,value,domain,path,expiry,isSecure);
			        driver.manage().addCookie(ck); // This will add the stored cookie to your current session					
	        	}		
	        }
	        Buffreader.close();
			Defaults.setPreAuthorised();
	        driver.navigate().refresh();
        } catch(Exception ex){	
	        ex.printStackTrace();			
        }
	}
}
