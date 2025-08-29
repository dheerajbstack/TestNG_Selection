package com.automation.pages;

import com.automation.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;

/**
 * Base Page class that all page objects extend
 * Contains common functionality for all pages
 */
public abstract class BasePage {
    
    protected WebDriver driver;
    protected SeleniumUtils seleniumUtils;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.seleniumUtils = new SeleniumUtils(driver);
    }
    
    /**
     * Get current page title
     * @return Page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Get current page URL
     * @return Page URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    /**
     * Wait for page to load completely
     */
    public void waitForPageLoad() {
        seleniumUtils.waitForPageLoad();
    }
    
    /**
     * Refresh the current page
     */
    public void refreshPage() {
        driver.navigate().refresh();
    }
    
    /**
     * Navigate back in browser history
     */
    public void navigateBack() {
        driver.navigate().back();
    }
    
    /**
     * Navigate forward in browser history
     */
    public void navigateForward() {
        driver.navigate().forward();
    }
}
