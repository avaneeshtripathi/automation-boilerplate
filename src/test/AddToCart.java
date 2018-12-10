package test;

import org.testng.annotations.Test;

import helper.BrowserActions;
import helper.Utils;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;

public class AddToCart {
	private static By PDP_PRODUCT_NAME = By.cssSelector(".coreProductDetails .primaryDetails .coreWrapper h1");
	private static By ADD_TO_CART_BUTTON = By.cssSelector(".pdpCtaContainer button");
	private static By PANEL_PRODUCT_BOX = By.cssSelector(".panelContainer .panel .productContainer");
	private static By PROCEED_CHECKOUT_BUTTON = By.xpath("//button [contains(.,'Proceed to Checkout')]");
	private static By CART_PRODUCT_NAME = By.cssSelector(".cartItemsColumn .coreDetailsContainer .productName");
	
	@BeforeTest
    @Parameters({"browser", "searchKeyword"})
    public void beforeTest(String browser, String searchKeyword) {
        BrowserActions.launchApp(browser);
        SearchProduct.navigateToProduct(searchKeyword);
    }

	private static void addProductToCart() {
		String productName = BrowserActions.getTextContent(PDP_PRODUCT_NAME);
		
		Utils.logger("Adding product to cart:\n" + productName);
		
		BrowserActions.click(ADD_TO_CART_BUTTON);
		BrowserActions.waitForElement(PANEL_PRODUCT_BOX);
		
		Utils.logger("Added to cart successfully");
	}
	
	@Test(priority=1)
	public static void goToCart() {
		addProductToCart();
		
		Utils.logger("Navigating to cart");
		
		BrowserActions.waitForElementClickable(PROCEED_CHECKOUT_BUTTON);
		BrowserActions.click(PROCEED_CHECKOUT_BUTTON);
		BrowserActions.waitForElement(CART_PRODUCT_NAME);
		Utils.waitForSeconds(1);
		String productName = BrowserActions.getTextContent(CART_PRODUCT_NAME);
		
		Utils.logger("Navigated to cart successfully. Product in cart: \n" + productName);
	}
	
    @AfterTest
    public void afterTest() {
        BrowserActions.closeSession();
    }
}
