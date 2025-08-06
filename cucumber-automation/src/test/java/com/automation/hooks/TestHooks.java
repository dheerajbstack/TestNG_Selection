package com.automation.hooks;

import com.automation.utils.DriverFactory;
import com.automation.utils.DriverManager;
import com.automation.utils.LogCapture;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHooks {
    
    private static final Logger logger = LoggerFactory.getLogger(TestHooks.class);
    
    @Before
    public void setUp(Scenario scenario) {
        // Start step-by-step log capture for this scenario
        LogCapture.startLogCapture(scenario);
        
        logger.info("=== Starting Test Scenario: {} ===", scenario.getName());
        
        // Initialize WebDriver using DriverFactory
        initializeWebDriverUsingFactory(scenario);
    }
    
    private void initializeWebDriverUsingFactory(Scenario scenario) {
        logger.info("Initializing WebDriver using DriverFactory for scenario: {}", scenario.getName());
        LogCapture.logDriverInit();
        
        try {
            // Use DriverFactory to create Chrome driver in headless mode
            WebDriver driver = DriverFactory.createDefaultDriver();
            
            // Set driver in DriverManager for backward compatibility
            DriverManager.setDriver(driver);
            
            LogCapture.logDriverInitSuccess();
            logger.info("WebDriver initialized successfully using DriverFactory for scenario: {}", scenario.getName());
            
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver using DriverFactory: {}", e.getMessage(), e);
            LogCapture.logDriverInitFailure(e.getMessage());
            throw new RuntimeException("WebDriver initialization failed", e);
        }
    }
    
    @After
    public void tearDown(Scenario scenario) {
        logger.info("=== Finishing Test Scenario: {} ===", scenario.getName());
        
        // Check if scenario failed and take appropriate action
        if (scenario.isFailed()) {
            LogCapture.addStepLog("Test Result", "❌ Scenario FAILED: %s", scenario.getName());
            logger.error("Scenario failed: {}", scenario.getName());
            
            // Take screenshot if test failed
            takeScreenshotOnFailure(scenario);
        } else {
            LogCapture.addStepLog("Test Result", "✅ Scenario PASSED: %s", scenario.getName());
            logger.info("Scenario passed: {}", scenario.getName());
            
            // Take screenshot for successful test as well (optional)
            takeScreenshotOnSuccess(scenario);
        }
        
        // Cleanup WebDriver
        cleanupWebDriver(scenario);
        
        // Stop log capture and save final summary
        LogCapture.stopLogCaptureAndSave(scenario);
    }
    
    private void takeScreenshotOnFailure(Scenario scenario) {
        try {
            if (DriverManager.isDriverInitialized()) {
                LogCapture.logScreenshotCapture();
                WebDriver driver = DriverManager.getDriver();
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                
                // Use enhanced screenshot attachment method
                LogCapture.attachScreenshot(screenshot, "Failed Test Screenshot");
                logger.info("Screenshot attached for failed scenario: {}", scenario.getName());
            }
        } catch (Exception e) {
            logger.error("Failed to take screenshot: {}", e.getMessage(), e);
            LogCapture.logError("Screenshot Capture", e.getMessage());
        }
    }
    
    private void takeScreenshotOnSuccess(Scenario scenario) {
        try {
            if (DriverManager.isDriverInitialized()) {
                LogCapture.addStepLog("Success Screenshot", "📸 Taking screenshot for successful test...");
                WebDriver driver = DriverManager.getDriver();
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                
                // Use enhanced screenshot attachment method
                LogCapture.attachScreenshot(screenshot, "Successful Test Screenshot");
                logger.info("Success screenshot attached for scenario: {}", scenario.getName());
            }
        } catch (Exception e) {
            logger.error("Failed to take success screenshot: {}", e.getMessage(), e);
            LogCapture.logError("Success Screenshot", e.getMessage());
        }
    }
    
    private void cleanupWebDriver(Scenario scenario) {
        logger.info("Cleaning up WebDriver for scenario: {}", scenario.getName());
        LogCapture.logDriverCleanup();
        
        try {
            // Use DriverFactory to close driver properly
            DriverFactory.closeDriver();
            
            // Also remove from DriverManager for consistency
            DriverManager.removeDriver();
            
            LogCapture.logDriverCleanupSuccess();
            logger.info("WebDriver closed successfully using DriverFactory for scenario: {}", scenario.getName());
            
        } catch (Exception e) {
            logger.error("Error during WebDriver cleanup: {}", e.getMessage(), e);
            LogCapture.logError("WebDriver Cleanup", e.getMessage());
        }
    }
}
