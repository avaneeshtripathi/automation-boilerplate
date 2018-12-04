package test;

import org.testng.annotations.*;
import helper.*;

public class LaunchApp {
	@Test(priority=1)
	@Parameters("browser")
	public void LaunchBrowser(String browser) {
		Utils.logger("Launching Application at: " + Info.baseAppUrl);
		BrowserActions.initialiseWebDriver(browser);
		BrowserActions.openUrl(Info.baseAppUrl);
	}
}
