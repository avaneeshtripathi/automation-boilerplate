package test;

import org.openqa.selenium.*;
import org.testng.annotations.Test;
import helper.*;

public class SaveAndSelectAddress {
	@Test(priority=61)
	public void mapSelection() {
		Utils.logger("Awaiting google maps");
		
		WebElement searchInput = BrowserActions.waitForElement(By.className("mapSearchBox"));
		
		Utils.logger("Map loaded successfully");
		Utils.logger("Selecting location on map");
		
		searchInput.sendKeys("downtown"); // Taking downtown region as default to select for address
		searchInput.sendKeys(Keys.ENTER);
		BrowserActions.moveClickElementByOffset(By.className("mapWrapper"), 400, 200);
		BrowserActions.waitForElementClickable(By.xpath("//button [contains(.,'Confirm location')]")).click();
		BrowserActions.waitForElement(By.xpath("//* [contains(.,'Edit Address')]"));
		
		Utils.logger("Location selected successfully");
	}
	
	@Test(priority=62)
	public void saveAddressDetails() {
		Utils.logger("Initiating address details submission");
		
		BrowserActions.findElement(By.name("phone")).sendKeys("4242424");
		BrowserActions.findElement(By.name("firstName")).sendKeys("Test");
		BrowserActions.findElement(By.name("lastName")).sendKeys("Order");
		BrowserActions.findElement(By.name("address")).sendKeys("Do Not,  Deliver To This, Address");
		BrowserActions.findElement(By.className("addressLabelRadioLabel")).click();
		BrowserActions.findElement(By.cssSelector(".footerButtonWrapper button")).click();
		BrowserActions.waitForElement(By.xpath("//button [contains(.,'Continue')]")).click();
		
		Utils.logger("Address saved successfully");
		
		BrowserActions.waitForElement(By.className("paymentWrapper"));

		Utils.logger("Address selected successfully");
	}
}
