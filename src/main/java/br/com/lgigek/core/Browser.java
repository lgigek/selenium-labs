package br.com.lgigek.core;

import java.util.ArrayList;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Browser {

	private WebDriver driver;
	private static Browser instance;
	private String browserName;
	private JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

	private static final Logger logger = LogManager.getLogger(Browser.class);

	public static Browser createInstance(BrowserType browserType, ArrayList<String> browserOptions, String driverPath) {
		if (instance != null)
			instance.closeWindow();
		instance = new Browser(browserType, browserOptions, driverPath);

		return instance;
	}

	private Browser(BrowserType browserType, ArrayList<String> browserOptions, String driverPath) {
		this.browserName = browserType.toString().toLowerCase();
		logger.info("Starting browser {}", browserName);
		if (browserType.equals(BrowserType.CHROME)) {

			if (driverPath == null)
				driverPath = getDefaultChromeDriver();

			System.setProperty("webdriver.chrome.driver", driverPath);
			ChromeOptions chromeOptions = new ChromeOptions();

			browserOptions.stream().forEach(option -> {
				chromeOptions.addArguments(option);
			});

			driver = new ChromeDriver(chromeOptions);
		} else {
			throw new NotImplementedException("Implemented only for chromedriver");
		}
	}

	public static Browser getInstance() {
		return instance;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void maximize() {
		driver.manage().window().maximize();
	}

	public void navigate(String url) {
		logger.info("Navigating to url {}", url);
		driver.get(url);
	}

	public void closeTab() {
		logger.info("Closing tab {}", driver.getTitle());
		driver.close();
	}

	public void closeWindow() {
		logger.info("Closing browser");
		driver.quit();
	}

	public void refresh() {
		logger.info("Refreshing page");
		driver.navigate().refresh();
	}

	public void navigateBack() {
		logger.info("Navigating back");
		driver.navigate().back();
	}

	public void navigateFoward() {
		logger.info("Navigating foward");
		driver.navigate().forward();
	}

	public String getTabTitle() {
		logger.info("Getting tab title");
		return driver.getTitle();
	}

	public String getTabUrl() {
		logger.info("Getting tab url");
		return driver.getCurrentUrl();
	}

	public Cookie getCookieByName(String name) {
		logger.info("Getting value of cookie named {}", name);
		return driver.manage().getCookieNamed(name);
	}

	public Boolean verifyIfCookieExists(String name) {
		try {
			logger.info("Verifying if cookie named {} exists", name);
			driver.manage().getCookieNamed(name).getValue().toString();
			return true;
		} catch (Exception e) {
			logger.warn("Verification failed if cookie named {} exists", name);
			return false;
		}
	}

	public void clearCookies() {
		logger.info("Clearing cookies");
		driver.manage().deleteAllCookies();
	}

	public void setLocalstorageItem(String name, String value) {
		logger.info("Setting item {} with value {} in localstorage", name, value);
		jsExecutor.executeScript(String.format("window.localStorage.setItem('%s','%s');", name, value));
	}

	public void clearLocalstorage() {
		logger.info("Clearing localstorage");
		jsExecutor.executeScript(String.format("window.localStorage.clear();"));
	}

	public void focusFrame(Element element) {
		logger.info("Focusing frame {}", element.getElementLocator());
		driver.switchTo().frame(driver.findElement(element.getElementLocator()));
	}

	public void focusParentFrame() {
		logger.info("Focusing parent frame");
		driver.switchTo().parentFrame();
	}

	public void waitForPageLoad(int timeout) {
		logger.info("Waiting for page to load");
		Wait<WebDriver> wait = new WebDriverWait(driver, timeout);
		wait.until(driver -> String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
				.equals("complete"));
	}

	private String getDefaultChromeDriver() {
		String chromeDriverPath = null;
		if (SystemUtils.IS_OS_WINDOWS)
			chromeDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/windows/chromedriver.exe";
		else
			chromeDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/linux/chromedriver";

		return chromeDriverPath;
	}
}
