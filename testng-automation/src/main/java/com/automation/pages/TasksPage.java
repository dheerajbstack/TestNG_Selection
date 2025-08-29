package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tasks Page object class
 */
public class TasksPage extends BasePage {
    
    private static final Logger logger = LoggerFactory.getLogger(TasksPage.class);
    
    private final By pageTitle = By.cssSelector("h1, h2");
    private final By tasksList = By.cssSelector(".tasks-list, .task-item");
    
    public TasksPage(WebDriver driver) {
        super(driver);
        logger.info("TasksPage initialized");
    }
    
    public boolean isPageLoaded() {
        try {
            seleniumUtils.waitForElementVisible(pageTitle);
            logger.info("Tasks page loaded successfully");
            return true;
        } catch (Exception e) {
            logger.error("Tasks page not loaded", e);
            return false;
        }
    }
    
    public String getPageTitleText() {
        return seleniumUtils.getText(pageTitle);
    }
}
