package com.automation.steps;

import com.automation.businessLayer.SimpleTestBL;
import com.automation.utils.LogCapture;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SimpleSteps {
        
    @Given("I open the web application")
    public void i_open_the_web_application() {
        LogCapture.logTestStep("🌐 Opening web application...");
        new SimpleTestBL().openApplication();
        LogCapture.addStepLog("Navigation", "✅ Web application opened successfully");
    }
    
    @When("I check the page title")
    public void i_check_the_page_title() {
        LogCapture.logTestStep("🔍 Checking page title...");
        new SimpleTestBL().checkPageTitle();
        LogCapture.addStepLog("Page Validation", "✅ Page title retrieved and ready for validation");
    }
    
    @Then("I should see the title {string}")
    public void i_should_see_the_title(String expectedTitle) {
        LogCapture.logTestStepWithDetails("🎯 Verifying page title", 
            "Expected title: " + expectedTitle,
            "Performing title validation...");
        new SimpleTestBL().validatePageTitle(expectedTitle);
        LogCapture.addStepLog("Validation Success", "✅ Title validation passed! Expected title matches actual title.");
    }
}
