package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Products Page object class
 */
public class ProductsPage extends BasePage {
    
    private static final Logger logger = LoggerFactory.getLogger(ProductsPage.class);
    
    // Page Elements
    private final By pageTitle = By.cssSelector("h1, h2");
    private final By addProductButton = By.cssSelector("button[data-testid='add-product'], .add-btn");
    private final By productsList = By.cssSelector(".products-list, .product-item");
    private final By searchBox = By.cssSelector("input[type='search'], .search-input");
    
    public ProductsPage(WebDriver driver) {
        super(driver);
        logger.info("ProductsPage initialized");
    }
    
    public boolean isPageLoaded() {
        try {
            seleniumUtils.waitForElementVisible(pageTitle);
            logger.info("Products page loaded successfully");
            return true;
        } catch (Exception e) {
            logger.error("Products page not loaded", e);
            return false;
        }
    }
    
    public String getPageTitleText() {
        return seleniumUtils.getText(pageTitle);
    }
    
    public void clickAddProduct() {
        seleniumUtils.click(addProductButton);
    }
    
    public void searchProduct(String productName) {
        seleniumUtils.type(searchBox, productName);
    }
}
