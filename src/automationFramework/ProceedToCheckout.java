package automationFramework;

import org.openqa.selenium.*;
import org.testng.annotations.Test;

public class ProceedToCheckout {
	WebDriver driver = Factory.getDriver("");

	@Test(priority=51)
	public void navigateToCart() {
		Factory.logger("Navigating to cart");
		
		Factory.waitForElementClickable(By.xpath("//button [contains(.,'Proceed to Checkout')]")).click();
		Factory.waitForElement(By.id("my-wishlist"));
		
		Factory.logger("Navigated to cart successfully");
	}
	
	@Test(priority=52)
	public void checkoutWithCart() {
		Factory.logger("Navigating for address selection");
		
		driver.findElement(By.xpath("//button [contains(.,'Checkout Now')]")).click();
		Factory.waitForElement(By.cssSelector(".mapWrapper"));

		Factory.logger("Navigated to address selection successfully");
	}
}
