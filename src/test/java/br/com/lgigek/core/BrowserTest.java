package br.com.lgigek.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebElement;
import org.powermock.reflect.Whitebox;

import br.com.lgigek.thrall.core.Browser;
import br.com.lgigek.thrall.core.BrowserType;
import br.com.lgigek.thrall.core.Element;

public class BrowserTest {

	private Browser browser;
	private WebDriver spyWebDriver;

	@Before
	public void setUp() {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		browser = Browser.createChromeInstance(chromeOptions, null);
		spyWebDriver = spy(browser.getDriver());

		Whitebox.setInternalState(browser, WebDriver.class, spyWebDriver);
		Whitebox.setInternalState(browser, JavascriptExecutor.class, (JavascriptExecutor) spyWebDriver);
	}

	@After
	public void tearDown() {
		browser.closeWindow();
	}

	@Test
	public void getBrowserType_ReturnBrowserName() {
		assertEquals(BrowserType.CHROME, browser.getBrowserType());
	}

	@Test
	public void maximize_shouldMaximize() {
		Options mockOption = mock(Options.class);
		when(spyWebDriver.manage()).thenReturn(mockOption);

		Window mockWindow = mock(Window.class);
		when(mockOption.window()).thenReturn(mockWindow);

		browser.maximize();
		verify(mockWindow).maximize();
	}

	@Test
	public void navigate_shouldNavigate() {
		String fileLocation = "file://" + System.getProperty("user.dir") + "/src/test/resources/web-pages/example.html";
		browser.navigate(fileLocation);

		verify(spyWebDriver).get(fileLocation);
	}

	@Test
	public void closeTab_shouldCloseTab() {
		browser.closeTab();

		verify(spyWebDriver).close();
	}

	@Test
	public void closeWindow_shoudQuitBrowser() {
		browser.closeWindow();

		verify(spyWebDriver).quit();
	}

	@Test
	public void refresh_shouldRefreshPage() {
		Navigation mockNavigation = mock(Navigation.class);
		when(spyWebDriver.navigate()).thenReturn(mockNavigation);

		browser.refresh();

		verify(mockNavigation).refresh();
	}

	@Test
	public void navigateBack_shouldNavigateBack() {
		Navigation mockNavigation = mock(Navigation.class);
		when(spyWebDriver.navigate()).thenReturn(mockNavigation);

		browser.navigateBack();

		verify(mockNavigation).back();
	}

	@Test
	public void navigateFoward_shouldNavigateFoward() {
		Navigation mockNavigation = mock(Navigation.class);
		when(spyWebDriver.navigate()).thenReturn(mockNavigation);

		browser.navigateFoward();

		verify(mockNavigation).forward();
	}

	@Test
	public void getTabTitle_shouldReturnTabTitle() {
		assertTrue(browser.getTabTitle() instanceof String);
		verify(spyWebDriver).getTitle();
	}

	@Test
	public void getTabUrl_shouldReturnTabUrl() {
		assertTrue(browser.getTabUrl() instanceof String);
		verify(spyWebDriver).getCurrentUrl();
	}

	@Test
	public void getCookieByName_shouldReturnACookieIfCookieExists() {
		String cookieName = "existing-cookie";

		Options mockOption = mock(Options.class);
		when(spyWebDriver.manage()).thenReturn(mockOption);

		Cookie mockCookie = mock(Cookie.class);
		when(mockOption.getCookieNamed(cookieName)).thenReturn(mockCookie);

		browser.getCookieByName(cookieName);

		verify(mockOption).getCookieNamed(cookieName);
	}

	@Test(expected = RuntimeException.class)
	public void getCookieByName_shouldThrowRunTimeExceptionIfCookieDoesNotExist() {
		String cookieName = "non-existing-cookie";

		Options mockOption = mock(Options.class);
		when(spyWebDriver.manage()).thenReturn(mockOption);

		when(mockOption.getCookieNamed(cookieName)).thenReturn(null);

		browser.getCookieByName(cookieName);
	}

	@Test
	public void verifyIfCookieExists_shouldReturnTrueIfCookieExists() {
		String cookieName = "existing-cookie";

		Options mockOption = mock(Options.class);
		when(spyWebDriver.manage()).thenReturn(mockOption);

		Cookie mockCookie = mock(Cookie.class);
		when(mockOption.getCookieNamed(cookieName)).thenReturn(mockCookie);

		assertTrue(browser.verifyIfCookieExists(cookieName));
	}

	@Test
	public void verifyIfCookieExists_shouldReturnFalseIfCookieDoesNotExist() {
		String cookieName = "non-existing-cookie";

		Options mockOption = mock(Options.class);
		when(spyWebDriver.manage()).thenReturn(mockOption);

		when(mockOption.getCookieNamed(cookieName)).thenReturn(null);

		assertFalse(browser.verifyIfCookieExists(cookieName));
	}

	@Test
	public void clearCookies_shouldDeleteAllCookies() {
		Options mockOption = mock(Options.class);
		when(spyWebDriver.manage()).thenReturn(mockOption);

		browser.clearCookies();

		verify(mockOption).deleteAllCookies();
	}

	@Test
	public void setLocalstorageItem_shouldExecuteJsForSettingAnItemInLocalstorage() {
		// navigate is required because "Storage is disabled inside 'data:' URLs"
		String fileLocation = "file://" + System.getProperty("user.dir") + "/src/test/resources/web-pages/example.html";
		browser.navigate(fileLocation);

		browser.setLocalstorageItem("itemName", "itemValue");

		verify((JavascriptExecutor) spyWebDriver).executeScript("window.localStorage.setItem('itemName','itemValue');");
	}

	@Test
	public void clearLocalstorage_shouldExecuteJsForClearingLocalstorage() {
		// navigate is required because "Storage is disabled inside 'data:' URLs"
		String fileLocation = "file://" + System.getProperty("user.dir") + "/src/test/resources/web-pages/example.html";
		browser.navigate(fileLocation);

		browser.clearLocalstorage();

		verify((JavascriptExecutor) spyWebDriver).executeScript("window.localStorage.clear();");
	}

	@Test
	public void focusFrame_shouldFocusFrameElement() {
		By byIframe = By.cssSelector("#iframe");

		TargetLocator mockTargetLocator = mock(TargetLocator.class);
		when(spyWebDriver.switchTo()).thenReturn(mockTargetLocator);

		Element mockElement = mock(Element.class);
		when(mockElement.getElementLocator()).thenReturn(byIframe);
		doReturn(mock(WebElement.class)).when(spyWebDriver).findElement(byIframe);

		browser.focusFrame(mockElement);

		verify(mockTargetLocator).frame(spyWebDriver.findElement(byIframe));
	}

	@Test
	public void focusParentFrame_shouldFocusParentFrame() {
		TargetLocator mockTargetLocator = mock(TargetLocator.class);
		when(spyWebDriver.switchTo()).thenReturn(mockTargetLocator);

		browser.focusParentFrame();

		verify(mockTargetLocator).parentFrame();
	}

	@Test
	public void waitForPageLoad_shouldWaitUnitPageIsLoaded() {
		doReturn("complete").when(((JavascriptExecutor) spyWebDriver)).executeScript("return document.readyState");

		browser.waitForPageLoad(1);
		verify((JavascriptExecutor) spyWebDriver).executeScript("return document.readyState");
	}

	@Test(expected = RuntimeException.class)
	public void waitForPageLoad_shouldFailIfPageDoesNotLoad() {
		doReturn("incomplete").when(((JavascriptExecutor) spyWebDriver)).executeScript("return document.readyState");

		browser.waitForPageLoad(1);
	}

}