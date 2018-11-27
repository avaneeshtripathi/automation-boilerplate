package automationFramework;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

public class OpenApp {
	@Test
	@Parameters("browser")
	public void LaunchBrowser(String browser) {
		WebDriver driver;
		if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
        } else {
    		driver = new ChromeDriver();
        }
		driver.get("https://www.noonstg.com");
		driver.manage().window().maximize();
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.close();
	}
}
