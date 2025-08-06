package com.automation.utils;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enhanced WebDriver Manager utility class
 * Now integrates with DriverFactory for centralized driver management
 * Maintains backward compatibility while providing new features
 */
public class DriverManager {
    
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    
    /**
     * Set WebDriver instance for current thread
     * @param driver WebDriver instance
     */
    public static void setDriver(WebDriver driver) {
        logger.debug("Setting WebDriver instance for thread: {}", Thread.currentThread().getId());
        driverThreadLocal.set(driver);
    }
    
    /**
     * Get WebDriver instance for current thread
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            logger.error("No WebDriver instance found for thread: {}", Thread.currentThread().getId());
            throw new IllegalStateException("WebDriver has not been initialized for this thread. Make sure to use @web tag for web tests.");
        }
        return driver;
    }
    
    /**
     * Remove WebDriver instance for current thread
     */
    public static void removeDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            logger.debug("Removing WebDriver instance for thread: {}", Thread.currentThread().getId());
            driverThreadLocal.remove();
        }
    }
    
    /**
     * Check if WebDriver is initialized for current thread
     * @return true if WebDriver is initialized, false otherwise
     */
    public static boolean isDriverInitialized() {
        boolean initialized = driverThreadLocal.get() != null;
        logger.debug("WebDriver initialized check for thread {}: {}", Thread.currentThread().getId(), initialized);
        return initialized;
    }
    
    // ===== NEW FACTORY INTEGRATION METHODS =====
    
    /**
     * Create and set default Chrome driver (headless)
     * @return WebDriver instance
     */
    public static WebDriver createAndSetDefaultDriver() {
        logger.info("Creating and setting default Chrome driver (headless)");
        WebDriver driver = DriverFactory.createDefaultDriver();
        setDriver(driver);
        return driver;
    }
    
    /**
     * Create and set Chrome driver for debugging (headed)
     * @return WebDriver instance
     */
    public static WebDriver createAndSetDebugDriver() {
        logger.info("Creating and setting Chrome driver for debugging (headed)");
        WebDriver driver = DriverFactory.createDebugDriver();
        setDriver(driver);
        return driver;
    }
    
    /**
     * Create and set driver with specific browser and mode
     * @param browserType Browser type (CHROME, FIREFOX, EDGE)
     * @param executionMode Execution mode (HEADLESS, HEADED)
     * @return WebDriver instance
     */
    public static WebDriver createAndSetDriver(DriverFactory.BrowserType browserType, 
                                               DriverFactory.ExecutionMode executionMode) {
        logger.info("Creating and setting {} driver in {} mode", browserType, executionMode);
        WebDriver driver = DriverFactory.createDriver(browserType, executionMode);
        setDriver(driver);
        return driver;
    }
    
    /**
     * Close current driver and remove from thread
     */
    public static void closeAndRemoveDriver() {
        if (isDriverInitialized()) {
            logger.info("Closing and removing WebDriver for thread: {}", Thread.currentThread().getId());
            WebDriver driver = getDriver();
            try {
                driver.quit();
                logger.debug("WebDriver quit successfully");
            } catch (Exception e) {
                logger.error("Error while quitting WebDriver: {}", e.getMessage(), e);
            } finally {
                removeDriver();
            }
        } else {
            logger.warn("No WebDriver to close for thread: {}", Thread.currentThread().getId());
        }
    }
}
