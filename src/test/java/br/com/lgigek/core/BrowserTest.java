package br.com.lgigek.core;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.powermock.reflect.Whitebox;

public class BrowserTest {

	private Browser browser;

	private WebDriver spyWebDriver;

	@Before
	public void setUp() {
		browser = Browser.createInstance(BrowserType.CHROME, null, null);
		spyWebDriver = spy(browser.getDriver());

		Whitebox.setInternalState(browser, WebDriver.class, spyWebDriver);
	}

	@After
	public void tearDown() {
		browser.closeWindow();
	}

	@Test
	public void test() {
		browser.maximize();
		verify(spyWebDriver, atLeastOnce()).manage();
		
//		verify(spyWebDriver, atLeastOnce()).manage().window();
//		verify(spyWebDriver, atLeastOnce()).manage().window().maximize();
	}
	

}