package br.com.lgigek.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import br.com.lgigek.core.Browser;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = { "pretty", "html:target/cucumber/chrome-incognito" },
        glue = "br.com.lgigek.steps",
        features = "classpath:features")
public class ChromeIncognitoRunner {

	static Browser browser;
	
	@BeforeClass
	public static void setUp() {
		browser = Browser.createInstance("chrome-incognito");
	}

	@AfterClass
	public static void tearDown() {
		browser.closeWindow();
	}
	
}