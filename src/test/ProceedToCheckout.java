package test;

import org.openqa.selenium.*;
import org.testng.annotations.Test;
import helper.*;

public class ProceedToCheckout {
	@Test(priority=51)
	public void navigateToCart() {
		Utils.logger("Navigating to cart");
		
		BrowserActions.waitForElementClickable(By.xpath("//button [contains(.,'Proceed to Checkout')]")).click();
		BrowserActions.waitForElement(By.id("my-wishlist"));
		
		Utils.logger("Navigated to cart successfully");
	}
	
	@Test(priority=52)
	public void checkoutWithCart() {
		Utils.logger("Navigating for address selection");
		
		BrowserActions.findElement(By.xpath("//button [contains(.,'Checkout Now')]")).click();
		BrowserActions.waitForElement(By.cssSelector(".mapWrapper"));

		Utils.logger("Navigated to address selection successfully");
	}
}
