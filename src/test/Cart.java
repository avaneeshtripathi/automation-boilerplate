package test;

import helper.*;

import org.testng.annotations.*;

import java.util.LinkedHashMap;

import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;

public class Cart {
	private static By HEADER_CART_ICON = By.cssSelector("header.siteHeader .cartIconContainer div");
	private static By HEADER_WISHLIST_ICON = By.cssSelector("header.siteHeader .counterIcon span.counter");
	private static By CART_ITEM = By.cssSelector(".cartItemsColumn .cartCtr .cartItem");
	private static By CART_ITEM_HEADING = By.cssSelector(".cartItemsColumn .cartCtr .productName");
	private static By WISHLIST_ITEM = By.cssSelector(".cartItemsColumn .wishlistCtr .cartItem");
	private static By WISHLIST_ITEM_HEADING = By.cssSelector(".cartItemsColumn .wishlistCtr .productName");
	private static By SHIPPING_PANEL_TRIGGER = By.cssSelector(".shippingLocationContainer .panelTrigger");
	private static By SHIPPING_PANEL_CITY = By.cssSelector(".optionsPanelContainer.open ul button");
	private static By MOVE_TO_WISHLIST = By.xpath("//button [contains(.,'Move to Wishlist')]");
	private static By MOVE_TO_CART = By.xpath("//button [contains(.,'Move to Cart')]");
	private static By REMOVE_FROM_CART = By.xpath("//div[contains(@class,'cartCtr')]//button[contains(.,'Remove')]");
	private static By REMOVE_FROM_WISHLIST = By.xpath("//div[contains(@class,'wishlistCtr')]//button[contains(.,'Remove')]");
	private static By START_SHOPPING_BUTTON = By.xpath("//button[contains(.,'Start Shopping')]");
	
	private static boolean showLogs = true;
	
	@BeforeTest
    @Parameters({"authentication", "searchKeyword"})
    public void beforeTest(String authentication, String searchKeyword) {
        BrowserActions.launchApp();
        if (authentication.equalsIgnoreCase("login")) {
            Login.authenticate();
        } else {
        	Signup.authenticate();
        }
    	SearchProduct.navigateToProduct(searchKeyword);
    	AddToCart.goToCart();
    }
	
	private static LinkedHashMap<String, Integer> getCounts() {
		LinkedHashMap<String, Integer> count = new LinkedHashMap<String, Integer>();
		count.put("cart", BrowserActions.getListItemCount(CART_ITEM));
		count.put("wishlist", BrowserActions.getListItemCount(WISHLIST_ITEM));
		try {
			count.put("headerCart", Integer.parseInt(BrowserActions.getTextContent(HEADER_CART_ICON)));
		} catch (Exception e) {
			count.put("headerCart", 0);
		}
		try {
			count.put("headerWishlist", Integer.parseInt(BrowserActions.getTextContent(HEADER_WISHLIST_ICON)));
		} catch (Exception e) {
			count.put("headerWishlist", 0);
		}
		return count;
	}
	
	@Test(priority=1)
	public static void changeDeliveryCity() {
		Utils.waitForSeconds(1);
		String initialCity = BrowserActions.getTextContent(SHIPPING_PANEL_TRIGGER).replace("Deliver to: ", "");
		BrowserActions.click(SHIPPING_PANEL_TRIGGER);
		BrowserActions.waitForElement(SHIPPING_PANEL_CITY);
		Utils.waitForSeconds(1);
		String selectedCity = BrowserActions.clickRandomInList(SHIPPING_PANEL_CITY).getText();
		
		Utils.logger("Attempting to change delivery city\nCurrent: " + initialCity + "\nSelected: " + selectedCity);
		Utils.waitForSeconds(2);

		String finalCity = BrowserActions.getTextContent(SHIPPING_PANEL_TRIGGER).replace("Deliver to: ", "");
		
		if (!finalCity.equalsIgnoreCase(initialCity) && finalCity.equalsIgnoreCase(selectedCity)) {
			Utils.logger("Delivery city changed to " + finalCity + " successfully");
		} else {
			Utils.logger("Failed to change delivery city");
		}
	}
	
	@Test(priority=2)
	public static void moveToWishList() {
		Utils.logger("Moving item to wishlist");
		
		String cartItemName = BrowserActions.getTextContent(CART_ITEM_HEADING);
		if (showLogs) {
			Utils.logger("Item in cart: " + cartItemName);
		}
		
		BrowserActions.click(MOVE_TO_WISHLIST);
		BrowserActions.waitForElement(WISHLIST_ITEM_HEADING);
		String wishlistItemName = BrowserActions.getTextContent(WISHLIST_ITEM_HEADING);
		
		if (showLogs) {
			Utils.logger("Item in wishlist: " + wishlistItemName);
			if (wishlistItemName.equalsIgnoreCase(cartItemName)) {
				Utils.logger("Item successfully moved to wishlist");
			} else {
				Utils.logger("Move to wishlist failed");
			}
		}
	}
	
	@Test(priority=3)
	public static void moveToCart() {
		Utils.logger("Moving item to cart");

		String wishlistItemName = BrowserActions.getTextContent(WISHLIST_ITEM_HEADING);
		Utils.logger("Item in wishlist: " + wishlistItemName);
		
		BrowserActions.click(MOVE_TO_CART);
		BrowserActions.waitForElement(CART_ITEM_HEADING);
		String cartItemName = BrowserActions.getTextContent(CART_ITEM_HEADING);

		Utils.logger("Item in cart: " + cartItemName);
		
		if (cartItemName.equalsIgnoreCase(wishlistItemName)) {
			Utils.logger("Item successfully moved to cart");
		} else {
			Utils.logger("Move to cart failed");
		}
	}
	
	@Test(priority=4)
	public static void removeFromCart() {
		Utils.logger("Removing item from cart");

		Utils.logger("Item in cart: " + BrowserActions.getTextContent(CART_ITEM_HEADING));
		
		BrowserActions.click(REMOVE_FROM_CART);
		BrowserActions.waitForElement(START_SHOPPING_BUTTON);
		
		Utils.logger("Item successfully removed from cart");
	}
	
	@Test(priority=5)
    @Parameters("searchKeyword")
	public static void removeFromWishlist(String searchKeyword) {
		Utils.logger("Removing item from wishlist");

    	showLogs = false;
    	SearchProduct.navigateToProduct(searchKeyword);
    	AddToCart.goToCart();
    	moveToWishList();

		BrowserActions.waitForElement(REMOVE_FROM_WISHLIST);

		Utils.logger("Item in wishlist: " + BrowserActions.getTextContent(WISHLIST_ITEM_HEADING));
		
		BrowserActions.click(REMOVE_FROM_WISHLIST);
		BrowserActions.waitForElement(START_SHOPPING_BUTTON);
		
		Utils.logger("Item successfully removed from wishlist");
	}
	
    @AfterTest
    public void afterTest() {
        BrowserActions.closeSession();
    }
}
