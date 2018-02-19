package br.com.lgigek.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.lgigek.core.Browser;

public class Element {

	WebDriver driver;
	WebElement element;
	
	public Element(By locator) {
		Browser browser = Browser.getInstance();
		driver = browser.getDriver(); 
		
		element = driver.findElement(locator);
	}
	
	public WebElement getWebElement() {
		return element;
	}
	
}
