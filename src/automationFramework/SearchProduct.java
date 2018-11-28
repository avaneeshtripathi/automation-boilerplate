package automationFramework;

import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;

public class SearchProduct {
	WebDriver driver = Factory.getDriver("");
	
	@Test(priority=31)
	@Parameters("searchKeyword")
	public void searchForProduct(String searchKeyword) {
		Factory.logger("Searchning Keyword: " + searchKeyword);
		
		WebElement searchField = driver.findElement(By.id("searchBar"));
		searchField.sendKeys(searchKeyword);
		searchField.sendKeys(Keys.ENTER);
		
		Factory.logger("Searched successfully");
	}
	

	@Test(priority=32)
	public void navigateToProduct() {
		Factory.logger("Awaiting product list");
		
		Factory.waitForElement(By.cssSelector(".productList .productContainer .product"));
		List<WebElement> productList = driver.findElements(By.cssSelector(".productList .productContainer .product"));
		
		Factory.logger("Product list loaded successfully");
		
		int randomProductIndex = Factory.getRandomUpto(productList.size());
		WebElement productCard = productList.get(randomProductIndex);
		String productDetails = productCard.getText();

		Actions actions = new Actions(driver);
		actions.moveToElement(productCard).click().perform();
		
		Factory.logger("Navigating to randomly selected product:\n" + productDetails);
		Factory.logger("Awaiting product detail page");
		
		Factory.waitForElement(By.cssSelector(".coreProductDetails .primaryDetails"));
		
		Factory.logger("Navigated successfully to: \n" + productDetails);
	}
}
