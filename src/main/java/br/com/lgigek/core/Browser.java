package br.com.lgigek.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Browser {

	private WebDriver driver;
	private static Browser instance;

	public static Browser createInstance(String browserName) {
		if (instance != null)
			instance.driver.quit();
		instance = new Browser(browserName);

		return instance;
	}

	private Browser(String browserName) {
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

	public void navigate(String url) {
		driver.get(url);
	}
	
	public void closeTab() {
		driver.close();
	}

	public void closeWindow() {
		driver.quit();
	}
}
