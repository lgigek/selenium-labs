package br.com.lgigek.core;

import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import br.com.lgigek.scenario.Scenario;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Element.class)
@PowerMockIgnore("javax.management.*")
public class ElementTest {

	private WebDriver mockDriver;

	private Browser mockBrowser;

	private Element mockPresentElement;
	private By byPresentElement = By.cssSelector("#presentElement");
	private Element mockElementNotPresent;
	private By byElementNotPresent = By.cssSelector("#elementNotPresent");

	@Before
	public void setUp() throws Exception {
		mockDriver = mock(WebDriver.class);
		mockBrowser = mock(Browser.class);

		// For Browser.getInstance return a mocked Browser
		Whitebox.setInternalState(Browser.class, "instance", mockBrowser);

		when(Browser.getInstance().getDriver()).thenReturn(mockDriver);

		// Necessary stuff for mocking a present Element
		mockPresentElement = spy(new Element(byPresentElement));
		when(mockDriver.findElement(byPresentElement)).thenReturn(mock(WebElement.class));
		doReturn(true).when(mockPresentElement, "verifyIfElementIsPresent");

		mockElementNotPresent = spy(new Element(byElementNotPresent));
	}

	@Test
	public void itShouldReturnLocator() {
		assertEquals(byPresentElement, mockPresentElement.getElementLocator());
	}

	@Test
	public void itShouldClearValueIfElementIsPresent() throws Exception {
		mockPresentElement.clearValue();

		verifyPrivate(mockDriver.findElement(byPresentElement), times(1)).invoke("clear");
	}

	@Test(expected = RuntimeException.class)
	public void itShouldNotClearValueIfElementIsNotPresent() throws Exception {
		mockElementNotPresent.clearValue();

		verifyPrivate(Scenario.class).invoke("fail");
		verifyPrivate(mockDriver.findElement(byPresentElement), times(0)).invoke("clear");
	}

	@Test
	public void itShouldClickIfElementIsPresent() throws Exception {
		mockPresentElement.click();

		verifyPrivate(mockDriver.findElement(byPresentElement), times(1)).invoke("click");
	}

	@Test(expected = RuntimeException.class)
	public void itShouldNotClickIfElementIsNotPresent() throws Exception {
		mockElementNotPresent.click();

		verifyPrivate(Scenario.class).invoke("fail");
		verifyPrivate(mockDriver.findElement(byPresentElement), times(0)).invoke("click");
	}

	@Test
	@Ignore
	public void itShouldClickWiThJsExecutorIfElementIsPresent() throws Exception {
		// TODO
	}

	@Test
	@Ignore
	public void itShouldNotClickWithJsExecutorIfElementIsNotPresent() throws Exception {
		// TODO
	}

	@Test
	public void itShouldReturnAttributeValueIfElementIsPresent() throws Exception {
		when(mockDriver.findElement(byPresentElement).getAttribute("existingAttribute")).thenReturn("test value");

		assertEquals("test value", mockPresentElement.getAttribute("existingAttribute"));
		verifyPrivate(mockDriver.findElement(byPresentElement), times(1)).invoke("getAttribute", "existingAttribute");
	}
	
	@Test
	public void itShouldReturnNullIfElementIsPresentButAttributeDoesNotExist() throws Exception {
		assertEquals(null, mockPresentElement.getAttribute("attributeThatDoesNotExist"));
		verifyPrivate(mockDriver.findElement(byPresentElement), times(1)).invoke("getAttribute", "attributeThatDoesNotExist");
	}
	
	@Test(expected = RuntimeException.class)
	public void itShouldReturnNullIfElementIsNotPresent() throws Exception {
		assertEquals(null, mockElementNotPresent.getAttribute("existingAttribute"));
		verifyPrivate(Scenario.class).invoke("fail");
		verifyPrivate(mockDriver.findElement(byPresentElement), times(0)).invoke("getAttribute");
	}

}
