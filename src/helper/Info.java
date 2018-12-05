package helper;

public class Info {
	// STATIC DATA
	public static String browser = "chrome";
	public static String baseEmail = "avi.test@noon.com";
	public static String basePassword = "testtesttest123";
	public static String baseFirstName = "Test";
	public static String baseLastName = "Account";
	public static String searchKeyword = "munch";
	public static String baseAppUrl = "https://www.noonstg.com/uae-en/";
	public static String accountsAppUrl = "https://account.noonstg.com/uae-en/";

	// CARD DETAILS
	public static String ccNumber = "4242424242424242";
	public static String cardExpiryMonth = "12";
	public static String cardExpiryYear = "21";
	public static String cardCvv = "123";
	
	// OTHER DETAILS
	public static String mapRegion = "downtown";
	public static String phoneNumber = "4242424";
	public static String address = "Do Not, Deliver To This, Address";
	
	// USER DATA
	public static String userName = "";
	public static String email = "";
	public static String password = "";
	public static boolean preSavedAddresses = false;

	// FLOW OF CONTROL
	public static boolean registerUser = false;
	public static boolean addNewAddress = true;
	public static boolean payByCard = false;
	
	
	// GETTER AND SETTER METHODS FOR LOGGED IN USER
	public static void setUserName(String text) {
		userName = text;
		Utils.logger("Username <" + userName + "> set to factory.");
	}
	
	public static void setEmail(String text) {
		email = text;
		Utils.logger("Email <" + email + "> set to factory.");
	}
	
	public static void setPassword(String text) {
		password = text;
		Utils.logger("Password <" + password + "> set to factory.");
	}
	
	public static void setPreSavedAddresses(boolean value) {
		preSavedAddresses = value;
	}
}