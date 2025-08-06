package com.automation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DriverFactory - Centralized WebDriver management
 * This class provides a factory pattern for creating and managing WebDriver instances
 * across the entire test framework
 */
public class DriverFactory {
    
    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    
    // Browser types enum
    public enum BrowserType {
        CHROME, FIREFOX, EDGE
    }
    
    // Execution modes enum
    public enum ExecutionMode {
        HEADLESS, HEADED
    }
    
    /**
     * Creates WebDriver instance based on browser type and execution mode
     * @param browserType - Type of browser (CHROME, FIREFOX, EDGE)
     * @param executionMode - Execution mode (HEADLESS, HEADED)
     * @return WebDriver instance
     */
    public static WebDriver createDriver(BrowserType browserType, ExecutionMode executionMode) {
        logger.info("Creating {} driver in {} mode", browserType, executionMode);
        
        WebDriver driver;
        
        try {
            switch (browserType) {
                case CHROME:
                    driver = createChromeDriver(executionMode);
                    break;
                case FIREFOX:
                    driver = createFirefoxDriver(executionMode);
                    break;
                case EDGE:
                    driver = createEdgeDriver(executionMode);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser type: " + browserType);
            }
            
            // Configure common driver settings
            configureDriver(driver);
            
            // Store in ThreadLocal for thread safety
            driverThreadLocal.set(driver);
            
            logger.info("Successfully created {} driver in {} mode", browserType, executionMode);
            return driver;
            
        } catch (Exception e) {
            logger.error("Failed to create {} driver: {}", browserType, e.getMessage(), e);
            throw new RuntimeException("Driver creation failed for " + browserType, e);
        }
    }
    
    /**
     * Creates Chrome WebDriver with specified execution mode
     */
    private static WebDriver createChromeDriver(ExecutionMode executionMode) {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        
        // Common Chrome options
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-web-security");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-plugins");
        options.addArguments("--disable-default-apps");
        options.addArguments("--disable-background-timer-throttling");
        options.addArguments("--disable-backgrounding-occluded-windows");
        options.addArguments("--disable-renderer-backgrounding");
        options.addArguments("--remote-debugging-port=0");
    
        
        return new ChromeDriver(options);
    }
    
    /**
     * Creates Firefox WebDriver with specified execution mode
     */
    private static WebDriver createFirefoxDriver(ExecutionMode executionMode) {
        WebDriverManager.firefoxdriver().setup();
        
        FirefoxOptions options = new FirefoxOptions();
        
        // Set headless mode if specified
        if (executionMode == ExecutionMode.HEADLESS) {
            options.addArguments("--headless");
        }
        
        return new FirefoxDriver(options);
    }
    
    /**
     * Creates Edge WebDriver with specified execution mode
     */
    private static WebDriver createEdgeDriver(ExecutionMode executionMode) {
        WebDriverManager.edgedriver().setup();
        
        EdgeOptions options = new EdgeOptions();
        
        // Set headless mode if specified
        if (executionMode == ExecutionMode.HEADLESS) {
            options.addArguments("--headless");
        }
        
        return new EdgeDriver(options);
    }
    
    /**
     * Configure common driver settings
     */
    private static void configureDriver(WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }
    
    /**
     * Get current WebDriver instance from ThreadLocal
     * @return Current WebDriver instance
     */
    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            logger.error("No WebDriver instance found in current thread");
            throw new IllegalStateException("WebDriver is not initialized. Call createDriver() first.");
        }
        return driver;
    }
    
    /**
     * Check if WebDriver is initialized in current thread
     * @return true if driver exists, false otherwise
     */
    public static boolean isDriverInitialized() {
        return driverThreadLocal.get() != null;
    }
    
    /**
     * Close and remove WebDriver instance from current thread
     */
    public static void closeDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                logger.info("Closing WebDriver instance");
                driver.quit();
                logger.info("WebDriver closed successfully");
            } catch (Exception e) {
                logger.error("Error while closing WebDriver: {}", e.getMessage(), e);
            } finally {
                driverThreadLocal.remove();
                logger.info("WebDriver removed from ThreadLocal");
            }
        } else {
            logger.warn("No WebDriver instance to close");
        }
    }
    
    /**
     * Create default Chrome driver in headless mode
     * @return WebDriver instance
     */
    public static WebDriver createDefaultDriver() {
        return createDriver(BrowserType.CHROME, ExecutionMode.HEADLESS);
    }
    
    /**
     * Create Chrome driver in headed mode for debugging
     * @return WebDriver instance
     */
    public static WebDriver createDebugDriver() {
        return createDriver(BrowserType.CHROME, ExecutionMode.HEADED);
    }
}
