package automationFramework;

import org.openqa.selenium.*;
import org.testng.annotations.Test;

public class AddToCart {
	WebDriver driver = Factory.getDriver("");
	
	@Test(priority=41)
	public void addProductToCart() {
		Factory.logger("Adding product to cart");
		
		driver.findElement(By.cssSelector(".pdpCtaContainer button")).click();
		Factory.waitForElement(By.cssSelector(".panelContainer .panel .productContainer"));
		
		Factory.logger("Added to cart successfully");
	}
}
