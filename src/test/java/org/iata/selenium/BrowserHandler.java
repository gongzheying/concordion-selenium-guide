package org.iata.selenium;

import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class BrowserHandler {

	private static final long TIME_OUT_IN_SECONDS = 5;
	private static final long SLEEP_IN_MILLS = 100;
	
	private WebDriver driver;
	private WebDriverWait wait;
	private Actions action;

	public BrowserHandler(WebDriver driver) {
		this.driver = driver;
		this.wait = new  WebDriverWait(driver, TIME_OUT_IN_SECONDS, SLEEP_IN_MILLS);
		this.action = new Actions(driver);
	}
	
	
	public void open(String url) {
		driver.get(url);

	}
	
	public List<WebElement> findElements(By by) {
		return driver.findElements(by);
	}

	public WebElement findElement(By by) {
		List<WebElement> elements =  findElements(by);
		return elements.isEmpty() ? null : elements.get(0);
	}
	
	public void value(By by, String value) {
		WebElement input = findElement(by);
		input.clear();
		input.sendKeys(value);
	}
	
	
	public void click(By by) {
		WebElement input = findElement(by);
		input.click();
	}
	
	public void doubleClick(By by) {
		WebElement input = findElement(by);
		action.doubleClick(input);
	}
	
	public void mouseOver(By by) {
		WebElement input = findElement(by);
		action.moveToElement(input).build().perform();
	}
	
	public void calendar(String id, Date date) {

		String setDateJs = "{ " + "\n" +
			"	if (RichFaces.$(\""+id+"\")==null) { " + "\n" +
			"		return;  " + "\n" +
			"	}  " + "\n" +
			"	var dateString = RichFaces.$(\""+id+"\").getValueAsString(); " + "\n" +
			"	var datePattern = RichFaces.$(\""+id+"\").datePattern; " + "\n" +
			"	var selectedDate = RichFaces.calendarUtils.formatDate(new Date("+date.getTime()+"), datePattern); " + "\n" +
			"	if (selectedDate == dateString) {  " + "\n" +
			"		return;  " + "\n" +
			"	}  " + "\n" +
			//"	RichFaces.$(\""+id+"\").showPopup();  " + "\n" +
			"	RichFaces.$(\""+id+"\").setValue(selectedDate);  " + "\n" +
			//"	RichFaces.$(\""+id+"\").hidePopup();  " + "\n" +
			"	return selectedDate;  " + "\n" +
			"} ";
		
		String selectedDate = (String) executeScript(setDateJs);
		until(ExpectedConditions.textToBePresentInElementValue(By.id(id+"InputDate"), selectedDate));
	}
	
	public void selectTree(String id, String treeType, String value) {
		

		//popup dialog
		click(By.id(id+"ImxSelectTree"));
		until(ExpectedConditions.visibilityOfElementLocated(By.id(id+"popup_content")));

		//select type
		if (treeType != null) {
			WebElement typeSelect = findElement(By.xpath("//div[@id=\""+id+"popup_content\"]//table/tbody/tr[td[1]/label[text()=\"Type\"]]/td[2]/div"));
			String typeSelectId = typeSelect.getAttribute("id");
			
			WebElement typeSelectInput = findElement(By.id(typeSelectId+"Input"));
			String  typeDefaultValue = typeSelectInput.getAttribute("value");
			if (!typeDefaultValue.equals(treeType)) {
				select(typeSelectId, treeType);
				refreshed(By.id(id+"tree"));
			}
		}
		
		
		//filter by key
		value(By.id(id+"keywordtxtKwd"), value);
		click(By.id(id+"keywordbtn"));
		
		
		//expand tree nodes
		WebElement treeRootDiv = refreshed(By.id(id+"tree"));
		String treeRootId = treeRootDiv.getAttribute("id");
		WebElement parentNodeDiv = findElement(By.xpath("//div[@id=\""+id+"tree\"]//label[text()=\""+value+"\"]/../../../../.."));
		String parentNodeId = parentNodeDiv.getAttribute("id");
		Stack<WebElement> parentNodeStack = new Stack<WebElement>();

		while (!parentNodeId.equals(treeRootId)) {
			parentNodeStack.push(parentNodeDiv);
			parentNodeDiv = parentNodeDiv.findElement(By.xpath(".."));
			parentNodeId = parentNodeDiv.getAttribute("id");
		} 

		
		while (!parentNodeStack.isEmpty()) {
			WebElement treeNodeDiv = parentNodeStack.pop();
			String treeNodeClass = treeNodeDiv.getAttribute("class");
			if (!treeNodeClass.contains("rf-tr-nd-exp")) {
				WebElement treeNodeECButton = treeNodeDiv.findElement(By.xpath("div[1]/span[contains(@class,\"rf-trn-hnd\")]"));
				treeNodeECButton.click();
			}
		}
				
		//click selected tree node		
		click(By.xpath("//div[@id=\""+id+"tree\"]//label[text()=\""+value+"\"]"));		
				
		//click ok
		click(By.id(id+"buttonOk"));
		
		
	}
	
	public void selectTree(String id, String value) {
		selectTree(id,null,value);
	}
	
	public void selectGrid(String id, String value) {
		
		click(By.id(id+"ImxSelectGrid"));
		until(ExpectedConditions.visibilityOfElementLocated(By.id(id+"popup_content")));
		
		value(By.id(id+"keywordtxtKwd"), value);
		click(By.id(id+"keywordbtn"));
		refreshed(By.id(id+"tablePanel"));

		click(By.xpath(("//tbody[@id=\""+id+"table:tb\"]/tr[td[normalize-space(text())=\""+value+"\"]]")));

		String buttonValue = findElement(By.id(id+"buttonOk")).getAttribute("value");
		if ("OK".equals(buttonValue)) {
			click(By.id(id+"buttonOk"));
			 
		} else { //add
			click(By.id(id+"buttonOk"));
			refreshed(By.id(id+"tablePanel"));
			click(By.id(id+"popupImg"));
		}
		

				
	}
	
	
	public void select(String id, String value) {
		
		WebElement input = findElement(By.id(id+"Input"));
		String defaultValue = input.getAttribute("value");
		
		if(value.equals(defaultValue.trim())){
			return;
		}

		String isReadOnly = input.getAttribute("readonly");
		if (isReadOnly != null){
			click(By.id(id+"Button"));
		} else {
			value(By.id(id+"Input"),value); 
		}
		
		
		WebElement selectItems = findElement(By.id(id+"Items"));
		wait.until(ExpectedConditions.visibilityOf(selectItems));
		selectItems.findElement(By.xpath(".//div[normalize-space(text())=\""+value+"\"]")).click();
	}
	
	public void pickList(String id, String value) {
		
		
		WebElement selectItems = findElement(By.id(id+"SourceItems"));
		List<WebElement> options = selectItems.findElements(By.tagName("div"));
		for (WebElement option : options) {
			if (option.getText().trim().equals(value)) {
				option.click();
				break;
			}
		}
		
		WebElement addButton = findElement(By.name(id+"add"));
		addButton.click();
		
	}


	
	public <V> V until(Function<? super WebDriver, V> isTrue) {
		return wait.until(isTrue);
	}
	

	
	/**
	 * @param by
	 * @return
	 * @deprecated use {@link org.iata.selenium.web.AjaxEvent }
	 */
	public WebElement refreshed(By by) {
		until(ExpectedConditions.stalenessOf(findElement(by)));
		return until(ExpectedConditions.presenceOfElementLocated(by));

	}
	
	public Object executeScript(String script, Object... args) {
		return ((JavascriptExecutor)driver).executeScript(script, args);
	}
	
	public void switchToDefaultContent() {
		
		driver.switchTo().defaultContent();
	}
	
	public void switchToFrame(By by) {
		until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
	}
	


	
}
