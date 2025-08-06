package com.automation.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Test;

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.automation.steps", "com.automation.hooks"},
    plugin = {
        "pretty", 
        "html:target/cucumber-reports/report.html", 
        "json:target/cucumber-reports/report.json",
        "summary"
    },
    monochrome = true
)
public class TestNGRunner extends AbstractTestNGCucumberTests {
    
    @Test(groups = "cucumber", description = "Runs Cucumber Features")
    public void runCucumber() {
        // This method will be called by TestNG and will run all scenarios
    }
}