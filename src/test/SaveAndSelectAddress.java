package test;

import org.openqa.selenium.*;
import org.testng.annotations.Test;
import helper.*;

public class SaveAndSelectAddress {
	@Test(priority=61)
	public void handleAddressSelection() {
		Utils.waitForSeconds(1);
		if (Info.preSavedAddresses) {
			Utils.logger("Previously saved addresses available");
			if (Info.addNewAddress) {
				Utils.logger("Initiating to add a new address");
				
				BrowserActions.findElement(By.cssSelector(".addNewAddressCtr .addNewAddress")).click();
				mapSelection();
				saveAddressDetails();
			} else {
				Utils.logger("Selecting previously saved address");
				
				BrowserActions.waitForElementClickable(By.xpath("//button [contains(.,'Continue')]")).click();
				BrowserActions.waitForElement(By.className("paymentWrapper"));
				
				Utils.logger("Address selected successfully");
			}
		} else {
			Utils.logger("No previously saved addresses found");
			
			mapSelection();
			saveAddressDetails();
		}
	}

	private static void mapSelection() {
		Utils.logger("Awaiting google maps");
		
		WebElement searchInput = BrowserActions.waitForElement(By.className("mapSearchBox"));
		
		Utils.logger("Map loaded successfully");
		Utils.logger("Selecting location on map");
		
		searchInput.sendKeys(Info.mapRegion);
		searchInput.sendKeys(Keys.ENTER);
		BrowserActions.moveClickElementByOffset(By.className("mapWrapper"), 400, 200);
		BrowserActions.waitForElementClickable(By.xpath("//button [contains(.,'Confirm location')]")).click();
		BrowserActions.waitForElement(By.xpath("//* [contains(.,'Edit Address')]"));
		
		Utils.logger("Location selected successfully");
	}

	private static void saveAddressDetails() {
		Utils.logger("Initiating address details submission");
		
		BrowserActions.findElement(By.name("phone")).sendKeys(Info.phoneNumber);
		BrowserActions.findElement(By.name("firstName")).sendKeys(Info.baseFirstName);
		BrowserActions.findElement(By.name("lastName")).sendKeys(Info.baseLastName);
		BrowserActions.findElement(By.name("address")).sendKeys(Info.address);
		BrowserActions.findElement(By.className("addressLabelRadioLabel")).click();
		BrowserActions.findElement(By.cssSelector(".footerButtonWrapper button")).click();
		BrowserActions.waitForElement(By.xpath("//button [contains(.,'Continue')]")).click();
		
		Utils.logger("Address saved successfully");
		
		BrowserActions.waitForElement(By.className("paymentWrapper"));

		Utils.logger("Address selected successfully");
	}
}
