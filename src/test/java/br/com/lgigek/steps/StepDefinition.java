package br.com.lgigek.steps;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;

import br.com.lgigek.core.Browser;
import br.com.lgigek.core.Element;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinition {

	Browser browser = Browser.getInstance();

	@When("^The user navigates to Google home page$")
	public void accessGoogleHomePage() {
		browser.navigate("http://www.google.com");
	}

	@Then("^Google home page is displayed$")
	public void verifyIfGoogleHomePageIsDisplayed() {
		assertTrue(new Element(By.name("q")).isDisplayed());
	}

	@When("^The user navigates to Facebook home page$")
	public void accessFacebookHomePage() {
		browser.navigate("http://www.facebook.com");
	}

	@Then("^Facebook home page is displayed$")
	public void verifyIfFacebookHomePageIsDisplayed() {
		assertTrue(new Element(By.id("email")).isDisplayed());
	}

}
