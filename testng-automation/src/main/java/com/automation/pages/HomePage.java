package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Home Page object class
 * Contains elements and actions for the home page
 */
public class HomePage extends BasePage {
    
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);
    
    // Page Elements
    private final By titleElement = By.tagName("title");
    private final By headerElement = By.tagName("h1");
    private final By navigationMenu = By.cssSelector("nav");
    private final By dashboardLink = By.xpath("//button[contains(text(), 'Dashboard')]");
    private final By productsLink = By.xpath("//button[contains(text(), 'Products')]");
    private final By ordersLink = By.xpath("//button[contains(text(), 'Orders')]");
    private final By usersLink = By.xpath("//button[contains(text(), 'Users')]");
    private final By tasksLink = By.xpath("//button[contains(text(), 'Tasks')]");
    
    public HomePage(WebDriver driver) {
        super(driver);
        logger.info("HomePage initialized");
    }
    
    /**
     * Check if home page is loaded
     * @return true if loaded, false otherwise
     */
    public boolean isPageLoaded() {
        try {
            seleniumUtils.waitForElementVisible(headerElement);
            logger.info("Home page loaded successfully");
            return true;
        } catch (Exception e) {
            logger.error("Home page not loaded", e);
            return false;
        }
    }
    
    /**
     * Get page header text
     * @return Header text
     */
    public String getHeaderText() {
        String headerText = seleniumUtils.getText(headerElement);
        logger.info("Header text: {}", headerText);
        return headerText;
    }
    
    /**
     * Check if navigation menu is visible
     * @return true if visible, false otherwise
     */
    public boolean isNavigationMenuVisible() {
        boolean visible = seleniumUtils.isElementDisplayed(navigationMenu);
        logger.info("Navigation menu visible: {}", visible);
        return visible;
    }
    
    /**
     * Click on Dashboard link
     * @return DashboardPage instance
     */
    public DashboardPage clickDashboard() {
        logger.info("Clicking on Dashboard link");
        seleniumUtils.click(dashboardLink);
        return new DashboardPage(driver);
    }
    
    /**
     * Click on Products link
     * @return ProductsPage instance
     */
    public ProductsPage clickProducts() {
        logger.info("Clicking on Products link");
        seleniumUtils.click(productsLink);
        return new ProductsPage(driver);
    }
    
    /**
     * Click on Orders link
     * @return OrdersPage instance
     */
    public OrdersPage clickOrders() {
        logger.info("Clicking on Orders link");
        seleniumUtils.click(ordersLink);
        return new OrdersPage(driver);
    }
    
    /**
     * Click on Users link
     * @return UsersPage instance
     */
    public UsersPage clickUsers() {
        logger.info("Clicking on Users link");
        seleniumUtils.click(usersLink);
        return new UsersPage(driver);
    }
    
    /**
     * Click on Tasks link
     * @return TasksPage instance
     */
    public TasksPage clickTasks() {
        logger.info("Clicking on Tasks link");
        seleniumUtils.click(tasksLink);
        return new TasksPage(driver);
    }
    
    /**
     * Verify all navigation links are present
     * @return true if all links present, false otherwise
     */
    public boolean areAllNavigationLinksPresent() {
        boolean allPresent = seleniumUtils.isElementDisplayed(dashboardLink) &&
                            seleniumUtils.isElementDisplayed(productsLink) &&
                            seleniumUtils.isElementDisplayed(ordersLink) &&
                            seleniumUtils.isElementDisplayed(usersLink) &&
                            seleniumUtils.isElementDisplayed(tasksLink);
        
        logger.info("All navigation links present: {}", allPresent);
        return allPresent;
    }
}
