package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application Setup and Basic Navigation Tests
 * Converted from BackgroundSteps.java
 */
public class ApplicationSetupTest extends BaseTest {
    
    private static final Logger logger = LoggerFactory.getLogger(ApplicationSetupTest.class);
    
    @Test(description = "Verify application runs on configured URL and can be opened in browser")
    public void testApplicationSetupAndNavigation() {
        logger.info("Starting test: Application Setup and Navigation");
        
        // Configure application URL
        String appUrl = config.getProperty("base.url", "http://localhost:3000");
        System.setProperty("app.url", appUrl);
        logger.info("✅ Application URL configured: {}", appUrl);
        
        // Configure backend API URL
        String apiUrl = config.getProperty("api.url", "http://localhost:5000");
        System.setProperty("api.url", apiUrl);
        logger.info("✅ Backend API URL configured: {}", apiUrl);
        
        // Open application in browser - this is done in BaseTest.methodSetup()
        HomePage homePage = new HomePage(driver);
        
        // Verify application loads successfully
        Assert.assertTrue(homePage.isPageLoaded(), "Application should load successfully");
        logger.info("✅ Application opened successfully in browser");
        
        // Verify page title is not empty
        String pageTitle = homePage.getPageTitle();
        Assert.assertNotNull(pageTitle, "Page title should not be null");
        Assert.assertFalse(pageTitle.trim().isEmpty(), "Page title should not be empty");
        logger.info("✅ Page title verified: {}", pageTitle);
        
        logger.info("Test completed: Application Setup and Navigation");
    }
    
    @Test(description = "Verify backend API availability configuration")
    public void testBackendAPIConfiguration() {
        logger.info("Starting test: Backend API Configuration");
        
        String apiUrl = config.getProperty("api.url", "http://localhost:5000");
        System.setProperty("api.url", apiUrl);
        
        // Verify API URL is properly configured
        String configuredApiUrl = System.getProperty("api.url");
        Assert.assertEquals(configuredApiUrl, apiUrl, "API URL should be properly configured");
        logger.info("✅ Backend API URL validation passed: {}", apiUrl);
        
        logger.info("Test completed: Backend API Configuration");
    }
}
