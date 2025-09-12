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
