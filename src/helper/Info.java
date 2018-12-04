package helper;

public class Info {
	// STATIC DATA
	public static String baseEmail = "avi.test@noon.com";
	public static String basePassword = "testtesttest123";
	public static String baseFirstName = "Test";
	public static String baseLastName = "Account";
	public static String baseAppUrl = "https://www.noonstg.com/uae-en/";
	public static String accountsAppUrl = "https://account.noonstg.com/uae-en/";
	public static boolean newUser = false;

	// CARD DETAILS
	public static String ccNumber = "4242424242424242";
	public static String cardExpiryMonth = "12";
	public static String cardExpiryYear = "21";
	public static String cardCvv = "123";
	public static String searchKeyword = "munch";
	
	// USER DATA
	public static String userName = "";
	public static String email = "";
	public static String password = "";
	
	
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
}