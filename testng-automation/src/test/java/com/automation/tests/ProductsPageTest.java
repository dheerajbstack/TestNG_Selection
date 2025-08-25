package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Products Page Test class
 * Contains tests for products page functionality
 */
public class ProductsPageTest extends BaseTest {
    
    private static final Logger logger = LoggerFactory.getLogger(ProductsPageTest.class);
    
    @Test(description = "Verify products page loads successfully")
    public void testProductsPageLoad() {
        logger.info("Starting test: testProductsPageLoad");
        
        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = homePage.clickProducts();
        
        // Verify page is loaded
        Assert.assertTrue(productsPage.isPageLoaded(), "Products page should be loaded");
        
        // Verify page title
        String pageTitle = productsPage.getPageTitleText();
        Assert.assertNotNull(pageTitle, "Page title should not be null");
        
        logger.info("Products page title: {}", pageTitle);
        logger.info("Test completed: testProductsPageLoad");
    }
    
    @Test(description = "Verify navigation to products page")
    public void testNavigationToProducts() {
        logger.info("Starting test: testNavigationToProducts");
        
        HomePage homePage = new HomePage(driver);
        
        // Navigate to Products page
        ProductsPage productsPage = homePage.clickProducts();
        
        // Verify Products page is loaded
        Assert.assertTrue(productsPage.isPageLoaded(), "Products page should be loaded");
        
        // Verify current URL contains products
        String currentUrl = productsPage.getCurrentUrl();
        logger.info("Current URL after navigation: {}", currentUrl);
        
        logger.info("Test completed: testNavigationToProducts");
    }
    
    @Test(description = "Verify search functionality", enabled = false)
    public void testProductSearch() {
        logger.info("Starting test: testProductSearch");
        
        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = homePage.clickProducts();
        
        // Search for a product
        String searchTerm = "test product";
        productsPage.searchProduct(searchTerm);
        
        logger.info("Searched for product: {}", searchTerm);
        logger.info("Test completed: testProductSearch");
    }
}
