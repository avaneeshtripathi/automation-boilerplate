package test;

import org.testng.annotations.Test;

import helper.BrowserActions;
import helper.Utils;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;

public class AddToCart {
	private static By ADD_TO_CART_BUTTON = By.cssSelector(".pdpCtaContainer button");
	private static By PANEL_PRODUCT_BOX = By.cssSelector(".panelContainer .panel .productContainer");
	private static By PROCEED_CHECKOUT_BUTTON = By.xpath("//button [contains(.,'Proceed to Checkout')]");
	private static By CART_WRAPPER = By.id("my-wishlist");
	
	@BeforeTest
    @Parameters("browser")
    public void beforeTest(String browser) {
        BrowserActions.launchApp(browser);
        SearchProduct.searchForProduct();
        SearchProduct.navigateToProduct();
    }
	
	@Test(priority=1)
	public static void addProductToCart() {
		Utils.logger("Adding product to cart");
		
		BrowserActions.click(ADD_TO_CART_BUTTON);
		BrowserActions.waitForElement(PANEL_PRODUCT_BOX);
		
		Utils.logger("Added to cart successfully");
	}
	
	@Test(priority=2)
	public static void navigateToCart() {
		Utils.logger("Navigating to cart");
		
		BrowserActions.waitForElementClickable(PROCEED_CHECKOUT_BUTTON);
		BrowserActions.click(PROCEED_CHECKOUT_BUTTON);
		BrowserActions.waitForElement(CART_WRAPPER);
		
		Utils.logger("Navigated to cart successfully");
	}
	
    @AfterTest
    public void afterTest() {
        BrowserActions.closeSession();
    }
}
