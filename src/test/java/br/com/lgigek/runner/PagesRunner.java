package br.com.lgigek.runner;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import br.com.lgigek.core.Browser;
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
		ArrayList<String> browserOptions = new ArrayList<String>();
		browserOptions.add("--start-maximized");
		browser = Browser.createInstance("chrome", browserOptions, null);
	}

	@AfterClass
	public static void tearDown() {
		browser.closeWindow();
	}
	
}
