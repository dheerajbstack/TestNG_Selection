package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Application Tests
 * Converted from SimpleSteps.java
 */
public class SimpleApplicationTest extends BaseTest {
    
    private static final Logger logger = LoggerFactory.getLogger(SimpleApplicationTest.class);
    
    @Test(description = "Open web application and verify basic functionality")
    public void testOpenWebApplicationAndVerifyTitle() {
        logger.info("Starting test: Open Web Application and Verify Title");
        
        // Open web application - this is done in BaseTest.methodSetup()
        HomePage homePage = new HomePage(driver);
        logger.info("✅ Web application opened successfully");
        
        // Check page title
        String actualTitle = homePage.getPageTitle();
        logger.info("🔍 Retrieved page title: {}", actualTitle);
        
        // Verify title is not null or empty
        Assert.assertNotNull(actualTitle, "Page title should not be null");
        Assert.assertFalse(actualTitle.trim().isEmpty(), "Page title should not be empty");
        logger.info("✅ Page title validation passed");
        
        logger.info("Test completed: Open Web Application and Verify Title");
    }
    
    @Test(description = "Verify specific page title matches expected value")
    public void testPageTitleMatchesExpected() {
        logger.info("Starting test: Page Title Matches Expected");
        
        HomePage homePage = new HomePage(driver);
        
        // Get expected title from configuration or use default
        String expectedTitle = config.getProperty("app.title", "React App");
        
        // Check page title
        String actualTitle = homePage.getPageTitle();
        logger.info("🎯 Expected title: {}", expectedTitle);
        logger.info("🔍 Actual title: {}", actualTitle);
        
        // Verify title matches expected (allowing for partial match)
        boolean titleMatches = actualTitle.contains(expectedTitle) || 
                              actualTitle.toLowerCase().contains(expectedTitle.toLowerCase());
        
        Assert.assertTrue(titleMatches, 
                         String.format("Page title should contain '%s', but was '%s'", expectedTitle, actualTitle));
        
        logger.info("✅ Title validation passed! Expected title matches actual title");
        logger.info("Test completed: Page Title Matches Expected");
    }
    
    @Test(description = "Verify application loads within reasonable time")
    public void testApplicationLoadTime() {
        logger.info("Starting test: Application Load Time");
        
        long startTime = System.currentTimeMillis();
        
        HomePage homePage = new HomePage(driver);
        
        // Verify page is loaded
        Assert.assertTrue(homePage.isPageLoaded(), "Application should load successfully");
        
        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;
        
        logger.info("✅ Application load time: {} ms", loadTime);
        
        // Verify load time is reasonable (less than 30 seconds)
        Assert.assertTrue(loadTime < 30000, 
                         String.format("Application should load within 30 seconds, but took %d ms", loadTime));
        
        logger.info("✅ Application load time validation passed");
        logger.info("Test completed: Application Load Time");
    }
    
    @Test(description = "Verify page structure and basic elements are present")
    public void testBasicPageStructure() {
        logger.info("Starting test: Basic Page Structure");
        
        HomePage homePage = new HomePage(driver);
        
        // Verify page is loaded
        Assert.assertTrue(homePage.isPageLoaded(), "Page should be loaded");
        
        // Verify basic HTML structure
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains("<html"), "Page should have valid HTML structure");
        Assert.assertTrue(pageSource.contains("<body"), "Page should have body element");
        Assert.assertTrue(pageSource.contains("<head"), "Page should have head element");
        
        logger.info("✅ Basic HTML structure verified");
        
        // Verify navigation is present
        Assert.assertTrue(homePage.isNavigationMenuVisible(), "Navigation menu should be visible");
        
        logger.info("✅ Navigation structure verified");
        logger.info("Test completed: Basic Page Structure");
    }
    
    @Test(description = "Verify application URL is accessible")
    public void testApplicationURLAccessibility() {
        logger.info("Starting test: Application URL Accessibility");
        
        HomePage homePage = new HomePage(driver);
        
        // Get current URL
        String currentUrl = homePage.getCurrentUrl();
        logger.info("🔗 Current URL: {}", currentUrl);
        
        // Verify URL is valid
        Assert.assertNotNull(currentUrl, "Current URL should not be null");
        Assert.assertTrue(currentUrl.startsWith("http"), "URL should start with http");
        
        // Verify we can navigate to the expected base URL
        String expectedBaseUrl = config.getProperty("base.url", "http://localhost:3000");
        Assert.assertTrue(currentUrl.contains(expectedBaseUrl) || currentUrl.startsWith(expectedBaseUrl),
                         String.format("Current URL should be based on %s", expectedBaseUrl));
        
        logger.info("✅ URL accessibility validation passed");
        logger.info("Test completed: Application URL Accessibility");
    }
}
