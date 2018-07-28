package com.github.lgigek.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.lgigek.scenario.Scenario;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Browser {

    private WebDriver driver;
    private static Browser instance;
    private static BrowserType browserType;
    private JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

    private static final Logger logger = LogManager.getLogger(Browser.class);

    /**
     * Creates an instance of Browser class with Chrome browser. By creating an
     * instance, a browser is started. If there is an another instance created, the
     * old one is closed and creates a new one.
     * 
     * @param chromeOptions
     *            Options for ChromeDriver. If null, create a Driver without
     *            options.
     * @param driverPath
     *            Driver file path. If null, get the default path
     *            (/src/test/resources/drivers).
     * @return The created instance.
     */
    public static Browser createChromeInstance(ChromeOptions chromeOptions, String driverPath) {
        if (instance != null)
            instance.closeWindow();
        browserType = BrowserType.CHROME;

        instance = new Browser(chromeOptions, driverPath);
        return instance;
    }

    /**
     * Creates an instance of Browser class with Firefox browser. By creating an
     * instance, a browser is started. If there is an another instance created, the
     * old one is closed and creates a new one.
     * 
     * @param firefoxOptions
     *            Options for GeckoDriver. If null, create a Driver without options.
     * @param driverPath
     *            Driver file path. If null, get the default path
     *            (/src/test/resources/drivers).
     * @return The created instance.
     */
    public static Browser createFirefoxInstance(FirefoxOptions firefoxOptions, String driverPath) {
        if (instance != null)
            instance.closeWindow();
        browserType = BrowserType.FIREFOX;

        instance = new Browser(firefoxOptions, driverPath);
        return instance;
    }

    private Browser(ChromeOptions chromeOptions, String driverPath) {
        logger.info("Starting browser {}", browserType.toString().toLowerCase());

        if (driverPath == null)
            WebDriverManager.chromedriver().setup();
        else
            System.setProperty("webdriver.chrome.driver", driverPath);

        try {
            if (chromeOptions == null) {
                driver = new ChromeDriver();
            } else {
                driver = new ChromeDriver(chromeOptions);
            }
        } catch (Exception e) {
            Scenario.fail("It was not possible to open ChromeDriver. Error: " + e);
        }

    }

    private Browser(FirefoxOptions firefoxOptions, String driverPath) {
        logger.info("Starting browser {}", browserType.toString().toLowerCase());

        if (driverPath == null)
            WebDriverManager.firefoxdriver().setup();
        else
            System.setProperty("webdriver.gecko.driver", driverPath);

        try {
            if (firefoxOptions == null) {
                driver = new FirefoxDriver();
            } else {
                driver = new FirefoxDriver(firefoxOptions);
            }
        } catch (Exception e) {
            Scenario.fail("It was not possible to open GeckoDriver. Error: " + e);
        }
    }

    /**
     * @return The instance that already have been created.
     */
    public static Browser getInstance() {
        return instance;
    }

    /**
     * @return The WebDriver object from the current instance.
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * @return The Browser type of the current instance.
     */
    public BrowserType getBrowserType() {
        return browserType;
    }

    /**
     * Maximize the browser window.
     */
    public void maximize() {
        driver.manage().window().maximize();
    }

    /**
     * Use the current tab to navigate do an URL.
     * 
     * @param url
     *            URL to be navigated.
     */
    public void navigate(String url) {
        logger.info("Navigating to url {}", url);
        driver.get(url);
    }

    /**
     * Close the current tab.
     */
    public void closeTab() {
        logger.info("Closing tab {}", driver.getTitle());
        driver.close();
    }

    /**
     * Close the browser window and instance.
     */
    public void closeWindow() {
        logger.info("Closing browser");
        driver.quit();
    }

    /**
     * Refresh the current tab.
     */
    public void refresh() {
        logger.info("Refreshing page");
        driver.navigate().refresh();
    }

    /**
     * Navigate to the previous page.
     */
    public void navigateBack() {
        logger.info("Navigating back");
        driver.navigate().back();
    }

    /**
     * Navigate to the next page.
     */
    public void navigateFoward() {
        logger.info("Navigating foward");
        driver.navigate().forward();
    }

    /**
     * Get the title from current page.
     * 
     * @return Title from current page.
     */
    public String getTabTitle() {
        logger.info("Getting tab title");
        return driver.getTitle();
    }

    /**
     * Get the URL from current tab.
     * 
     * @return URL from current tab.
     */
    public String getTabUrl() {
        logger.info("Getting tab url");
        return driver.getCurrentUrl();
    }

    /**
     * Get a cookie based on its name.
     * 
     * @param name
     *            Name of cookie.
     * @return Cookie with name according to parameter.
     */
    public Cookie getCookieByName(String name) {
        logger.info("Getting value of cookie named {}", name);
        Cookie cookie = driver.manage().getCookieNamed(name);
        if (cookie == null)
            Scenario.fail("Failed to get cookie with name " + name);
        return cookie;
    }

    /**
     * Verify if a cookie exists and return <code>true</code> if it exists.
     * 
     * @param name
     *            Name of cookie.
     * @return <code>true</code> if cookie exists; <code>false</code> if cookie does
     *         not.
     */
    public Boolean verifyIfCookieExists(String name) {
        try {
            logger.info("Verifying if cookie named {} exists", name);
            driver.manage().getCookieNamed(name).getValue();
            return true;
        } catch (Exception e) {
            logger.warn("Verification failed if cookie named {} exists", name);
            return false;
        }
    }

    /**
     * Delete all cookies from the current URL's domain.
     */
    public void clearCookies() {
        logger.info("Clearing cookies");
        driver.manage().deleteAllCookies();
    }

    /**
     * Set and LocalStorage item.
     * 
     * @param name
     *            Name of LocalStorage item
     * @param value
     *            Value of LocalStorage item.
     */
    public void setLocalstorageItem(String name, String value) {
        logger.info("Setting item {} with value {} in localstorage", name, value);
        jsExecutor.executeScript(String.format("window.localStorage.setItem('%s','%s');", name, value));
    }

    /**
     * Delete all LocalStorage items from the current URL's domain.
     */
    public void clearLocalstorage() {
        logger.info("Clearing localstorage");
        jsExecutor.executeScript(String.format("window.localStorage.clear();"));
    }

    /**
     * Focus a frame by its {@link Element}
     * 
     * @param element
     *            Frame to be focused.
     */
    public void focusFrame(Element element) {
        logger.info("Focusing frame {}", element.getElementLocator());
        driver.switchTo().frame(driver.findElement(element.getElementLocator()));
    }

    /**
     * Focus to the parent context.
     */
    public void focusParentFrame() {
        logger.info("Focusing parent frame");
        driver.switchTo().parentFrame();
    }

    /**
     * Wait until the page finish loading.
     * 
     * @param timeout
     *            Maximum time for wait.
     */
    public void waitForPageLoad(int timeout) {
        logger.info("Waiting for page to load");
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(driver -> String.valueOf(jsExecutor.executeScript("return document.readyState"))
                    .equals("complete"));
        } catch (TimeoutException e) {
            Scenario.fail("Timeout reached while waiting for page to load");
        }
    }
}
