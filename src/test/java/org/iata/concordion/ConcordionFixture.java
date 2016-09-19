package org.iata.concordion;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.concordion.api.AfterSuite;
import org.concordion.api.BeforeSpecification;
import org.concordion.api.BeforeSuite;
import org.concordion.api.FailFast;
import org.concordion.api.extension.Extension;
import org.concordion.ext.ScreenshotExtension;
import org.concordion.integration.junit4.ConcordionRunner;
import org.iata.selenium.Browser;
import org.iata.selenium.SeleniumScreenshotTaker;
import org.iata.selenium.web.SeleniumPage;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@FailFast
@RunWith(ConcordionRunner.class)
public abstract class ConcordionFixture<T extends SeleniumPage> {

	private static final String BROWSER_INSTANCE = "org.iata.selenium.browser";
	private static final String BROWSER_DEFAULT = "org.iata.selenium.FirefoxBrowser";

    private static Browser browser;
    private static SeleniumScreenshotTaker screenshotTaker;


    @Extension
    private static ScreenshotExtension extension = new ScreenshotExtension();
    
    private static Map<String, Object> cachedPages = new HashMap<String, Object>();
     
	protected T page;

	
    @SuppressWarnings("unchecked")
	@BeforeSpecification
    public void initPage() {

    	try {
        	
        	Type type = getClass().getGenericSuperclass(); 
    		Type trueType = ((ParameterizedType)type).getActualTypeArguments()[0];
        	Class<T> pageClass = (Class<T>)trueType;
        	String pageClassName = pageClass.getName();
        	
        	if (cachedPages.containsKey(pageClassName)) {
        		page = (T) cachedPages.get(pageClassName);
        		System.err.println(String.format(">>>>>>found cached page %2$s at %1$s", getClass().getName(), pageClassName));
        	} else {
        		page = pageClass.getConstructor(WebDriver.class).newInstance(browser.getDriver());
        		cachedPages.put(pageClassName, page);
        	}
        	
	    	System.err.println(String.format(">>>>>>init page %2$s at %1$tT", new Date(), pageClassName));
		} catch (Exception e) {
			throw new RuntimeException("Init page fault: " + e.getMessage());
		}	

    	
    }
	
	@BeforeSuite
	public void initBrowser() {
		
		try {
			String browserInstance = System.getProperty(BROWSER_INSTANCE, BROWSER_DEFAULT);
			browser = (Browser) Class.forName(browserInstance).newInstance();

			screenshotTaker = new SeleniumScreenshotTaker(browser.getDriver());
			extension.setScreenshotTaker(screenshotTaker);
		} catch (Exception e) {
			throw new RuntimeException("Init browser fault: " + e.getMessage());
		}	

        
		
		
	}
	
	@AfterSuite
	public void closeBrowser() {
		browser.dump();
		browser.close();
	}
}
