package org.iata.imx.selenium.web;

import org.iata.selenium.web.SeleniumPage;
import org.openqa.selenium.WebDriver;

public class AutotestPage extends SeleniumPage {


	public AutotestPage(WebDriver webDriver) {
		super(webDriver);

	}

	
 
	
	public void open(String url) {
		handler.open(url);
	}
	
	


	
}
