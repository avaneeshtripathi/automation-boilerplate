package test;

import org.openqa.selenium.*;
import org.testng.annotations.*;
import helper.*;

public class SearchProduct {
	private static By HEADER_SEARCH_INPUT = By.id("searchBar");
	private static By PRODUCT_CARD = By.cssSelector(".productListContainer .productListWrapper .productList .product");
	private static By PRODUCT_DETAIL_WRAPPER = By.cssSelector(".coreProductDetails .primaryDetails");
	
    @BeforeTest
    public void beforeTest() {
        BrowserActions.launchApp();
    }
    
	private static void searchForProduct(String searchKeyword) {
		Utils.logger("Searching Keyword: " + "munch");
		
		BrowserActions.input(HEADER_SEARCH_INPUT, "munch");
		BrowserActions.submit(HEADER_SEARCH_INPUT);
		
		Utils.logger("Searched successfully");
	}
	

	@Test(priority=1)
    @Parameters("searchKeyword")
	public static void navigateToProduct(String searchKeyword) {
		searchForProduct(searchKeyword);
		
		Utils.logger("Awaiting product list");
		
		BrowserActions.waitForElement(PRODUCT_CARD);
		
		Utils.logger("Product list loaded successfully");
		
		String product = BrowserActions.clickRandomInList(PRODUCT_CARD).getText();
		
		Utils.logger("Navigating to randomly selected product:\n" + product);
		Utils.logger("Awaiting product detail page");
		
		BrowserActions.waitForElement(PRODUCT_DETAIL_WRAPPER);
		
		Utils.logger("Navigated successfully");
	}
	
    @AfterTest
    public void afterTest() {
        BrowserActions.closeSession();
    }
}
