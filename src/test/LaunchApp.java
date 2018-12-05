package test;

import org.testng.annotations.*;
import helper.*;

public class LaunchApp {
	@Test(priority=1)
	public void LaunchBrowser() {
		Utils.logger("Launching Application at: " + Info.baseAppUrl);
		BrowserActions.openUrl(Info.baseAppUrl);
	}
}
