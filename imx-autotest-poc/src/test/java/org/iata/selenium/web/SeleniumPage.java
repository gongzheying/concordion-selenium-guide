package org.iata.selenium.web;

import org.iata.selenium.BrowserHandler;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SeleniumPage {


	
	protected BrowserHandler handler;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass()); 
	
	public SeleniumPage(WebDriver driver) {

		this.handler = new BrowserHandler(driver);
		

	}

	protected AjaxEvent buildAjax() {
		return new AjaxEvent(handler);
	}


	
}
