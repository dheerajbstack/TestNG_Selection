package com.automation.base;

import com.automation.driver.WebDriverFactory;
import com.automation.utils.ConfigReader;
import com.automation.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

/**
 * Base Test class that provides common setup and teardown for all test classes
 */
public abstract class BaseTest {
    
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected WebDriver driver;
    protected ConfigReader config;
    protected SeleniumUtils seleniumUtils;
    
    @BeforeSuite(alwaysRun = true)
    public void suiteSetup() {
        logger.info("=== Test Suite Started ===");
        config = ConfigReader.getInstance();
    }
    
    @BeforeClass(alwaysRun = true)
    public void classSetup() {
        logger.info("=== Test Class Setup: {} ===", this.getClass().getSimpleName());
    }
    
    @BeforeMethod(alwaysRun = true)
    public void methodSetup() {
        logger.info("=== Test Method Setup ===");
        driver = WebDriverFactory.initializeDriver();
        seleniumUtils = new SeleniumUtils(driver);
        config = ConfigReader.getInstance();
        String baseUrl = config.getProperty("base.url", "http://localhost:3000");
        logger.info("Navigating to base URL: {}", baseUrl);
        driver.get(baseUrl);
    }
    
    @AfterMethod(alwaysRun = true)
    public void methodTeardown() {
        logger.info("=== Test Method Teardown ===");
        if (WebDriverFactory.isDriverInitialized()) {
            WebDriverFactory.quitDriver();
        }
    }
    
    @AfterClass(alwaysRun = true)
    public void classTeardown() {
        logger.info("=== Test Class Teardown: {} ===", this.getClass().getSimpleName());
    }
    
    @AfterSuite(alwaysRun = true)
    public void suiteTeardown() {
        logger.info("=== Test Suite Completed ===");
    }
    
    /**
     * Get the current WebDriver instance
     * @return WebDriver instance
     */
    protected WebDriver getDriver() {
        return driver;
    }
    
    /**
     * Navigate to a specific URL
     * @param url The URL to navigate to
     */
    protected void navigateTo(String url) {
        logger.info("Navigating to URL: {}", url);
        driver.get(url);
    }
    
    /**
     * Get the current page title
     * @return Current page title
     */
    protected String getPageTitle() {
        String title = driver.getTitle();
        logger.info("Current page title: {}", title);
        return title;
    }
    
    /**
     * Get the current page URL
     * @return Current page URL
     */
    protected String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.info("Current page URL: {}", url);
        return url;
    }
}
