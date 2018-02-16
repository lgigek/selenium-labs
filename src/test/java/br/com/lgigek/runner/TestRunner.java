package br.com.lgigek.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = { "pretty", "html:target/cucumber" },
        glue = "br.com.lgigek.steps",
        features = "classpath:features")
public class TestRunner {
	
}
