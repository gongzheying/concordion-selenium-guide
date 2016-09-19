package org.iata.imx.selenium.web;

import java.util.List;

import org.iata.selenium.web.SeleniumPage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends SeleniumPage {


	public LoginPage(WebDriver webDriver) {
		super(webDriver);

	}

	
 
	
	
	public String logout() {
		
		handler.switchToDefaultContent();
		
		//click logout link
		handler.click(By.xpath("//img[@title=\"Logout\"]"));
		
		
		//click yes button in confirm dialog
		By yesLnkLocated = By.xpath("//div[@id=\"lan:logoutPanel_content\"]//a[text()=\"Yes\"]");
		handler.until(ExpectedConditions.visibilityOfElementLocated(yesLnkLocated)).click();

		

		//expect the login form to appear
		handler.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginForm")));

		return handler.findElement(By.xpath("//div[@id=\"menuTop\"]//div[@class=\"label1\"]")).getText();
	}
	

	
	
	public String login(String username, String password) {
		
		
		handler.value(By.id("username"), username);
		handler.value(By.id("password"), password);
		handler.click(By.id("loginButton"));
		

		
		handler.until(ExpectedConditions.stalenessOf(handler.findElement(By.id("loginForm"))));
		
		return handler.findElement(By.xpath("//div[@id=\"menuTop\"]//div[@class=\"label1\"]")).getText();
	}
    
	


	
}
