package br.com.lgigek.runner;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeOptions;

import br.com.lgigek.core.Browser;
import br.com.lgigek.core.BrowserType;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = { "pretty", "html:target/cucumber/pages" },
        glue = "br.com.lgigek.steps",
        features = "classpath:features")
public class PagesRunner {

	static Browser browser;
	
	@BeforeClass
	public static void setUp() {
		ArrayList<String> arguments = new ArrayList<String>();
		ChromeOptions chromeOptions = new ChromeOptions();
		
		arguments.add("-headless");
		arguments.add("-window-size=1300,1000");
		chromeOptions.addArguments(arguments);
		
		browser = Browser.createChromeInstance(BrowserType.CHROME, chromeOptions, null);
	}

	@AfterClass
	public static void tearDown() {
		browser.closeWindow();
	}
	
}
