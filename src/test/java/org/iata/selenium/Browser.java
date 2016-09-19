package org.iata.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages the browser session. 
 */
public abstract class Browser {
	
    private static final Logger logger = LoggerFactory.getLogger("browser");

    private WebDriver driver;
    
    protected abstract WebDriver createDriver();
    
    
    private WebDriver addEventLogger(WebDriver webDriver) {
    	EventFiringWebDriver efwd = new EventFiringWebDriver(webDriver);
        efwd.register(new SeleniumEventLogger());
        return efwd;

    }
    
    private WebDriver addProfile(WebDriver webDriver) {

		webDriver.manage().window().maximize();
		return webDriver;
    }
    
    

    
    public void close() {
    	if (driver != null) {
    		driver.quit();
    		driver = null;
    	}
    }
    
    
    public void dump() {
    	if (driver != null) {
    		logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    		logger.debug(" capture browser logger ");
    		LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
    		for (LogEntry logEntry : logEntries) {
    			logger.debug( String.format(" %1$tT | %2$-10s | %3$s ", 
    					logEntry.getTimestamp(), 
    					logEntry.getLevel().getName(),
    					logEntry.getMessage() ));
    		}
    		logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    	}
    	
    }
    
    public WebDriver getDriver() {
    	if (driver == null) {
    		driver = addProfile(addEventLogger(createDriver()));
    	}
        return driver;
    }
}
