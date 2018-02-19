package br.com.lgigek.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import br.com.lgigek.core.Browser;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = { "pretty", "html:target/cucumber/chrome" },
        glue = "br.com.lgigek.steps",
        features = "classpath:features")
public class ChromeRunner {

	static Browser browser;
	
	@BeforeClass
	public static void setUp() {
		browser = Browser.createInstance("chrome");
	}

	@AfterClass
	public static void tearDown() {
		browser.closeWindow();
	}
	
}
