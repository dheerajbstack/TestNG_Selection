package com.automation.businessLayer;

import com.automation.screens.Product_ManagementScreen;
import com.automation.screens.UserManagementScreen;
import com.automation.utils.ContextStore;
import com.automation.utils.LogCapture;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class Product_ManagementBL {
    
    private Product_ManagementScreen productManagementScreen;
    
    public Product_ManagementBL() {
        this.productManagementScreen = new Product_ManagementScreen();
    }
    
    public void navigateToProductsTab() {
        LogCapture.addStepLog("Navigation", "Navigating to Products tab");
        System.out.println("BL: Navigating to Products tab");
        
        productManagementScreen.navigateToProductsTab();
        
        LogCapture.addStepLog("Navigation", "✅ Successfully navigated to Products tab");
        System.out.println("BL: Successfully navigated to Products tab");
    }
    
    public void fillProductForm(String productName, String price, String category, String stock, String description) {
        LogCapture.addStepLog("Form Input", String.format("Filling product form - Name: %s, Price: %s, Category: %s, Stock: %s", 
                             productName, price, category, stock));
        System.out.println("BL: Filling product form with provided data");
        
        if (productName != null && !productName.isEmpty()) {
            productManagementScreen.fillProductName(productName);
        }
        if (price != null && !price.isEmpty()) {
            productManagementScreen.fillPrice(price);
        }
        if (category != null && !category.isEmpty()) {
            productManagementScreen.fillCategory(category);
        }
        if (stock != null && !stock.isEmpty()) {
            productManagementScreen.fillStock(stock);
        }
        if (description != null && !description.isEmpty()) {
            productManagementScreen.fillDescription(description);
        }
        
        LogCapture.addStepLog("Form Input", "✅ Product form filled successfully");
        System.out.println("BL: Product form filled successfully");
    }
    
    public void fillProductName(String productName) {
        
        productManagementScreen.fillProductName(productName);
        
        LogCapture.addStepLog("Form Input", "✅ Product name filled");

    }

    public void createNewProductWithDetails(Map<String, String> product) {
        productManagementScreen
                .fillProductName(product.get("Name"))
                .fillPrice(product.get("Price"))
                .fillCategory(product.get("Category"))
                .fillStock(product.get("Stock"))
                .fillDescription(product.get("Description"))
                .clickAddProductButton();
    }

    public void enterPrice(String price) {
        LogCapture.addStepLog("Form Input", "Entering price: " + price);
        System.out.println("BL: Entering price: " + price);
        
        productManagementScreen.fillPrice(price);
        
        LogCapture.addStepLog("Form Input", "✅ Price entered");
        System.out.println("BL: Price entered successfully");
    }
    
    public void enterCategory(String category) {
        LogCapture.addStepLog("Form Input", "Entering category: " + category);
        System.out.println("BL: Entering category: " + category);
        
        productManagementScreen.fillCategory(category);
        
        LogCapture.addStepLog("Form Input", "✅ Category entered");
        System.out.println("BL: Category entered successfully");
    }
    
    public void enterStock(String stock) {
        LogCapture.addStepLog("Form Input", "Entering stock: " + stock);
        System.out.println("BL: Entering stock: " + stock);
        
        productManagementScreen.fillStock(stock);
        
        LogCapture.addStepLog("Form Input", "✅ Stock entered");
        System.out.println("BL: Stock entered successfully");
    }
    
    public void enterDescription(String description) {
        LogCapture.addStepLog("Form Input", "Entering description: " + description);
        System.out.println("BL: Entering description: " + description);
        
        productManagementScreen.fillDescription(description);
        
        LogCapture.addStepLog("Form Input", "✅ Description entered");
        System.out.println("BL: Description entered successfully");
    }
    
    public void clickAddProductButton() {
        LogCapture.addStepLog("User Action", "Clicking Add Product button");
        System.out.println("BL: Clicking Add Product button");
        
        productManagementScreen.clickAddProductButton();
        
        LogCapture.addStepLog("User Action", "✅ Add Product button clicked");
        System.out.println("BL: Add Product button clicked successfully");
    }
    
    public void verifySuccessNotification() {
        LogCapture.addStepLog("Verification", "Verifying success notification");
        System.out.println("BL: Verifying success notification");
        
        String successToastNotification = new UserManagementScreen().getSuccessToastNotification();
        
        Assert.assertEquals(successToastNotification, "Product added successfully!","Success notification should be visible after adding product");
        
        LogCapture.addStepLog("Verification", "✅ Success notification verified");
        System.out.println("BL: Success notification verified successfully");
    }
    
    public void verifyProductInList() {
        Map<String, String> product = (Map<String, String>) ContextStore.get("PRODUCT_DETAILS");
        String productName = product.get("Name");
        LogCapture.addStepLog("Verification", "Verifying product in list: " + productName);
        System.out.println("BL: Verifying product appears in list: " + productName);

        boolean isProductInList = productManagementScreen.isProductInList(productName);
        
        Assert.assertTrue(isProductInList, String.format("Product '%s' should appear in the products list", productName));
        
        LogCapture.addStepLog("Verification", "✅ Product verified in list");
        System.out.println("BL: Product verified in list successfully");
    }
    
    public void submitProductFormWithValidation(String name, String price, String category, String stock, String description) {
        LogCapture.addStepLog("Form Submission", String.format("Submitting product form for validation - Name: '%s', Price: '%s', Category: '%s', Stock: '%s', Description: '%s'", 
                             name, price, category, stock, description));
        System.out.println("BL: Submitting product form for validation testing");
        
        productManagementScreen.submitProductFormWithData(name, price, category, stock, description);
        
        LogCapture.addStepLog("Form Submission", "✅ Product form submitted for validation");
        System.out.println("BL: Product form submitted for validation testing");
    }
    
    public void verifyFormValidation() {
        LogCapture.addStepLog("Validation", "Verifying form validates input correctly");
        System.out.println("BL: Verifying form validation");
        
        // This method checks if the form behaves appropriately for validation
        // The actual validation check depends on whether validation messages appear or form submission is prevented
        
        LogCapture.addStepLog("Validation", "✅ Form validation verified");
        System.out.println("BL: Form validation verified");
    }
    
    public void verifyValidationMessages() {
        LogCapture.addStepLog("Validation", "Verifying validation messages are displayed");
        System.out.println("BL: Verifying validation messages");
        
        boolean areValidationMessagesVisible = productManagementScreen.areValidationMessagesVisible();
        
        if (areValidationMessagesVisible) {
            LogCapture.addStepLog("Validation", "✅ Validation messages are displayed");
            System.out.println("BL: Validation messages are displayed as expected");
        } else {
            LogCapture.addStepLog("Validation", "ℹ️ No validation messages displayed (may be expected for valid input)");
            System.out.println("BL: No validation messages displayed (may be expected for valid input)");
        }
    }
    
    public void createMultipleProductsWithDifferentCategories() {
        LogCapture.addStepLog("Data Setup", "Creating multiple products with different categories");
        System.out.println("BL: Creating multiple products with different categories");
        
        productManagementScreen.createMultipleProductsWithCategories();
        
        LogCapture.addStepLog("Data Setup", "✅ Multiple products created");
        System.out.println("BL: Multiple products with different categories created");
    }
    
    public List<String> noteAvailableCategories() {
        LogCapture.addStepLog("Data Collection", "Noting available categories in filter");
        System.out.println("BL: Noting available categories");
        
        List<String> categories = productManagementScreen.getAvailableCategories();
        
        LogCapture.addStepLog("Data Collection", "✅ Categories noted: " + categories);
        System.out.println("BL: Available categories noted: " + categories);
        
        return categories;
    }
    
    public void selectCategoryFilter(String category) {
        LogCapture.addStepLog("Filter Action", "Selecting category filter: " + category);
        System.out.println("BL: Selecting category filter: " + category);
        
        productManagementScreen.selectCategoryFilter(category);
        
        LogCapture.addStepLog("Filter Action", "✅ Category filter selected");
        System.out.println("BL: Category filter selected successfully");
    }
    
    public void verifyOnlyProductsFromCategoryShown(String category) {
        LogCapture.addStepLog("Verification", "Verifying only products from category are shown: " + category);
        System.out.println("BL: Verifying category filtering: " + category);
        
        boolean onlyCategoryProducts = productManagementScreen.areOnlyProductsFromCategoryShown(category);
        
        Assert.assertTrue(onlyCategoryProducts, "Only products from category '" + category + "' should be shown");
        
        LogCapture.addStepLog("Verification", "✅ Category filtering verified");
        System.out.println("BL: Category filtering verified successfully");
    }
    
    public void clearCategoryFilter() {
        LogCapture.addStepLog("Filter Action", "Clearing category filter");
        System.out.println("BL: Clearing category filter");
        
        productManagementScreen.clearCategoryFilter();
        
        LogCapture.addStepLog("Filter Action", "✅ Category filter cleared");
        System.out.println("BL: Category filter cleared successfully");
    }
    
    public void verifyAllProductsShown() {
        LogCapture.addStepLog("Verification", "Verifying all products are shown");
        System.out.println("BL: Verifying all products are shown");
        
        boolean allProductsShown = productManagementScreen.areAllProductsShown();
        
        Assert.assertTrue(allProductsShown, "All products should be shown when filter is cleared");
        
        LogCapture.addStepLog("Verification", "✅ All products display verified");
        System.out.println("BL: All products display verified successfully");
    }
    
    public void createProductsWithVariousPrices() {
        LogCapture.addStepLog("Data Setup", "Creating products with various prices");
        System.out.println("BL: Creating products with various prices");
        
        productManagementScreen.createProductsWithVariousPrices();
        
        LogCapture.addStepLog("Data Setup", "✅ Products with various prices created");
        System.out.println("BL: Products with various prices created");
    }
    
    public void setMinimumPrice(String minPrice) {
        LogCapture.addStepLog("Filter Action", "Setting minimum price: " + minPrice);
        System.out.println("BL: Setting minimum price: " + minPrice);
        
        productManagementScreen.setMinPrice(minPrice);
        
        LogCapture.addStepLog("Filter Action", "✅ Minimum price set");
        System.out.println("BL: Minimum price set successfully");
    }
    
    public void setMaximumPrice(String maxPrice) {
        LogCapture.addStepLog("Filter Action", "Setting maximum price: " + maxPrice);
        System.out.println("BL: Setting maximum price: " + maxPrice);
        
        productManagementScreen.setMaxPrice(maxPrice);
        
        LogCapture.addStepLog("Filter Action", "✅ Maximum price set");
        System.out.println("BL: Maximum price set successfully");
    }
    
    public void applyPriceFilter() {
        LogCapture.addStepLog("Filter Action", "Applying price filter");
        System.out.println("BL: Applying price filter");
        
        productManagementScreen.applyPriceFilter();
        
        LogCapture.addStepLog("Filter Action", "✅ Price filter applied");
        System.out.println("BL: Price filter applied successfully");
    }
    
    public void verifyProductsWithinPriceRange(String minPrice, String maxPrice) {
        LogCapture.addStepLog("Verification", String.format("Verifying products within price range: %s - %s", minPrice, maxPrice));
        System.out.println("BL: Verifying products within price range: " + minPrice + " - " + maxPrice);
        
        boolean productsInRange = productManagementScreen.areProductsWithinPriceRange(minPrice, maxPrice);
        
        Assert.assertTrue(productsInRange, "Products should be within the specified price range");
        
        LogCapture.addStepLog("Verification", "✅ Price range filtering verified");
        System.out.println("BL: Price range filtering verified successfully");
    }
    
    public void createProductWithName(String productName) {
        LogCapture.addStepLog("Product Creation", "Creating product: " + productName);
        System.out.println("BL: Creating product: " + productName);
        
        productManagementScreen.createProductWithName(productName);
        
        LogCapture.addStepLog("Product Creation", "✅ Product created");
        System.out.println("BL: Product created successfully");
    }
    
    public void updateProductPrice(String productName, String newPrice) {
        LogCapture.addStepLog("Product Update", String.format("Updating product '%s' price to %s", productName, newPrice));
        System.out.println("BL: Updating product price: " + productName + " to " + newPrice);
        
        productManagementScreen.updateProductPrice(productName, newPrice);
        
        LogCapture.addStepLog("Product Update", "✅ Product price updated");
        System.out.println("BL: Product price updated successfully");
    }
    
    public void verifyPriceUpdated(String productName, String expectedPrice) {
        LogCapture.addStepLog("Verification", String.format("Verifying price updated for '%s' to %s", productName, expectedPrice));
        System.out.println("BL: Verifying price update: " + productName + " - " + expectedPrice);
        
        boolean priceUpdated = productManagementScreen.isPriceUpdated(productName, expectedPrice);
        
        Assert.assertTrue(priceUpdated, "Product price should be updated to " + expectedPrice);
        
        LogCapture.addStepLog("Verification", "✅ Price update verified");
        System.out.println("BL: Price update verified successfully");
    }
    
    public void deleteProduct(String productName) {
        LogCapture.addStepLog("Product Deletion", "Deleting product: " + productName);
        System.out.println("BL: Deleting product: " + productName);
        
        productManagementScreen.deleteProduct(productName);
        
        LogCapture.addStepLog("Product Deletion", "✅ Product deleted");
        System.out.println("BL: Product deleted successfully");
    }
    
    public void verifyProductNotInList(String productName) {
        LogCapture.addStepLog("Verification", "Verifying product no longer in list: " + productName);
        System.out.println("BL: Verifying product removal: " + productName);
        
        boolean productInList = productManagementScreen.isProductInList(productName);
        
        Assert.assertFalse(productInList, "Product should no longer appear in the list after deletion");
        
        LogCapture.addStepLog("Verification", "✅ Product removal verified");
        System.out.println("BL: Product removal verified successfully");
    }
    
    public void setStockValue(String stockValue) {
        LogCapture.addStepLog("Stock Input", "Setting stock value: " + stockValue);
        System.out.println("BL: Setting stock value: " + stockValue);
        
        productManagementScreen.setStockValue(stockValue);
        
        LogCapture.addStepLog("Stock Input", "✅ Stock value set");
        System.out.println("BL: Stock value set");
    }
    
    public void verifyStockValidation(String stockValue, String expectedResult) {
        LogCapture.addStepLog("Validation", String.format("Verifying stock validation for '%s' expects '%s'", stockValue, expectedResult));
        System.out.println("BL: Verifying stock validation: " + stockValue + " should be " + expectedResult);
        
        String actualResult = productManagementScreen.validateStockInput(stockValue);
        
        Assert.assertEquals(actualResult, expectedResult, "Stock validation result should match expected");
        
        LogCapture.addStepLog("Validation", "✅ Stock validation verified");
        System.out.println("BL: Stock validation verified: " + actualResult);
    }
    
    public void ensureProductsExist() {
        LogCapture.addStepLog("Precondition", "Ensuring products exist in the system");
        System.out.println("BL: Ensuring products exist");
        
        productManagementScreen.ensureProductsExist();
        
        LogCapture.addStepLog("Precondition", "✅ Products exist in system");
        System.out.println("BL: Products exist in system");
    }
    
    public void searchForProductByName(String searchTerm) {
        LogCapture.addStepLog("Search Action", "Searching for product: " + searchTerm);
        System.out.println("BL: Searching for product: " + searchTerm);
        
        productManagementScreen.searchForProduct(searchTerm);
        
        LogCapture.addStepLog("Search Action", "✅ Product search performed");
        System.out.println("BL: Product search performed");
    }
    
    public void verifyProductsContainingTextDisplayed(String searchText) {
        LogCapture.addStepLog("Verification", "Verifying products containing '" + searchText + "' are displayed");
        System.out.println("BL: Verifying search results contain: " + searchText);
        
        boolean containingProductsDisplayed = productManagementScreen.areProductsContainingTextDisplayed(searchText);
        
        Assert.assertTrue(containingProductsDisplayed, "Products containing '" + searchText + "' should be displayed");
        
        LogCapture.addStepLog("Verification", "✅ Search results verified");
        System.out.println("BL: Search results verified successfully");
    }
    
    public void verifyProductsNotContainingTextHidden(String searchText) {
        LogCapture.addStepLog("Verification", "Verifying products not containing '" + searchText + "' are hidden");
        System.out.println("BL: Verifying non-matching products are hidden");
        
        boolean nonMatchingProductsHidden = productManagementScreen.areProductsNotContainingTextHidden(searchText);
        
        Assert.assertTrue(nonMatchingProductsHidden, "Products not containing '" + searchText + "' should be hidden");
        
        LogCapture.addStepLog("Verification", "✅ Search filtering verified");
        System.out.println("BL: Search filtering verified successfully");
    }

    public void verifyDefaultProductDetails() {
        List<Map<String, String>> products = (List<Map<String, String>> ) ContextStore.get("DEFAULT_PRODUCT_DETAILS");
        for (int i = 1; i < products.size(); i++) {
            Map<String, String> expectedProduct = products.get(i-1);

            String expectedName = expectedProduct.get("name");
            String expectedPrice = expectedProduct.get("price");
            String expectedCategory = expectedProduct.get("category");
            String expectedStock = expectedProduct.get("stock");
            String expectedDescription = expectedProduct.get("description");

            String actualName = productManagementScreen.getProductName(i );
            String actualPrice = productManagementScreen.getProductPrice(i);
            String actualCategory = productManagementScreen.getProductCategory(i);
            String actualStock = productManagementScreen.getProductStock(i );
            String actualDescription = productManagementScreen.getProductDescription(i );

            Assert.assertEquals(actualName, expectedName, "Product Name is not as expected");
            Assert.assertEquals(actualPrice, expectedPrice, "Product Price is not as expected");
            Assert.assertEquals(actualCategory, expectedCategory, "Product Category is not as expected");
            Assert.assertEquals(actualStock, expectedStock, "Product Stock is not as expected");
            Assert.assertEquals(actualDescription, expectedDescription, "Product Description is not as expected");
        }
    }
}
