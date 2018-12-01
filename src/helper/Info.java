package helper;

public class Info {
	private static String userName = "";
	private static String email = "";
	private static String password = "";
	
	
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
	
	public static String getUserName() {
		return userName;
	}
	
	public static String getEmail() {
		return email;
	}
	
	public static String getPassword() {
		return password;
	}
}