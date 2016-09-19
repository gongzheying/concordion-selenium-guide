package org.iata.selenium.web;

import org.iata.selenium.BrowserHandler;

@FunctionalInterface
public interface AjaxEventAction {
	
	public void execute(BrowserHandler browserHandler);
	
}