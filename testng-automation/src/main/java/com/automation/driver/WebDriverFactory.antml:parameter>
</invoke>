package com.automation.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * WebDriverFactory class for managing WebDriver instances
 * Supports Chrome, Firefox, Edge, and Safari browsers
 * Includes headless mode support
 */
public class WebDriverFactory {
    
    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    /**
     * Browser types enum
     */
    public enum BrowserType {
        CHROME, FIREFOX, EDGE, SAFARI
    }
    
    /**
     * Initialize WebDriver based on browser type
     * @param browserType The browser to initialize
     * @param headless Whether to run in headless mode
     * @return WebDriver instance
     */
    public static WebDriver initializeDriver(BrowserType browserType, boolean headless) {
        WebDriver webDriver = null;
        
        try {
            switch (browserType) {
                case CHROME:
                    webDriver = createChromeDriver(headless);
                    break;
                case FIREFOX:
                    webDriver = createFirefoxDriver(headless);
                    break;
                case EDGE:
                    webDriver = createEdgeDriver(headless);
                    break;
                case SAFARI:
                    webDriver = createSafariDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Browser type not supported: " + browserType);
            }
            
            // Configure WebDriver settings
            webDriver.manage().window().maximize();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            webDriver.manage().deleteAllCookies();
            
            driver.set(webDriver);
            logger.info("WebDriver initialized successfully for browser: {}", browserType);
            
        } catch (Exception e) {
            logger.error("Failed to initialize WebDriver for browser: {}", browserType, e);
            throw new RuntimeException("WebDriver initialization failed", e);
        }
        
        return webDriver;
    }
    
    /**
     * Initialize WebDriver based on system property or default to Chrome
     * @return WebDriver instance
     */
    public static WebDriver initializeDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        
        BrowserType browserType;
        switch (browser) {
            case "firefox":
                browserType = BrowserType.FIREFOX;
                break;
            case "edge":
                browserType = BrowserType.EDGE;
                break;
            case "safari":
                browserType = BrowserType.SAFARI;
                break;
            default:
                browserType = BrowserType.CHROME;
        }
        
        return initializeDriver(browserType, headless);
    }
    
    /**
     * Get current WebDriver instance
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver == null) {
            throw new IllegalStateException("WebDriver is not initialized. Call initializeDriver() first.");
        }
        return webDriver;
    }
    
    /**
     * Quit WebDriver and clean up resources
     */
    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            try {
                webDriver.quit();
                logger.info("WebDriver quit successfully");
            } catch (Exception e) {
                logger.error("Error while quitting WebDriver", e);
            } finally {
                driver.remove();
            }
        }
    }
    
    /**
     * Create Chrome WebDriver with options
     */
    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-web-security");
        options.addArguments("--disable-features=VizDisplayCompositor");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");
        
        if (headless) {
            options.addArguments("--headless");
            logger.info("Chrome driver initialized in headless mode");
        } else {
            logger.info("Chrome driver initialized in normal mode");
        }
        
        return new ChromeDriver(options);
    }
    
    /**
     * Create Firefox WebDriver with options
     */
    private static WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        
        FirefoxOptions options = new FirefoxOptions();
        
        if (headless) {
            options.addArguments("--headless");
            logger.info("Firefox driver initialized in headless mode");
        } else {
            logger.info("Firefox driver initialized in normal mode");
        }
        
        return new FirefoxDriver(options);
    }
    
    /**
     * Create Edge WebDriver with options
     */
    private static WebDriver createEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver().setup();
        
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-web-security");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        
        if (headless) {
            options.addArguments("--headless");
            logger.info("Edge driver initialized in headless mode");
        } else {
            logger.info("Edge driver initialized in normal mode");
        }
        
        return new EdgeDriver(options);
    }
    
    /**
     * Create Safari WebDriver
     * Note: Safari doesn't support headless mode
     */
    private static WebDriver createSafariDriver() {
        logger.info("Safari driver initialized (headless mode not supported)");
        return new SafariDriver();
    }
    
    /**
     * Check if WebDriver is initialized
     * @return true if initialized, false otherwise
     */
    public static boolean isDriverInitialized() {
        return driver.get() != null;
    }
}
