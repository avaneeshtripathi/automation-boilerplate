package automationFramework;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class PlaceAndVerifyOrder {
	WebDriver driver = Factory.getDriver("");
	
	private static String orderNr = "";

//	@Test(priority=71)
//	public void payByCard() {
//		Factory.logger("Initiating payment by card");
//		
//		Factory.waitForElement(By.id("ccNumber")).sendKeys("4242424242424242");
//		driver.findElement(By.name("cardExpiryMonth")).sendKeys("12");
//		driver.findElement(By.name("cardExpiryYear")).sendKeys("21");
//		driver.findElement(By.cssSelector(".inputWrapper.withTooltip input.neutral")).sendKeys("123");
//		Factory.waitForElementClickable(By.cssSelector(".placeOrderCtaContainer button")).click();
//		
//		Factory.logger("Submitting card details");
//	}
	
	@Test(priority=71)
	public void payByCash() {
		Factory.logger("Initiating payment by cash");
		
		Actions actions = new Actions(driver);
		WebElement codButton = Factory.waitForElement(By.cssSelector(".codOption label.buttonPadding"));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Factory.logger("Sleep failed");
		}
		actions.moveToElement(codButton).click().perform();
		Factory.waitForElementClickable(By.cssSelector(".placeOrderCtaContainer button")).click();
		
		Factory.logger("Placing order by cash");
		
		Factory.waitForElement(By.cssSelector(".orderItemsWrapper .itemContainer"));
		orderNr = driver.findElement(By.cssSelector(".successPage .coreOrderDetails")).getText().replace("Order ", "");
		
		Factory.logger("Order: " + orderNr + " placed successfully.");
	}
	
	@Test(priority=72)
	public void verifyOrder() {
		Factory.logger("Navigating to orders for verification");
		
		driver.get("https://account-alt.noon.com/uae-en/orders/" + orderNr);
		String verificationOrderNr = Factory.waitForElement(By.cssSelector(".orderInfoWrapper .boxHeader h2")).getText().replace("Order ", "");
		
		if (orderNr.equalsIgnoreCase(verificationOrderNr)) {
			Factory.logger("Order: " + orderNr + " verified successfully");
		} else {
			Factory.logger("Order: " + orderNr + " can't be verified");
		}
	}
}
