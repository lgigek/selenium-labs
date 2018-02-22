package br.com.lgigek.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Browser {

	private WebDriver driver;
	private static Browser instance;
	private String browserName; 
	private JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
	
	private static final Logger logger = LogManager.getLogger(Browser.class);
	
	public static Browser createInstance(String browserName) {
		if (instance != null)
			instance.closeWindow();
		instance = new Browser(browserName);

		return instance;
	}

	private Browser(String browserName) {
		this.browserName = browserName; 
		logger.info("Starting browser {}", browserName);
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver");
		ChromeOptions chromeOptions = new ChromeOptions();
		if (browserName.toLowerCase().equals("chrome-incognito")) {
			chromeOptions.addArguments("-incognito");
		}
		driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
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
		return driver.getTitle();
	}

	public String getTabUrl() {
		return driver.getCurrentUrl();
	}
	
	public Cookie getCookieByName(String name) {
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

	public void clearingLocalstorage() {
		logger.info("Clearing localstorage");
		jsExecutor.executeScript(String.format("window.localStorage.clear();"));
	}
	
	public void focusFrame(By by) {
		logger.info("Focusing frame {}", by.toString());
		driver.switchTo().frame(driver.findElement(by));
	}
	
	public void focusParentFrame() {
		logger.info("Focusing parent frame");
		driver.switchTo().parentFrame();
	}
}
