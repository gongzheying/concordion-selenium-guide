package org.iata.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BrowserChainedHandler extends BrowserHandler {
	private WebElement parent;
	public BrowserChainedHandler(WebDriver driver, WebElement parent) {
		super(driver);
		this.parent = parent;
	}
	
	@Override
	public List<WebElement> findElements(By by) {
		List<WebElement> elements =  parent.findElements(by);
		return elements.isEmpty() ? super.findElements(by) : elements;
		
	}
	
	@Override
	public WebElement findElement(By by) {
		List<WebElement> elements = findElements(by);
		return elements.isEmpty() ? super.findElement(by) : elements.get(0);
	}
	
	
	
}