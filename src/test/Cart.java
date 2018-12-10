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
	private static By WISHLIST_ITEM = By.cssSelector(".cartItemsColumn .wishlistCtr .cartItem");
	private static By SHIPPING_PANEL_TRIGGER = By.cssSelector(".shippingLocationContainer .panelTrigger");
	private static By SHIPPING_PANEL_CITY = By.cssSelector(".optionsPanelContainer.open ul button");
	private static By MOVE_TO_WISHLIST = By.xpath("//button [contains(.,'Move to Wishlist')]");
	private static By MOVE_TO_CART = By.xpath("//button [contains(.,'Move to Cart')]");
	private static By REMOVE_FROM_CART = By.xpath("//div[contains(@class,'cartCtr')]//button[contains(.,'Remove')]");
	private static By REMOVE_FROM_WISHLIST = By.xpath("//div[contains(@class,'wishlistCtr')]//button[contains(.,'Remove')]");
	
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
	
	@Test(priority=2, dependsOnMethods={"changeDeliveryCity"})
	public static void moveToWishList() {
		Utils.logger("Moving item to wishlist");
		
		LinkedHashMap<String, Integer> initialCount = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> finalCount = new LinkedHashMap<String, Integer>();
		initialCount = getCounts();
		
		if (showLogs) {
			Utils.logger("Initial count:\nCart: " + initialCount.get("cart")
				+ "\nHeader cart icon: " + initialCount.get("headerCart")
				+ "\nWishlist: " + initialCount.get("wishlist")
				+ "\nHeader wishlist icon: " + initialCount.get("headerWishlist"));
		}
		
		BrowserActions.click(MOVE_TO_WISHLIST);
		finalCount = getCounts();
		
		if (showLogs) {
			Utils.logger("Final count:\nCart: " + finalCount.get("cart")
				+ "\nHeader cart icon: " + finalCount.get("headerCart")
				+ "\nWishlist: " + finalCount.get("wishlist")
				+ "\nHeader wishlist icon: " + finalCount.get("headerWishlist"));

			
			if (finalCount.get("cart") < initialCount.get("cart")
					&& finalCount.get("wishlist") > initialCount.get("wishlist")) {
				Utils.logger("Item successfully moved to wishlist");
			} else {
				Utils.logger("Move to wishlist failed.");
			}
		}
	}
	
	@Test(priority=3, dependsOnMethods={"moveToWishList"})
	public static void moveToCart() {
		Utils.logger("Moving item to cart");
		
		LinkedHashMap<String, Integer> initialCount = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> finalCount = new LinkedHashMap<String, Integer>();
		initialCount = getCounts();
		
		Utils.logger("Initial count:\nCart: " + initialCount.get("cart")
			+ "\nHeader cart icon: " + initialCount.get("headerCart")
			+ "\nWishlist: " + initialCount.get("wishlist")
			+ "\nHeader wishlist icon: " + initialCount.get("headerWishlist"));
		
		BrowserActions.click(MOVE_TO_CART);
		finalCount = getCounts();
		
		Utils.logger("Final count:\nCart: " + finalCount.get("cart")
			+ "\nHeader cart icon: " + finalCount.get("headerCart")
			+ "\nWishlist: " + finalCount.get("wishlist")
			+ "\nHeader wishlist icon: " + finalCount.get("headerWishlist"));
		
		if (finalCount.get("cart") > initialCount.get("cart")
				&& finalCount.get("wishlist") < initialCount.get("wishlist")) {
			Utils.logger("Item successfully moved to cart");
		} else {
			Utils.logger("Move to cart failed.");
		}
	}
	
	@Test(priority=4, dependsOnMethods={"moveToCart"})
	public static void removeFromCart() {
		Utils.logger("Removing item from cart");
		
		LinkedHashMap<String, Integer> initialCount = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> finalCount = new LinkedHashMap<String, Integer>();
		initialCount = getCounts();
		
		Utils.logger("Initial count:\nCart: " + initialCount.get("cart")
			+ "\nHeader cart icon: " + initialCount.get("headerCart"));
		
		BrowserActions.click(REMOVE_FROM_CART);
		finalCount = getCounts();
		
		Utils.logger("Final count:\nCart: " + finalCount.get("cart")
			+ "\nHeader cart icon: " + finalCount.get("headerCart"));
		
		if (finalCount.get("cart") < initialCount.get("cart")) {
			Utils.logger("Item successfully removed from cart");
		} else {
			Utils.logger("Removal from cart failed");
		}
	}
	
	@Test(priority=5, dependsOnMethods={"removeFromCart"})
    @Parameters("searchKeyword")
	public static void removeFromWishlist(String searchKeyword) {
		Utils.logger("Removing item from wishlist");

    	showLogs = false;
    	SearchProduct.navigateToProduct(searchKeyword);
    	AddToCart.goToCart();
    	moveToWishList();
		
		LinkedHashMap<String, Integer> initialCount = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> finalCount = new LinkedHashMap<String, Integer>();
		initialCount = getCounts();
		
		Utils.logger("Initial count:\nWishlist: " + initialCount.get("wishlist")
			+ "\nHeader wishlist icon: " + initialCount.get("headerWishlist"));
		
		BrowserActions.click(REMOVE_FROM_WISHLIST);
		finalCount = getCounts();
		
		Utils.logger("Final count:\nWishlist: " + finalCount.get("wishlist")
			+ "\nHeader wishlist icon: " + finalCount.get("headerWishlist"));
		
		if (finalCount.get("wishlist") < initialCount.get("wishlist")) {
			Utils.logger("Item successfully removed from wishlist");
		} else {
			Utils.logger("Removal from wishlist failed");
		}
	}
	
    @AfterTest
    public void afterTest() {
        BrowserActions.closeSession();
    }
}
