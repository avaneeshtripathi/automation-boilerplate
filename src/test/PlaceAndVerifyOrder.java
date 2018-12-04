package test;

import org.openqa.selenium.*;
import org.testng.annotations.Test;
import helper.*;

public class PlaceAndVerifyOrder {
	private static String orderNr = "";

//	@Test(priority=71)
//	public void payByCard() {
//		Utils.logger("Initiating payment by card");
//		
//		BrowserActions.waitForElement(By.id("ccNumber")).sendKeys(Info.ccNumber);
//		BrowserActions.findElement(By.name("cardExpiryMonth")).sendKeys(Info.cardExpiryMonth);
//		BrowserActions.findElement(By.name("cardExpiryYear")).sendKeys(Info.cardExpiryYear);
//		BrowserActions.findElement(By.cssSelector(".inputWrapper.withTooltip input.neutral")).sendKeys(Info.cardCvv);
//		
//		Utils.logger("Submitting card details");
//		
//		Utils.waitForSeconds(2);
//		BrowserActions.waitForElementClickable(By.cssSelector(".placeOrderCtaContainer button")).click();
//		BrowserActions.waitForElement(By.cssSelector(".orderItemsWrapper .itemContainer"));
//		orderNr = BrowserActions.findElement(By.cssSelector(".successPage .coreOrderDetails")).getText().replace("Order ", "");
//		
//		Utils.logger("Order: " + orderNr + " placed successfully.");
//	}
	
	@Test(priority=71)
	public void payByCash() {
		Utils.logger("Initiating payment by cash");
		
		WebElement codButton = BrowserActions.waitForElement(By.cssSelector(".codOption label.buttonPadding"));
		
		Utils.waitForSeconds(2);
		
		BrowserActions.moveClickElement(codButton);
		BrowserActions.waitForElementClickable(By.cssSelector(".placeOrderCtaContainer button")).click();
		
		Utils.logger("Placing order by cash");
		
		BrowserActions.waitForElement(By.cssSelector(".orderItemsWrapper .itemContainer"));
		orderNr = BrowserActions.findElement(By.cssSelector(".successPage .coreOrderDetails")).getText().replace("Order ", "");
		
		Utils.logger("Order: " + orderNr + " placed successfully.");
	}
	
	@Test(priority=72)
	public void verifyOrder() {
		Utils.logger("Navigating to orders for verification");
		
		BrowserActions.openUrl(Info.accountsAppUrl + "orders/" + orderNr);
		String verificationOrderNr = BrowserActions.waitForElement(By.cssSelector(".orderInfoWrapper .boxHeader h2")).getText().replace("Order ", "");
		
		if (orderNr.equalsIgnoreCase(verificationOrderNr)) {
			Utils.logger("Order: " + orderNr + " verified successfully");
		} else {
			Utils.logger("Order: " + orderNr + " can't be verified");
		}
	}
}
