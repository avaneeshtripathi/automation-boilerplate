package automationFramework;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

public class LaunchApp {
	@Test(priority=1)
	@Parameters("homeUrl")
	public void LaunchBrowser(String homeUrl) {
		WebDriver driver = Factory.getDriver("");
		Factory.logger("Launching Application at: " + homeUrl);
		driver.get(homeUrl);
	}
}
