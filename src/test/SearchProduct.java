package test;

import org.openqa.selenium.*;
import org.testng.annotations.*;
import helper.*;

public class SearchProduct {
	private static By HEADER_SEARCH_INPUT = By.id("searchBar");
	private static By PRODUCT_CARD = By.cssSelector(".productList .productContainer .product");
	private static By PRODUCT_DETAIL_WRAPPER = By.cssSelector(".coreProductDetails .primaryDetails");
	
    @BeforeTest
    @Parameters("browser")
    public void beforeTest(String browser) {
        BrowserActions.launchApp(browser);
    }
    
	@Test(priority=1)
	public static void searchForProduct() {
		Utils.logger("Searching Keyword: " + "munch");
		
		BrowserActions.input(HEADER_SEARCH_INPUT, "munch");
		BrowserActions.submit(HEADER_SEARCH_INPUT);
		
		Utils.logger("Searched successfully");
	}
	

	@Test(priority=2)
	public static void navigateToProduct() {
		Utils.logger("Awaiting product list");
		
		BrowserActions.waitForElement(PRODUCT_CARD);
		
		Utils.logger("Product list loaded successfully");
		
		BrowserActions.clickRandomInList(PRODUCT_CARD);
		
		Utils.logger("Navigating to randomly selected product");
		Utils.logger("Awaiting product detail page");
		
		BrowserActions.waitForElement(PRODUCT_DETAIL_WRAPPER);
		
		Utils.logger("Navigated successfully");
	}
	
    @AfterTest
    public void afterTest() {
        BrowserActions.closeSession();
    }
}
