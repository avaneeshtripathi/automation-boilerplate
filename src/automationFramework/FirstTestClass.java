package automationFramework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class FirstTestClass {
  @Test
  public void f() {
	  System.setProperty("webdriver.chrome.driver", "/Users/atripathi/avi/automation-boilerplate/drivers/chromedriver");
	  WebDriver driver = new ChromeDriver();
	  driver.get("https://www.google.com");
  }
}
