package helper;

import java.util.LinkedHashMap;

public class Defaults {
	public static LinkedHashMap<String, String> User;
	public static LinkedHashMap<String, String> Card;
	public static LinkedHashMap<String, String> App;
	public static boolean preSavedAddresses = false;
	
	public static void set(String name, LinkedHashMap<String, String> data) {
		switch(name) {
			case "user":
				User = data;
				break;
			case "card":
				Card = data;
				break;
			case "app":
				App = data;
				break;
		}
	}
	
	public static String get(String dataSet, String name) {
		switch(dataSet) {
			case "user": return User.get(name);
			case "card": return Card.get(name);
			case "app": return App.get(name);
		}
		return "";
	}
	
	public static void setPreSavedAddresses(boolean value) {
		preSavedAddresses = value;
	}
}