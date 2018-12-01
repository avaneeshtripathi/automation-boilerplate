package test;

import org.testng.annotations.*;
import helper.*;

public class LaunchApp {
	@Test(priority=1)
	@Parameters({"homeUrl", "browser"})
	public void LaunchBrowser(String homeUrl, String browser) {
		Utils.logger("Launching Application at: " + homeUrl);
		BrowserActions.initialiseWebDriver(browser);
		BrowserActions.openUrl(homeUrl);
	}
}
