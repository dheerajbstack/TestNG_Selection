package com.automation.utils;

import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for capturing and attaching logs to Cucumber reports
 * Provides step-by-step log attachments and screenshot functionality
 */
public class LogCapture {
    
    private static final Logger logger = LoggerFactory.getLogger(LogCapture.class);
    private static final String LOG_DIR = "target/test-logs";
    private static final String SCENARIO_LOG_DIR = LOG_DIR + "/scenarios";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    
    private static final ThreadLocal<List<String>> scenarioLogs = new ThreadLocal<>();
    private static final ThreadLocal<Scenario> currentScenario = new ThreadLocal<>();
    private static final ThreadLocal<Integer> stepCounter = new ThreadLocal<>();
    
    /**
     * Start capturing logs for a scenario
     * @param scenario Cucumber scenario
     */
    public static void startLogCapture(Scenario scenario) {
        try {
            createLogDirectories();
            
            scenarioLogs.set(new ArrayList<>());
            currentScenario.set(scenario);
            stepCounter.set(0);
            
            String startLog = String.format("🚀 SCENARIO STARTED: %s [Thread: %s] [%s]", 
                scenario.getName(), 
                Thread.currentThread().getName(),
                LocalDateTime.now().format(TIME_FORMAT));
            
            addStepLog("Scenario Setup", startLog);
            logger.info("Started log capture for scenario: {}", scenario.getName());
            
        } catch (Exception e) {
            logger.error("Failed to start log capture: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Add a step log and immediately attach to Cucumber scenario
     * @param stepName Name of the step
     * @param message Log message
     */
    public static void addStepLog(String stepName, String message) {
        try {
            Scenario scenario = currentScenario.get();
            List<String> logs = scenarioLogs.get();
            Integer counter = stepCounter.get();
            
            if (scenario != null && logs != null && counter != null) {
                counter++;
                stepCounter.set(counter);
                
                String timestamp = LocalDateTime.now().format(TIME_FORMAT);
                String formattedLog = String.format("[%s] Step %d - %s: %s", timestamp, counter, stepName, message);
                
                logs.add(formattedLog);
                scenario.attach(formattedLog.getBytes(), "text/plain", String.format("Step %d - %s", counter, stepName));
                
                logger.debug("Attached step log: {} - {}", stepName, message);
            }
            
        } catch (Exception e) {
            logger.error("Failed to add step log: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Add a formatted step log
     * @param stepName Name of the step
     * @param format Format string
     * @param args Arguments
     */
    public static void addStepLog(String stepName, String format, Object... args) {
        addStepLog(stepName, String.format(format, args));
    }
    
    /**
     * Add multiple logs for a complex step
     * @param stepName Name of the step
     * @param messages Multiple log messages
     */
    public static void addStepLogs(String stepName, String... messages) {
        try {
            Scenario scenario = currentScenario.get();
            if (scenario != null && messages.length > 0) {
                StringBuilder combinedLog = new StringBuilder();
                String timestamp = LocalDateTime.now().format(TIME_FORMAT);
                
                for (int i = 0; i < messages.length; i++) {
                    if (i > 0) combinedLog.append("\n");
                    combinedLog.append(String.format("  • %s", messages[i]));
                }
                
                Integer counter = stepCounter.get();
                if (counter != null) {
                    counter++;
                    stepCounter.set(counter);
                    
                    String formattedLog = String.format("[%s] Step %d - %s:\n%s", 
                        timestamp, counter, stepName, combinedLog.toString());
                    
                    List<String> logs = scenarioLogs.get();
                    if (logs != null) {
                        logs.add(formattedLog);
                    }
                    
                    scenario.attach(formattedLog.getBytes(), "text/plain", String.format("Step %d - %s", counter, stepName));
                }
            }
            
        } catch (Exception e) {
            logger.error("Failed to add step logs: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Attach screenshot to Cucumber report with logging
     * @param screenshot Screenshot as byte array
     * @param screenshotName Name for the screenshot
     */
    public static void attachScreenshot(byte[] screenshot, String screenshotName) {
        try {
            Scenario scenario = currentScenario.get();
            if (scenario != null && screenshot != null) {
                scenario.attach(screenshot, "image/png", screenshotName);
                addStepLog("Screenshot", "📸 Screenshot '%s' attached to report", screenshotName);
                logger.info("Screenshot '{}' attached to scenario: {}", screenshotName, scenario.getName());
            }
        } catch (Exception e) {
            logger.error("Failed to attach screenshot: {}", e.getMessage(), e);
            addStepLog("Screenshot Error", "❌ Failed to attach screenshot: %s", e.getMessage());
        }
    }
    
    /**
     * Take and attach screenshot using WebDriver (convenience method)
     * @param driver WebDriver instance
     * @param screenshotName Name for the screenshot
     */
    public static void takeAndAttachScreenshot(org.openqa.selenium.WebDriver driver, String screenshotName) {
        try {
            if (driver != null) {
                byte[] screenshot = ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
                attachScreenshot(screenshot, screenshotName);
            }
        } catch (Exception e) {
            logger.error("Failed to take and attach screenshot: {}", e.getMessage(), e);
            addStepLog("Screenshot Error", "❌ Failed to take screenshot: %s", e.getMessage());
        }
    }
    
    /**
     * Stop log capture and save final summary
     * @param scenario Cucumber scenario
     */
    public static void stopLogCaptureAndSave(Scenario scenario) {
        try {
            String endLog = String.format("🏁 SCENARIO COMPLETED: %s [Status: %s] [%s]", 
                scenario.getName(), 
                scenario.getStatus(),
                LocalDateTime.now().format(TIME_FORMAT));
            
            addStepLog("Scenario Cleanup", endLog);
            
            List<String> logs = scenarioLogs.get();
            if (logs != null && !logs.isEmpty()) {
                saveLogsToFile(scenario.getName(), logs);
                logger.info("Saved {} log entries for scenario: {}", logs.size(), scenario.getName());
            }
            
        } catch (Exception e) {
            logger.error("Failed to stop log capture and save: {}", e.getMessage(), e);
        } finally {
            scenarioLogs.remove();
            currentScenario.remove();
            stepCounter.remove();
        }
    }
    
    // ===== CONVENIENCE METHODS (ONLY USED ONES) =====
    
    /**
     * Log WebDriver initialization
     */
    public static void logDriverInit() {
        addStepLog("WebDriver Setup", "🚀 Initializing WebDriver using DriverFactory...");
    }
    
    /**
     * Log WebDriver initialization success
     */
    public static void logDriverInitSuccess() {
        addStepLog("WebDriver Setup", "✅ WebDriver initialized successfully");
    }
    
    /**
     * Log WebDriver initialization failure
     */
    public static void logDriverInitFailure(String error) {
        addStepLog("WebDriver Setup", "❌ Failed to initialize WebDriver: %s", error);
    }
    
    /**
     * Log test step execution
     */
    public static void logTestStep(String stepDescription) {
        addStepLog("Test Step", stepDescription);
    }
    
    /**
     * Log test step with details
     */
    public static void logTestStepWithDetails(String stepDescription, String... details) {
        addStepLogs("Test Step - " + stepDescription, details);
    }
    
    /**
     * Log WebDriver cleanup
     */
    public static void logDriverCleanup() {
        addStepLog("WebDriver Cleanup", "🧹 Cleaning up WebDriver...");
    }
    
    /**
     * Log WebDriver cleanup success
     */
    public static void logDriverCleanupSuccess() {
        addStepLog("WebDriver Cleanup", "✅ WebDriver closed successfully");
    }
    
    /**
     * Log screenshot capture
     */
    public static void logScreenshotCapture() {
        addStepLog("Screenshot", "📸 Taking screenshot for test evidence...");
    }
    
    /**
     * Log screenshot success
     */
    public static void logScreenshotSuccess() {
        addStepLog("Screenshot", "✅ Screenshot captured and attached to report");
    }
    
    /**
     * Log error or failure
     */
    public static void logError(String operation, String error) {
        addStepLog("Error - " + operation, "❌ %s", error);
    }
    
    // ===== PRIVATE HELPER METHODS =====
    
    /**
     * Save logs to a file for the scenario
     */
    private static void saveLogsToFile(String scenarioName, List<String> logs) {
        try {
            String sanitizedName = sanitizeFileName(scenarioName);
            String timestamp = LocalDateTime.now().format(DATE_FORMAT);
            String fileName = String.format("%s_%s.log", sanitizedName, timestamp);
            Path filePath = Paths.get(SCENARIO_LOG_DIR, fileName);
            
            StringBuilder logContent = new StringBuilder();
            logContent.append("CUCUMBER SCENARIO STEP-BY-STEP EXECUTION LOGS\n");
            logContent.append("==============================================\n");
            logContent.append("Scenario: ").append(scenarioName).append("\n");
            logContent.append("Generated: ").append(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append("\n\n");
            
            for (String log : logs) {
                logContent.append(log).append("\n");
            }
            
            Files.write(filePath, logContent.toString().getBytes());
            logger.info("Saved scenario logs to: {}", filePath.toAbsolutePath());
            
        } catch (Exception e) {
            logger.error("Failed to save logs to file: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Create necessary log directories
     */
    private static void createLogDirectories() {
        try {
            Files.createDirectories(Paths.get(LOG_DIR));
            Files.createDirectories(Paths.get(SCENARIO_LOG_DIR));
        } catch (Exception e) {
            logger.error("Failed to create log directories: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Sanitize filename for cross-platform compatibility
     */
    private static String sanitizeFileName(String fileName) {
        if (fileName == null) {
            return "unknown";
        }
        return fileName.replaceAll("[^a-zA-Z0-9\\-_]", "_")
                      .replaceAll("_{2,}", "_")
                      .trim();
    }
}
