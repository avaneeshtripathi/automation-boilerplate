package automationFramework;

import org.testng.annotations.*;

public class LoadConfigurations {
	@Test
	public void setDriverProperties() {
		System.setProperty("webdriver.chrome.driver", "/Users/atripathi/avi/automation-boilerplate/drivers/chromedriver");
		System.setProperty("webdriver.gecko.driver", "/Users/atripathi/avi/automation-boilerplate/drivers/geckodriver");	
	}
}

