package br.com.lgigek.steps;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinition {

	WebDriver driver;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	@When("^The user navigates to Google home page$")
	public void accessGoogleHomePage() {
		driver.get("http://www.google.com");
	}

	@Then("^Google home page is displayed$")
	public void verifyIfGoogleHomePageIsDisplayed() {
		assertTrue(driver.findElement(By.name("q")).isDisplayed());
	}

	@When("^The user navigates to Facebook home page$")
	public void accessFacebookHomePage() {
		driver.get("http://www.facebook.com");
	}

	@Then("^Facebook home page is displayed$")
	public void verifyIfFacebookHomePageIsDisplayed() {
		assertTrue(driver.findElement(By.id("email")).isDisplayed());
	}

}
