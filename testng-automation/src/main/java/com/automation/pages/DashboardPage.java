package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dashboard Page object class
 */
public class DashboardPage extends BasePage {
    
    private static final Logger logger = LoggerFactory.getLogger(DashboardPage.class);
    
    // Page Elements
    private final By pageTitle = By.cssSelector("h1, h2");
    private final By statsCards = By.cssSelector(".stats-card, .card");
    private final By chartContainer = By.cssSelector(".chart-container, .chart");
    
    public DashboardPage(WebDriver driver) {
        super(driver);
        logger.info("DashboardPage initialized");
    }
    
    public boolean isPageLoaded() {
        try {
            seleniumUtils.waitForElementVisible(pageTitle);
            logger.info("Dashboard page loaded successfully");
            return true;
        } catch (Exception e) {
            logger.error("Dashboard page not loaded", e);
            return false;
        }
    }
    
    public String getPageTitleText() {
        return seleniumUtils.getText(pageTitle);
    }
}
