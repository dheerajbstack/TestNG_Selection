package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Users Page object class
 */
public class UsersPage extends BasePage {
    
    private static final Logger logger = LoggerFactory.getLogger(UsersPage.class);
    
    private final By pageTitle = By.cssSelector("h1, h2");
    private final By usersTable = By.cssSelector("table, .users-table");
    
    public UsersPage(WebDriver driver) {
        super(driver);
        logger.info("UsersPage initialized");
    }
    
    public boolean isPageLoaded() {
        try {
            seleniumUtils.waitForElementVisible(pageTitle);
            logger.info("Users page loaded successfully");
            return true;
        } catch (Exception e) {
            logger.error("Users page not loaded", e);
            return false;
        }
    }
    
    public String getPageTitleText() {
        return seleniumUtils.getText(pageTitle);
    }
}
