package test;

import java.util.List;
import org.openqa.selenium.*;
import org.testng.annotations.*;
import helper.*;

public class SearchProduct {
	@Test(priority=31)
	public void searchForProduct() {
		Utils.logger("Searchning Keyword: " + Info.searchKeyword);
		
		WebElement searchField = BrowserActions.findElement(By.id("searchBar"));
		searchField.sendKeys(Info.searchKeyword);
		searchField.sendKeys(Keys.ENTER);
		
		Utils.logger("Searched successfully");
	}
	

	@Test(priority=32)
	public void navigateToProduct() {
		Utils.logger("Awaiting product list");
		
		BrowserActions.waitForElement(By.cssSelector(".productList .productContainer .product"));
		List<WebElement> productList = BrowserActions.findElements(By.cssSelector(".productList .productContainer .product"));
		
		Utils.logger("Product list loaded successfully");
		
		int randomProductIndex = Utils.getRandomUpto(productList.size());
		WebElement productCard = productList.get(randomProductIndex);
		BrowserActions.moveClickElement(productCard);
		String productDetails = productCard.getText();
		
		Utils.logger("Navigating to randomly selected product:\n" + productDetails);
		Utils.logger("Awaiting product detail page");
		
		BrowserActions.waitForElement(By.cssSelector(".coreProductDetails .primaryDetails"));
		
		Utils.logger("Navigated successfully to: \n" + productDetails);
	}
}
