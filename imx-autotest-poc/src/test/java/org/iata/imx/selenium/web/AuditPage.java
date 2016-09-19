package org.iata.imx.selenium.web;

import java.util.Date;

import org.iata.selenium.BrowserHandler;
import org.iata.selenium.web.AjaxEventAction;
import org.iata.selenium.web.SeleniumPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AuditPage extends SeleniumPage {

	public AuditPage(WebDriver driver) {
		super(driver);
	}

	public String enterNewAudit() {
		
		handler.switchToDefaultContent();
		
		if (!"true".equals(handler.findElement(By.id("AUDITSINSPECTIONS:expanded")).getAttribute("value"))) {
			handler.click(By.id("AUDITSINSPECTIONS"));
		}
		if (!"true".equals(handler.findElement(By.id("Audit:expanded")).getAttribute("value"))) {
			handler.click(By.id("Audit"));
		}
		handler.click(By.xpath("//div[@id=\"Audit\"]//div[descendant::td[text()=\"New Audit\"]][@onclick]"));
		
		handler.switchToFrame(By.id("mainFrame"));
		
		handler.until(ExpectedConditions.visibilityOfElementLocated(By.id("auditForm")));
		
		return handler.findElement(By.id("pagePosition")).getText();
		
	}
	
	public String saveAudit(final String type, final String subType, String location, final String auditee, 
							final Date startDate, final Date endDate, final String leadAuditor, 
							final String scopeType, final String scope) {
		
		
		buildAjax().render(By.id("auditForm:selectAuditGroup")).addAction((browserHandler) -> {
			browserHandler.select("auditForm:auditType", type);
		}).perform();



		buildAjax().render(By.id("auditForm:txtSubType")).addAction((browserHandler) -> {
			browserHandler.selectTree("auditForm:selectAuditType2:selectAuditType2", subType);
		}).perform();

		
		handler.select("auditForm:location", location);
				
		buildAjax().render(By.id("auditForm:auditee")).addAction((browserHandler) -> {
			browserHandler.selectTree("auditForm:selectAuditee2:selectAuditee2", auditee);
		}).perform();
		

		
		buildAjax().render(By.id("auditForm:scheduledEndDate")).addAction((browserHandler) -> {
			browserHandler.calendar("auditForm:scheduledStartDate", startDate);
		}).perform();
		
		
		buildAjax().render(By.id("auditForm:scheduledStartDate")).addAction((browserHandler) -> {
			browserHandler.calendar("auditForm:scheduledEndDate", endDate);
		}).perform();
		
		
		
		buildAjax().render(By.id("auditForm:leadAuditor")).addAction((browserHandler) -> {
			browserHandler.selectGrid("auditForm:selectLeader:selectLeader:selectLeader", leadAuditor);
		}).perform();
		

		buildAjax().render(By.id("auditForm:scope")).addAction((browserHandler) -> {
			browserHandler.selectTree("auditForm:selectScope:selectScope", scopeType, scope);
		}).perform();
		
		WebElement pagePosition = buildAjax().render(By.id("pagePosition")).addAction((browserHandler) -> {
			browserHandler.click(By.xpath(("//div[@class=\"button-row\"]/input[@value=\"Save\"]")));
		}).perform();
		
		
		return pagePosition.getText();
	}
	
	public String confirmSaveAudit() {
		
		WebElement messagesDiv = buildAjax().render(By.xpath("//div[@class=\"messages\"]")).addAction((browserHandler) -> {
			browserHandler.click(By.xpath(("//a[text()=\"Save\"]")));

		}).perform();
		
		return messagesDiv.findElement(By.xpath(".//span[@class=\"rf-msgs-sum\"]")).getText();
		

	}
	
	
	public String enter() {

		handler.switchToDefaultContent();
		
		if (!"true".equals(handler.findElement(By.id("AUDITSINSPECTIONS:expanded")).getAttribute("value"))) {
			handler.click(By.id("AUDITSINSPECTIONS:hdr"));
		}
		if (!"true".equals(handler.findElement(By.id("Audit:expanded")).getAttribute("value"))) {
			handler.click(By.id("Audit:hdr"));
		}
		
		handler.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id=\"Audit:cnt\"]/div[descendant::td[text()=\"List View\"]]"))).click();
		
		handler.switchToFrame(By.id("mainFrame"));
		
		return handler.until(ExpectedConditions.visibilityOfElementLocated(By.id("messageForm"))).getAttribute("action"); 
		
	}
	
	
	public String searchAudit(String audit,final String type,String text) {
		
		if (!"true".equals(handler.findElement(By.xpath("//div[@id=\"searchPanel\"]/div/input[@type=\"hidden\"]")))){
			
			handler.click(By.xpath("//div[@id=\"searchPanel\"]//td[div[text()=\"Search Criteria\"]]"));

			handler.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchForm:ddlMainType")));
		}
		
		// select "Audit / Inspection"
		handler.select("searchForm:ddlMainType",audit);
		
		// select "Type"
		buildAjax().render(By.id("searchForm:txtAuditType")).addAction((browserHandler) -> {
				browserHandler.selectTree("searchForm:selectAuditType:selectAuditType", type);
		}).perform();
		// click "search" button
		buildAjax().render(By.id("listForm:listPanel")).addAction((browserHandler) -> {
				browserHandler.click(By.id("searchForm:btnSearchByCriteria"));
		}).perform();

		return handler.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id=\"listForm:dataTable\"]//a[text()=\""+text+"\"]"))).getText(); 
		
	}
	
	public String deleteAudit() {
	
		// enter into "View Audit" page
		buildAjax().render(By.id("viewAuditForm")).addAction((browserHandler) -> {
				
				WebElement deleteData = browserHandler.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id=\"listForm:dataTable\"]/tbody/tr[1]")));

				deleteData.findElement(By.tagName("a")).click();

		}).perform();
		
		// Cick "Delete"
		buildAjax().render(By.id("viewAuditForm:deleteAudit")).addAction((browserHandler) -> {
				
				browserHandler.click(By.xpath("//a[text()=\"Delete\"]"));

			
		}).perform();
		
		// click "Yes" button
		buildAjax().render(By.id("listForm:listPanel")).addAction((browserHandler) -> {
				
				browserHandler.click(By.xpath("//div[@id=\"viewAuditForm:deleteAudit_content\"]//input[@value=\"Yes\"]"));

		}).perform();
		
		return handler.findElement(By.id("messageForm")).getAttribute("action");
		
	}
	
}
