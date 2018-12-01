package helper;

public class Utils {
	public static void logger(String text) {
		System.out.println(text);
		System.out.println("========================================================");
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
}
