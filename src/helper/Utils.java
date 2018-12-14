package helper;

import java.util.Date;

public class Utils {
	public static void logger(String text) {
		System.out.println(">> " + text);
	}
	
	public static int getRandomUpto(int max){
		return (int) (Math.random()*max);
	}
	
	public static void waitForSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			Utils.logger("Sleep failed");
		}
	}
	
	public static String getUniqueEmail(String baseEmail) {
		  int index = baseEmail.indexOf('@');
		  Date date = new Date();
		  StringBuilder userEmail = new StringBuilder(Defaults.get("user", "email"));
		  userEmail.insert(index, "+" + date.getTime());
		  return userEmail.toString();
	}
}
