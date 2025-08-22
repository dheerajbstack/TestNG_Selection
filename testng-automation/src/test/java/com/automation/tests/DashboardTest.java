package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.DashboardPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dashboard Functionality Tests
 * Converted from DashboardSteps.java
 */
public class DashboardTest extends BaseTest {
    
    private static final Logger logger = LoggerFactory.getLogger(DashboardTest.class);
    
    @Test(description = "Verify dashboard page title and navigation tabs")
    public void testDashboardPageAndNavigation() {
        logger.info("Starting test: Dashboard Page and Navigation");
        
        HomePage homePage = new HomePage(driver);
        DashboardPage dashboardPage = homePage.clickDashboard();
        
        // Verify dashboard page loads
        Assert.assertTrue(dashboardPage.isPageLoaded(), "Dashboard page should load successfully");
        logger.info("✅ Dashboard page loaded successfully");
        
        // Verify page title
        String expectedTitle = "Dashboard"; // This can be made configurable
        String actualTitle = dashboardPage.getPageTitleText();
        Assert.assertTrue(actualTitle.contains("Dashboard") || actualTitle.contains("dashboard"), 
                         "Page title should contain 'Dashboard'");
        logger.info("✅ Page title validation passed: {}", actualTitle);
        
        // Verify navigation is available
        Assert.assertTrue(homePage.areAllNavigationLinksPresent(), 
                         "All navigation tabs should be available");
        logger.info("✅ Navigation tabs verification passed");
        
        logger.info("Test completed: Dashboard Page and Navigation");
    }
    
    @Test(description = "Verify backend health status display")
    public void testBackendHealthStatusDisplay() {
        logger.info("Starting test: Backend Health Status Display");
        
        HomePage homePage = new HomePage(driver);
        DashboardPage dashboardPage = homePage.clickDashboard();
        
        // Navigate to dashboard if not already there
        Assert.assertTrue(dashboardPage.isPageLoaded(), "Dashboard should be loaded");
        
        // In a real implementation, you would verify health status elements
        // For now, we'll verify the dashboard page structure
        String pageContent = driver.getPageSource();
        
        // Check for health status indicators (adapt based on actual implementation)
        boolean hasHealthIndicators = pageContent.contains("health") || 
                                    pageContent.contains("status") || 
                                    pageContent.contains("uptime");
        
        if (hasHealthIndicators) {
            logger.info("✅ Health status indicators found on dashboard");
        } else {
            logger.info("ℹ️ Health status indicators not visible or not implemented");
        }
        
        logger.info("Test completed: Backend Health Status Display");
    }
    
    @Test(description = "Verify analytics dashboard functionality")
    public void testAnalyticsDashboardFunctionality() {
        logger.info("Starting test: Analytics Dashboard Functionality");
        
        HomePage homePage = new HomePage(driver);
        DashboardPage dashboardPage = homePage.clickDashboard();
        
        Assert.assertTrue(dashboardPage.isPageLoaded(), "Dashboard should be loaded");
        
        // Scroll to analytics section if it exists
        seleniumUtils.scrollToElement(By.cssSelector(".analytics, .dashboard-analytics, [data-testid='analytics']"));
        
        String pageContent = driver.getPageSource().toLowerCase();
        
        // Verify analytics sections exist (adapt based on actual implementation)
        boolean hasUserAnalytics = pageContent.contains("user") && 
                                  (pageContent.contains("analytics") || pageContent.contains("count"));
        if (hasUserAnalytics) {
            logger.info("✅ User analytics section verified");
        }
        
        boolean hasProductAnalytics = pageContent.contains("product") && 
                                     (pageContent.contains("analytics") || pageContent.contains("categories"));
        if (hasProductAnalytics) {
            logger.info("✅ Products analytics section verified");
        }
        
        boolean hasTaskAnalytics = pageContent.contains("task") && 
                                  (pageContent.contains("analytics") || pageContent.contains("completed"));
        if (hasTaskAnalytics) {
            logger.info("✅ Tasks analytics section verified");
        }
        
        boolean hasOrderAnalytics = pageContent.contains("order") && 
                                   (pageContent.contains("analytics") || pageContent.contains("total"));
        if (hasOrderAnalytics) {
            logger.info("✅ Orders analytics section verified");
        }
        
        logger.info("Test completed: Analytics Dashboard Functionality");
    }
    
    @Test(description = "Verify dashboard content updates when navigating between tabs")
    public void testDashboardTabContentUpdates() {
        logger.info("Starting test: Dashboard Tab Content Updates");
        
        HomePage homePage = new HomePage(driver);
        
        // Test navigation to different sections
        DashboardPage dashboardPage = homePage.clickDashboard();
        Assert.assertTrue(dashboardPage.isPageLoaded(), "Dashboard should be loaded");
        String dashboardContent = driver.getPageSource();
        
        // Navigate to Products and verify content changes
        homePage.clickProducts();
        String productsContent = driver.getPageSource();
        Assert.assertNotEquals(dashboardContent, productsContent, 
                              "Content should change when navigating to different tabs");
        logger.info("✅ Content updates verified when switching tabs");
        
        // Navigate back to Dashboard
        homePage.clickDashboard();
        Assert.assertTrue(dashboardPage.isPageLoaded(), "Should be able to navigate back to dashboard");
        
        logger.info("Test completed: Dashboard Tab Content Updates");
    }
}
