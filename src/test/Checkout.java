package test;

import org.openqa.selenium.*;
import org.testng.annotations.*;

import helper.*;

public class Checkout {
	private static By CART_CHECKOUT_BUTTON = By.xpath("//button [contains(.,'Checkout Now')]");
	private static By MAP_WRAPPER = By.cssSelector(".mapWrapper");
	private static By ADDRESS_CARD_WRAPPER = By.cssSelector(".addNewAddressCtr .addNewAddress");
	
	private static By ADD_ADDRESS_CARD = By.cssSelector(".addNewAddressCtr .addNewAddress");
	private static By PAYMENTS_WRAPPER = By.className("paymentWrapper");
	private static By MAP_SEARCH_FIELD = By.className("mapSearchBox");
	private static By CONFIRM_LOCATION_BUTTON = By.xpath("//button [contains(.,'Confirm location')]");
	private static By ADDRESS_LIST_HEADING = By.xpath("//* [contains(.,'Edit Address')]");
	private static By PHONE_FIELD = By.name("phone");
	private static By FIRSTNAME_FIELD = By.name("firstName");
	private static By LASTNAME_FIELD = By.name("lastName");
	private static By ADDRESS_FIELD = By.name("address");
	private static By ADDRESS_TYPE_RADIO = By.className("addressLabelRadioLabel");
	private static By SUBMIT_BUTTON = By.cssSelector(".footerButtonWrapper button");
	private static By CONTINUE_BUTTON = By.xpath("//button [contains(.,'Continue')]");
	
	private static By PRE_SAVED_CARD = By.cssSelector(".paymentMethodWrapper.prepay .cardRowInner input");
	private static By CC_NUMBER = By.id("ccNumber");
	private static By CC_EXPIRY_MONTH = By.name("cardExpiryMonth");
	private static By CC_EXPIRY_YEAR = By.name("cardExpiryYear");
	private static By CC_CVV = By.cssSelector(".inputWrapper.withTooltip input.neutral");
	private static By PLACE_ORDER_BUTTON = By.cssSelector(".placeOrderCtaContainer button");
	private static By SUCCESS_ORDER_WRAPPER = By.cssSelector(".orderItemsWrapper .itemContainer");
	private static By COD_BUTTON = By.cssSelector(".codOption label.buttonPadding");
	private static By SUCCESS_ORDER_NR = By.cssSelector(".successPage .coreOrderDetails");
	private static By ACCOUNT_ORDER_NR = By.cssSelector(".orderInfoWrapper .boxHeader h2");
	
	private static String orderNr = "";

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
    
	@Test(priority=1)
	public void checkoutWithCart() {
		Utils.logger("Navigating for address selection");
		
		BrowserActions.clickWithAction(BrowserActions.waitForElement(CART_CHECKOUT_BUTTON));
		BrowserActions.waitForOneOfElements(MAP_WRAPPER, ADDRESS_CARD_WRAPPER);
		Defaults.setPreSavedAddresses(BrowserActions.checkElementExists(ADDRESS_CARD_WRAPPER));
		
		Utils.logger("Navigated to address selection successfully");
	}
    
	@Test(priority=2)
    @Parameters({"addAddress", "mapRegion"})
	public void handleAddressSelection(String addAddress, String mapRegion) {
		Utils.waitForSeconds(1);
		
		if (Defaults.preSavedAddresses) {
			Utils.logger("Previously saved addresses available");
			if (addAddress.equalsIgnoreCase("yes")) {
				Utils.logger("Initiating to add a new address");
				
				BrowserActions.click(ADD_ADDRESS_CARD);
				mapSelection(mapRegion);
				saveAddressDetails();
			} else {
				Utils.logger("Selecting previously saved address");
				
				BrowserActions.waitForElementClickable(CONTINUE_BUTTON);
				BrowserActions.click(CONTINUE_BUTTON);
				BrowserActions.waitForElement(PAYMENTS_WRAPPER);
				
				Utils.logger("Address selected successfully");
			}
		} else {
			Utils.logger("No previously saved addresses found");

			mapSelection(mapRegion);
			saveAddressDetails();
		}
	}
	
	@Test(priority=3)
    @Parameters("payment")
	public void handlePayment (String payment) {
		if(payment.equalsIgnoreCase("card")) {
			payByCard();
		} else {
			payByCash();
		}

		Utils.waitForSeconds(2);
		BrowserActions.waitForElementClickable(PLACE_ORDER_BUTTON);
		BrowserActions.click(PLACE_ORDER_BUTTON);
		BrowserActions.waitForElement(SUCCESS_ORDER_WRAPPER);
		orderNr = BrowserActions.getTextContent(SUCCESS_ORDER_NR).replace("Order ", "");
		
		Utils.logger("Order: " + orderNr + " placed successfully.");
	}
	
	@Test(priority=4)
	@Parameters("browser")
	public void verifyOrder(String browser) {
		Utils.logger("Navigating to orders for verification");
		
		BrowserActions.openUrl(browser, Defaults.get("app", "accountAppUrl") + "orders/" + orderNr);
		BrowserActions.waitForElement(ACCOUNT_ORDER_NR);
		String verificationOrderNr = BrowserActions.getTextContent(ACCOUNT_ORDER_NR).replace("Order ", "");
				
		if (orderNr.equalsIgnoreCase(verificationOrderNr)) {
			Utils.logger("Order: " + orderNr + " verified successfully");
		} else {
			Utils.logger("Order: " + orderNr + " can't be verified");
		}
	}

	private static void mapSelection(String region) {
		Utils.logger("Awaiting google maps");
		
		BrowserActions.waitForElement(MAP_SEARCH_FIELD);
		
		Utils.logger("Map loaded successfully");
		Utils.logger("Selecting location on map");
		
		BrowserActions.input(MAP_SEARCH_FIELD, region);
		BrowserActions.submit(MAP_SEARCH_FIELD);
		BrowserActions.clickByOffset(MAP_WRAPPER, 400, 200);
		BrowserActions.waitForElementClickable(CONFIRM_LOCATION_BUTTON);
		BrowserActions.click(CONFIRM_LOCATION_BUTTON);
		BrowserActions.waitForElement(ADDRESS_LIST_HEADING);
		
		Utils.logger("Location selected successfully");
	}

	private static void saveAddressDetails() {
		Utils.logger("Initiating address details submission");
		
		BrowserActions.input(PHONE_FIELD, Defaults.get("user", "phone"));
		BrowserActions.input(FIRSTNAME_FIELD, Defaults.get("user", "firstName"));
		BrowserActions.input(LASTNAME_FIELD, Defaults.get("user", "lastName"));
		BrowserActions.input(ADDRESS_FIELD, Defaults.get("user", "address"));
		BrowserActions.click(ADDRESS_TYPE_RADIO);
		BrowserActions.click(SUBMIT_BUTTON);
		BrowserActions.waitForElement(CONTINUE_BUTTON);
		BrowserActions.click(CONTINUE_BUTTON);
		
		Utils.logger("Address saved successfully");

		BrowserActions.waitForElement(ADDRESS_LIST_HEADING);

		Utils.logger("Address selected successfully");
	}
	
	private static void payByCard() {
		Utils.logger("Initiating payment by card");
		
		BrowserActions.waitForOneOfElements(CC_NUMBER, PRE_SAVED_CARD);
		
		if (BrowserActions.checkElementExists(PRE_SAVED_CARD)) {
			Utils.logger("Selecting pre-saved card");
			
			BrowserActions.click(PRE_SAVED_CARD);
		} else {
			Utils.logger("Adding new card details");
			
			BrowserActions.input(CC_NUMBER, Defaults.get("card", "ccNumber"));
			BrowserActions.input(CC_EXPIRY_MONTH, Defaults.get("card", "ccExpiryMonth"));
			BrowserActions.input(CC_EXPIRY_YEAR, Defaults.get("card", "ccExpiryYear"));
			BrowserActions.input(CC_CVV, Defaults.get("card", "ccCvv"));
			
			Utils.logger("Submitting card details");
		}
	}
	
	private static void payByCash() {
		Utils.logger("Initiating payment by cash");

		WebElement codButton = BrowserActions.waitForElement(COD_BUTTON);
		Utils.waitForSeconds(2);
		BrowserActions.clickWithAction(codButton);
		
		Utils.logger("Placing order in cash");
	}
	
    @AfterTest
    public void afterTest() {
        BrowserActions.closeSession();
    }
}
