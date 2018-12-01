package test;

import org.openqa.selenium.*;
import org.testng.annotations.Test;
import helper.*;

public class AddToCart {
	@Test(priority=41)
	public void addProductToCart() {
		Utils.logger("Adding product to cart");
		
		BrowserActions.findElement(By.cssSelector(".pdpCtaContainer button")).click();
		BrowserActions.waitForElement(By.cssSelector(".panelContainer .panel .productContainer"));
		
		Utils.logger("Added to cart successfully");
	}
}
