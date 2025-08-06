package com.automation.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for taking screenshots in the automation framework
 */
public class ScreenshotUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOT_DIR = "target/screenshots";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    
    /**
     * Takes a screenshot using the current WebDriver instance from DriverManager
     * @param screenshotName Name for the screenshot file (without extension)
     * @return Full path to the saved screenshot file
     */
    public static String takeScreenshot(String screenshotName) {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            logger.warn("WebDriver is null, cannot take screenshot");
            return null;
        }
        
        return takeScreenshot(driver, screenshotName);
    }
    
    /**
     * Takes a screenshot using the provided WebDriver instance
     * @param driver WebDriver instance to use for screenshot
     * @param screenshotName Name for the screenshot file (without extension)
     * @return Full path to the saved screenshot file
     */
    public static String takeScreenshot(WebDriver driver, String screenshotName) {
        if (driver == null) {
            logger.warn("WebDriver is null, cannot take screenshot");
            return null;
        }
        
        try {
            // Ensure screenshot directory exists
            createScreenshotDirectory();
            
            // Generate timestamp for unique filename
            String timestamp = LocalDateTime.now().format(DATE_FORMAT);
            String fileName = String.format("%s_%s.png", screenshotName, timestamp);
            String filePath = SCREENSHOT_DIR + File.separator + fileName;
            
            // Take screenshot
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            
            // Copy screenshot to destination
            Files.copy(sourceFile.toPath(), destFile.toPath());
            
            logger.info("Screenshot saved: {}", destFile.getAbsolutePath());
            return destFile.getAbsolutePath();
            
        } catch (IOException e) {
            logger.error("Failed to take screenshot: {}", e.getMessage(), e);
            return null;
        } catch (Exception e) {
            logger.error("Unexpected error while taking screenshot: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Takes a screenshot for failed scenarios
     * @param scenarioName Name of the failed scenario
     * @return Full path to the saved screenshot file
     */
    public static String takeFailureScreenshot(String scenarioName) {
        String sanitizedName = sanitizeFileName(scenarioName);
        return takeScreenshot("FAILED_" + sanitizedName);
    }
    
    /**
     * Takes a screenshot for passed scenarios (optional)
     * @param scenarioName Name of the passed scenario
     * @return Full path to the saved screenshot file
     */
    public static String takePassScreenshot(String scenarioName) {
        String sanitizedName = sanitizeFileName(scenarioName);
        return takeScreenshot("PASSED_" + sanitizedName);
    }
    
    /**
     * Takes a screenshot at a specific step
     * @param stepName Name of the step
     * @param scenarioName Name of the scenario
     * @return Full path to the saved screenshot file
     */
    public static String takeStepScreenshot(String stepName, String scenarioName) {
        String sanitizedScenario = sanitizeFileName(scenarioName);
        String sanitizedStep = sanitizeFileName(stepName);
        return takeScreenshot(String.format("STEP_%s_%s", sanitizedScenario, sanitizedStep));
    }
    
    /**
     * Gets the screenshot as byte array for embedding in reports
     * @return Screenshot as byte array, null if failed
     */
    public static byte[] getScreenshotAsBytes() {
        WebDriver driver = DriverManager.getDriver();
        if (driver == null) {
            logger.warn("WebDriver is null, cannot take screenshot");
            return null;
        }
        
        return getScreenshotAsBytes(driver);
    }
    
    /**
     * Gets the screenshot as byte array for embedding in reports
     * @param driver WebDriver instance to use
     * @return Screenshot as byte array, null if failed
     */
    public static byte[] getScreenshotAsBytes(WebDriver driver) {
        if (driver == null) {
            logger.warn("WebDriver is null, cannot take screenshot");
            return null;
        }
        
        try {
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            return takesScreenshot.getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            logger.error("Failed to get screenshot as bytes: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Creates the screenshot directory if it doesn't exist
     */
    private static void createScreenshotDirectory() {
        try {
            Path screenshotPath = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(screenshotPath)) {
                Files.createDirectories(screenshotPath);
                logger.info("Created screenshot directory: {}", screenshotPath.toAbsolutePath());
            }
        } catch (IOException e) {
            logger.error("Failed to create screenshot directory: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Sanitizes file names by removing invalid characters
     * @param fileName Original file name
     * @return Sanitized file name
     */
    private static String sanitizeFileName(String fileName) {
        if (fileName == null) {
            return "unknown";
        }
        
        // Replace spaces and invalid characters with underscores
        return fileName.replaceAll("[^a-zA-Z0-9\\-_]", "_")
                      .replaceAll("_{2,}", "_")  // Replace multiple underscores with single
                      .trim();
    }
    
    /**
     * Gets the screenshot directory path
     * @return Screenshot directory path
     */
    public static String getScreenshotDirectory() {
        return SCREENSHOT_DIR;
    }
    
    /**
     * Cleans up old screenshots (older than specified days)
     * @param daysToKeep Number of days to keep screenshots
     */
    public static void cleanupOldScreenshots(int daysToKeep) {
        try {
            Path screenshotPath = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(screenshotPath)) {
                return;
            }
            
            long cutoffTime = System.currentTimeMillis() - (daysToKeep * 24L * 60 * 60 * 1000);
            
            Files.list(screenshotPath)
                 .filter(Files::isRegularFile)
                 .filter(path -> path.toString().endsWith(".png"))
                 .forEach(path -> {
                     try {
                         if (Files.getLastModifiedTime(path).toMillis() < cutoffTime) {
                             Files.delete(path);
                             logger.info("Deleted old screenshot: {}", path.getFileName());
                         }
                     } catch (IOException e) {
                         logger.warn("Failed to delete old screenshot {}: {}", path.getFileName(), e.getMessage());
                     }
                 });
                 
        } catch (IOException e) {
            logger.error("Failed to cleanup old screenshots: {}", e.getMessage(), e);
        }
    }
}
