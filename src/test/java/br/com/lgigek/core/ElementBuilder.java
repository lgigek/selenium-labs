package br.com.lgigek.core;

import static org.mockito.Mockito.mock;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class ElementBuilder {

	public static WebElement buildElementPresent() {
		return mock(WebElement.class);
	}
	
	public static Exception buildElementNotPresent() {
		return new NoSuchElementException(null);
	}
	
	
}
