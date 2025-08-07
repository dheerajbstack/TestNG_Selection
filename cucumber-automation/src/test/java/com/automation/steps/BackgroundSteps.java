package com.automation.steps;

import com.automation.businessLayer.BackgroundBL;
import com.automation.businessLayer.DashboardBL;
import com.automation.utils.LogCapture;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class BackgroundSteps {

    @Given("the application is running on {string}")
    public void the_application_is_running_on(String appUrl) {
        LogCapture.logTestStepWithDetails("🌐 Application Setup","Application URL: " + appUrl);

        System.setProperty("app.url", appUrl);
        LogCapture.addStepLog("Environment Setup", "✅ Application URL configured: " + appUrl);
    }

    @Given("the backend API is available at {string}")
    public void the_backend_api_is_available_at(String apiUrl) {
        LogCapture.logTestStepWithDetails("🔧 Backend API Setup","API URL: " + apiUrl);

        // Store the API URL for validation
        System.setProperty("api.url", apiUrl);
        LogCapture.addStepLog("API Configuration", "✅ Backend API URL configured: " + apiUrl);
    }

    @When("I open the application in browser")
    public void i_open_the_application_in_browser() {
        LogCapture.logTestStep("🚀 Opening application in browser...");

        String appUrl = System.getProperty("app.url", "http://localhost:3000");
        new BackgroundBL().openApplication(appUrl);

        LogCapture.addStepLog("Navigation", "✅ Application opened successfully in browser");
    }



}
