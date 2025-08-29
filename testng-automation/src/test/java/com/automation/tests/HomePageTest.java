package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.DashboardPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Home Page Test class
 * Contains tests for home page functionality
 */
public class HomePageTest extends BaseTest {
    
    private static final Logger logger = LoggerFactory.getLogger(HomePageTest.class);
    
    @Test(description = "Verify home page loads successfully")
    public void testHomePageLoad() {
        logger.info("Starting test: testHomePageLoad");
        
        HomePage homePage = new HomePage(driver);
        
        // Verify page is loaded
        Assert.assertTrue(homePage.isPageLoaded(), "Home page should be loaded");
        
        // Verify navigation menu is visible
        Assert.assertTrue(homePage.isNavigationMenuVisible(), "Navigation menu should be visible");
        
        // Verify all navigation links are present
        Assert.assertTrue(homePage.areAllNavigationLinksPresent(), "All navigation links should be present");
        
        logger.info("Test completed: testHomePageLoad");
    }
    
    @Test(description = "Verify navigation to Dashboard page")
    public void testNavigationToDashboard() {
        logger.info("Starting test: testNavigationToDashboard");
        
        HomePage homePage = new HomePage(driver);
        
        // Navigate to Dashboard
        DashboardPage dashboardPage = homePage.clickDashboard();
        
        // Verify Dashboard page is loaded
        Assert.assertTrue(dashboardPage.isPageLoaded(), "Dashboard page should be loaded");
        
        // Verify page title contains expected text
        String pageTitle = dashboardPage.getPageTitleText();
        Assert.assertNotNull(pageTitle, "Page title should not be null");
        Assert.assertFalse(pageTitle.isEmpty(), "Page title should not be empty");
        
        logger.info("Test completed: testNavigationToDashboard");
    }
    
    @Test(description = "Verify page title")
    public void testPageTitle() {
        logger.info("Starting test: testPageTitle");
        
        HomePage homePage = new HomePage(driver);
        
        // Get page title
        String title = homePage.getPageTitle();
        
        // Verify title is not null or empty
        Assert.assertNotNull(title, "Page title should not be null");
        Assert.assertFalse(title.trim().isEmpty(), "Page title should not be empty");
        
        logger.info("Page title: {}", title);
        logger.info("Test completed: testPageTitle");
    }
    
    @Test(description = "Verify current URL")
    public void testCurrentUrl() {
        logger.info("Starting test: testCurrentUrl");
        
        HomePage homePage = new HomePage(driver);
        
        // Get current URL
        String currentUrl = homePage.getCurrentUrl();
        
        // Verify URL is not null or empty
        Assert.assertNotNull(currentUrl, "Current URL should not be null");
        Assert.assertFalse(currentUrl.trim().isEmpty(), "Current URL should not be empty");
        
        // Verify URL contains expected pattern
        Assert.assertTrue(currentUrl.contains("localhost") || currentUrl.contains("http"), 
                         "URL should contain localhost or http");
        
        logger.info("Current URL: {}", currentUrl);
        logger.info("Test completed: testCurrentUrl");
    }
}
