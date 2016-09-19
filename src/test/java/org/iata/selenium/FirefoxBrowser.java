package org.iata.selenium;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

public class FirefoxBrowser extends Browser {

	@Override
	protected WebDriver createDriver() {

		
		File consoleLog = new File("firefox_console");
		if (consoleLog.exists()) {
			consoleLog.delete();
		}

		FirefoxProfile p = new FirefoxProfile();
		p.setPreference("webdriver.log.file", consoleLog.getAbsolutePath());
		p.setPreference("webdriver.log.driver", "DEBUG");
		

		DesiredCapabilities caps = new DesiredCapabilities();
		//caps.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "unstable");
		caps.setCapability(FirefoxDriver.PROFILE, p);
		
		FirefoxDriver driver = new FirefoxDriver(caps);
		return driver;
	}

}
