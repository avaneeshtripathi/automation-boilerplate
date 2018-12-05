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
		BrowserActions.waitForOneOfElements(By.cssSelector(".mapWrapper"), By.cssSelector(".addNewAddressCtr .addNewAddress"));
		Info.setPreSavedAddresses(BrowserActions.findElement(By.cssSelector(".mapWrapper")) == null);
		
		Utils.logger("Navigated to address selection successfully");
	}
}
