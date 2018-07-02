package br.com.lgigek.thrall.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.lgigek.thrall.scenario.Scenario;

public class Element {

	WebDriver driver;
	private By elementLocator;
	private JavascriptExecutor jsExecutor; 

	private static final Logger logger = LogManager.getLogger(Element.class);

	/**
	 * Create a new element.
	 * 
	 * @param locator
	 *            Element locator.
	 */
	public Element(By locator) {
		elementLocator = locator;
		Browser browser = Browser.getInstance();
		driver = browser.getDriver();
		jsExecutor = (JavascriptExecutor) driver;
	}

	/**
	 * Get the element locator.
	 * 
	 * @return The element locator.
	 */
	public By getElementLocator() {
		return elementLocator;
	}

	/**
	 * On inputs or text areas, clear the value. Do nothing on other elements.
	 */
	public void clearValue() {
		logger.info("Clearing {} value", elementLocator);
		if (verifyIfElementIsPresent()) {
			driver.findElement(elementLocator).clear();
		}
	}

	/**
	 * Click on current element.
	 */
	public void click() {
		logger.info("Clicking on {}", elementLocator);
		if (verifyIfElementIsPresent()) {
			driver.findElement(elementLocator).click();
		}
	}

	/**
	 * Click on current element by using JavaScript.
	 */
	public void jsExecutorClick() {
		logger.info("Clicking with jsExecutor on {}", elementLocator);
		if (verifyIfElementIsPresent()) {
			jsExecutor.executeScript("arguments[0].click();", driver.findElement(elementLocator));
		}
	}

	/**
	 * Get the value of the given attribute of current element.
	 * 
	 * @param name
	 *            Attribute name.
	 * @return <code>null</code> if the current element does not have the attribute;
	 *         or attribute value if the current element have the attribute
	 */
	public String getAttribute(String name) {
		logger.info("Getting attribute {} of {}", name, elementLocator);
		if (verifyIfElementIsPresent()) {
			String value = driver.findElement(elementLocator).getAttribute(name);
			if (value == null) {
				logger.warn("Tried to get attribute {} from element {}, but attribute was not found", name,
						elementLocator);
				return "";
			} else
				return value;
		}
		return null;
	}

	/**
	 * Returns the text of current element.
	 * 
	 * @return The text of current element.
	 */
	public String getText() {
		logger.info("Getting text of {}", elementLocator);
		if (verifyIfElementIsPresent()) {
			return driver.findElement(elementLocator).getText();
		}
		return null;
	}

	/**
	 * Type string on the element.
	 * 
	 * @param value
	 *            String to be typed.
	 */
	public void type(String value) {
		logger.info("Typing on {}", elementLocator);
		if (verifyIfElementIsPresent()) {
			driver.findElement(elementLocator).sendKeys(value);
		}
	}

	/**
	 * Type keys on the element.
	 * 
	 * @param value
	 *            keys to be typed.
	 */
	public void type(Keys value) {
		logger.info("Typing on {}", elementLocator);
		if (verifyIfElementIsPresent()) {
			driver.findElement(elementLocator).sendKeys(value);
		}
	}

	/**
	 * Verify if the element is present.
	 * 
	 * @return <code>true</code> if element is present; <code>false</code> if
	 *         element is not present.
	 */
	public Boolean isPresent() {
		logger.info("Verifying if {} is present", elementLocator);
		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
			return true;
		} catch (Exception e) {
			logger.warn("Verification failed if element {} is present", elementLocator);
			return false;
		}
	}

	/**
	 * Verify if the element is displayed.
	 * 
	 * @return <code>true</code> if element is displayed; <code>false</code> if
	 *         element is not displayed.
	 */
	public Boolean isDisplayed() {
		logger.info("Verifying if {} is displayed", elementLocator);
		if (verifyIfElementIsPresent()) {
			if (driver.findElement(elementLocator).isDisplayed())
				return true;
			else {
				logger.warn("Verification failed if element {} is displayed", elementLocator);
				return false;
			}
		}
		return false;
	}

	/**
	 * Verify if the element is enabled.
	 * 
	 * @return <code>true</code> if element is enabled; <code>false</code> if
	 *         element is not enabled.
	 */
	public Boolean isEnabled() {
		logger.info("Verifying if {} is enabled", elementLocator);
		if (verifyIfElementIsPresent())
			if (driver.findElement(elementLocator).isEnabled())
				return true;
			else {
				logger.warn("Verification failed if element {} is enabled", elementLocator);
				return false;
			}
		return false;
	}

	/**
	 * Verify if the element is selected.
	 * 
	 * @return <code>true</code> if element is selected; <code>false</code> if
	 *         element is not selected.
	 */
	public Boolean isSelected() {
		logger.info("Verifying if {} is selected", elementLocator);
		if (verifyIfElementIsPresent())
			if (driver.findElement(elementLocator).isSelected())
				return true;
			else {
				logger.warn("Verification failed if element {} is selected", elementLocator);
				return false;
			}
		return false;
	}

	/**
	 * Submit the current form.
	 */
	public void submit() {
		logger.info("Submitting {}", elementLocator);
		if (verifyIfElementIsPresent())
			driver.findElement(elementLocator).submit();
	}

	/**
	 * Wait until the current element to be present.
	 * 
	 * @param timeout
	 *            Maximum time to wait.
	 * @return <code>true</code> if element is present before timeout.
	 *         <code>false</code> if element is not present before timeout.
	 */
	public Boolean waitForElementToBePresent(int timeout) {
		if (timeout == 0)
			timeout = 1;
		try {
			logger.info("Waiting for element {} to be present", elementLocator);
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
			return true;
		} catch (TimeoutException e) {
			logger.warn("Timeout reached while waiting for element {} to be present", elementLocator);
			return false;
		}
	}

	/**
	 * Wait until the current element to be visible.
	 * 
	 * @param timeout
	 *            Maximum time to wait.
	 * @return <code>true</code> if element is visible before timeout.
	 *         <code>false</code> if element is not visible before timeout.
	 */
	public Boolean waitForElementToBeVisible(int timeout) {
		if (timeout == 0)
			timeout = 1;
		try {
			logger.info("Waiting for element {} to be visible", elementLocator);
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
			return true;
		} catch (TimeoutException e) {
			logger.warn("Timeout reached while waiting for element {} to be visible", elementLocator);
			return false;
		}
	}

	/**
	 * Wait until the current element to be not visible.
	 * 
	 * @param timeout
	 *            Maximum time to wait.
	 * @return <code>true</code> if element is not visible before timeout.
	 *         <code>false</code> if element is visible before timeout.
	 */
	public Boolean waitForElementToBeNotVisible(int timeout) {
		if (timeout == 0)
			timeout = 1;
		try {
			logger.info("Waiting for element {} to be not visible", elementLocator);
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elementLocator));
			return true;
		} catch (TimeoutException e) {
			logger.warn("Timeout reached while waiting for element {} to be not visible", elementLocator);
			return false;
		}
	}

	/**
	 * Wait until the current element to be present.
	 * 
	 * @param timeout
	 *            Maximum time to wait.
	 * @return <code>true</code> if element is present before timeout. Fails if the
	 *         element is not present before timeout.
	 */
	private Boolean verifyIfElementIsPresent() {

		try {
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
			return true;
		} catch (Exception e) {
			Scenario.fail("Element " + elementLocator + " was not found");
			return false;
		}

	}

}
