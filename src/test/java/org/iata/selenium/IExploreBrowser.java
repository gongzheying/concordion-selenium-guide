package org.iata.selenium;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class IExploreBrowser extends Browser {


	@Override
	protected WebDriver createDriver() {

    	File consoleLog = new File("ie_console");
    	if (consoleLog.exists()) {
    		consoleLog.delete();
    	}

    	DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
    	dc.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, true);
    	dc.setCapability(InternetExplorerDriver.IE_SWITCHES, "-private");
    	dc.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
    	dc.setCapability(InternetExplorerDriver.LOG_LEVEL, "TRACE");
    	dc.setCapability(InternetExplorerDriver.LOG_FILE, consoleLog.getAbsolutePath());
    	InternetExplorerDriver driver = new InternetExplorerDriver(dc);
    	return driver;
	}
	
}
