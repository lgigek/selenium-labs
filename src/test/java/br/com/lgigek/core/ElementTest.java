package br.com.lgigek.core;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.powermock.reflect.Whitebox;

public class ElementTest {

	private WebDriver mockDriver;
	private Browser mockBrowser;

	@Before
	public void setUp() {
		mockDriver = mock(WebDriver.class);
		mockBrowser = mock(Browser.class);
		
		// Powermock is being used to return set value "mockBrowser" to attribute
		// "instance" from Browser class
		Whitebox.setInternalState(Browser.class, "instance", mockBrowser);
		
		when(Browser.getInstance().getDriver()).thenReturn(mockDriver);
	}

	@Test
	public void itShouldReturnLocator() {
		when(this.mockDriver.findElement(By.id("testMock"))).thenReturn(ElementBuilder.buildElementPresent());
		Element testMock = new Element(By.id("testMock"));
		assertTrue(testMock.getElementLocator().equals(By.id("testMock")));
	}
	
//	@Test
//	public void itShouldClickIfElementIsPresent() {
//		when(this.mockDriver.findElement(By.id("testMock"))).thenReturn(ElementBuilder.buildElementPresent());
//		Element testMock = new Element(By.id("testMock"));
//		testMock.click();
//		
//		verify(mockDriver.findElement(By.id("testMock")), times(1)).click();
//	}
}
