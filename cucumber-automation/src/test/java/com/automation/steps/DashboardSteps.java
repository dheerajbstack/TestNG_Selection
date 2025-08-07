package com.automation.steps;

import com.automation.businessLayer.DashboardBL;
import com.automation.utils.LogCapture;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DashboardSteps {
    
    private DashboardBL dashboardBL;
    
    public DashboardSteps() {
        this.dashboardBL = new DashboardBL();
    }

    @Then("the page title should be {string}")
    public void the_page_title_should_be(String expectedTitle) {
        dashboardBL.validatePageTitle(expectedTitle);
        LogCapture.addStepLog("Title Validation", "✅ Page title validation passed: " + expectedTitle);
    }

    @Then("I should see navigation tabs: {string}")
    public void i_should_see_navigation_tabs(String expectedTabs) {
        dashboardBL.validateNavigationTabs(expectedTabs);
    }

    @Then("I click on each navigation tab, the active state and content should update accordingly")
    public void i_click_on_each_navigation_tab_the_active_state_and_content_should_update_accordingly() {
        dashboardBL.validateContentAndActiveStateChanges();
    }


    @When("I view the {string} section")
    public void i_view_the_section(String sectionName) {
        dashboardBL.viewBackendStatusSection();
    }

    @Then("I should see the health status as {string}")
    public void i_should_see_the_health_status_as(String expectedStatus) {
            dashboardBL.verifyHealthStatusOK();
    }

    @Then("I should see the uptime displayed in seconds")
    public void i_should_see_the_uptime_displayed_in_seconds() {
        dashboardBL.verifyUptimeDisplay();
         }

    // TC003 - Analytics Dashboard Functionality Steps
    @When("I scroll to the Analytics Dashboard section")
    public void i_scroll_to_the_analytics_dashboard_section() {
        LogCapture.logTestStep("📊 Scrolling to Analytics Dashboard section...");
        
        dashboardBL.scrollToAnalyticsDashboard();
        
        LogCapture.addStepLog("Analytics Navigation", "✅ Successfully scrolled to Analytics Dashboard section");
    }

    @Then("I should see User analytics with total count and admin count")
    public void i_should_see_user_analytics_with_total_count_and_admin_count() {
        LogCapture.logTestStep("👥 Verifying User analytics display...");
        dashboardBL.verifyUserAnalytics();
        
        LogCapture.addStepLog("User Analytics", "✅ User analytics with total and admin counts verified");
    }

    @Then("I should see Products analytics with total, categories, and total value")
    public void i_should_see_products_analytics_with_total_categories_and_total_value() {

        dashboardBL.verifyProductsAnalytics();
        
        LogCapture.addStepLog("Products Analytics", "✅ Products analytics with total, categories, and value verified");
    }

    @Then("I should see Tasks analytics with total, completed, pending, and high priority counts")
    public void i_should_see_tasks_analytics_with_total_completed_pending_and_high_priority_counts() {
        LogCapture.logTestStep("📋 Verifying Tasks analytics display...");
        
        dashboardBL.verifyTasksAnalytics();
        
        LogCapture.addStepLog("Tasks Analytics", "✅ Tasks analytics with all status counts verified");
    }

    @Then("I should see Orders analytics if orders exist")
    public void i_should_see_orders_analytics_if_orders_exist() {
        LogCapture.logTestStep("🛒 Verifying Orders analytics display...");
        
        dashboardBL.verifyOrdersAnalytics();
        
        LogCapture.addStepLog("Orders Analytics", "✅ Orders analytics verification completed");
    }
//
//    // Additional helper steps for comprehensive testing
//    @When("I perform complete dashboard validation")
//    public void i_perform_complete_dashboard_validation() {
//        LogCapture.logTestStep("🔍 Performing comprehensive dashboard validation...");
//
//        String appUrl = System.getProperty("app.url", "http://localhost:3000");
//        String apiUrl = System.getProperty("api.url", "http://localhost:5000");
//        String expectedTitle = "Enhanced Full-Stack React App";
//        String expectedTabs = "Dashboard, Users, Products, Tasks, Orders, Search, Files & Themes";
//
//        dashboardBL.performCompleteDashboardValidation(appUrl, apiUrl, expectedTitle, expectedTabs);
//
//        LogCapture.addStepLog("Complete Validation", "✅ Comprehensive dashboard validation completed successfully");
//    }
//
//    @Then("all dashboard functionality should work correctly")
//    public void all_dashboard_functionality_should_work_correctly() {
//        LogCapture.logTestStep("✅ Validating all dashboard functionality...");
//
//        // This step serves as a verification that all previous steps passed
//        // Additional validations could be added here if needed
//
//        LogCapture.addStepLog("Dashboard Validation", "✅ All dashboard functionality verified and working correctly");
//    }
//
//    @When("I verify backend API connectivity")
//    public void i_verify_backend_api_connectivity() {
//        LogCapture.logTestStep("🔗 Verifying backend API connectivity...");
//
//        String apiUrl = System.getProperty("api.url", "http://localhost:5000");
//        dashboardBL.verifyBackendAPIAvailability(apiUrl);
//
//        LogCapture.addStepLog("API Connectivity", "✅ Backend API connectivity verified");
//    }
//
//    @Then("the backend should be responsive and healthy")
//    public void the_backend_should_be_responsive_and_healthy() {
//        LogCapture.logTestStep("💊 Verifying backend health and responsiveness...");
//
//        dashboardBL.performCompleteHealthCheck();
//
//        LogCapture.addStepLog("Backend Health", "✅ Backend verified as responsive and healthy");
//    }
}
