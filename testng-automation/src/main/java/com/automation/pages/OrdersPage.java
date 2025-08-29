package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Orders Page object class
 */
public class OrdersPage extends BasePage {
    
    private static final Logger logger = LoggerFactory.getLogger(OrdersPage.class);
    
    private final By pageTitle = By.cssSelector("h1, h2");
    private final By ordersTable = By.cssSelector("table, .orders-table");
    
    public OrdersPage(WebDriver driver) {
        super(driver);
        logger.info("OrdersPage initialized");
    }
    
    public boolean isPageLoaded() {
        try {
            seleniumUtils.waitForElementVisible(pageTitle);
            logger.info("Orders page loaded successfully");
            return true;
        } catch (Exception e) {
            logger.error("Orders page not loaded", e);
            return false;
        }
    }
    
    public String getPageTitleText() {
        return seleniumUtils.getText(pageTitle);
    }
}
