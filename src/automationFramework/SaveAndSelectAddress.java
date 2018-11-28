package automationFramework;

import org.openqa.selenium.*;
import org.testng.annotations.Test;

public class SaveAndSelectAddress {
	WebDriver driver = Factory.getDriver("");

	@Test(priority=61)
	public void mapSelection() {
		Factory.logger("Awaiting google maps");
		
		WebElement searchInput = Factory.waitForElement(By.className("mapSearchBox"));
		
		Factory.logger("Map loaded successfully");
		Factory.logger("Selecting location on map");
		
		searchInput.sendKeys("downtown"); // Taking downtown region as default to select for address
		searchInput.sendKeys(Keys.ENTER);
		Factory.clickElementByOffset(By.className("mapWrapper"), 400, 200);
		Factory.waitForElementClickable(By.xpath("//button [contains(.,'Confirm location')]")).click();
		Factory.waitForElement(By.xpath("//* [contains(.,'Edit Address')]"));
		
		Factory.logger("Location selected successfully");
	}
	
	@Test(priority=62)
	public void saveAddressDetails() {
		Factory.logger("Initiating address details submission");
		
		driver.findElement(By.name("phone")).sendKeys("4242424");
		driver.findElement(By.name("firstName")).sendKeys("Avaneesh");
		driver.findElement(By.name("lastName")).sendKeys("Tripathi");
		driver.findElement(By.name("address")).sendKeys("Noon, Level 7, Boulevard Plaza, Downtown");
		driver.findElement(By.name("lastName")).sendKeys("Tripathi");
		driver.findElement(By.className("addressLabelRadioLabel")).click();
		driver.findElement(By.cssSelector(".footerButtonWrapper button")).click();
		Factory.waitForElement(By.xpath("//button [contains(.,'Continue')]")).click();
		
		Factory.logger("Address saved successfully");
		
		Factory.waitForElement(By.className("paymentWrapper"));

		Factory.logger("Address selected successfully");
	}
}
