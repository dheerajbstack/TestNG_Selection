@product-management
Feature: Product Management System

  Background:
    Given the application is running on "http://localhost:3000"
    And the backend API is available at "http://localhost:5000"
    And I open the application in browser
    And I navigate to the "Products" tab

  @data-validation
  Scenario: Validate Default Product Details (1 TC)
    Given I should see the default product with below details
      | id | name         | price   | category    | stock | description                         |
      | 1  | Laptop       | $999.99 | Electronics | 50    | High-performance laptop             |
      | 2  | Smartphone   | $699.99 | Electronics | 100   | Latest smartphone model             |
      | 3  | Headphones   | $199.99 | Electronics | 75    | Wireless noise-canceling headphones |
      | 4  | Coffee Maker | $89.99  | Home        | 30    | Automatic coffee maker              |
      | 5  | Book         | $14.99  | Education   | 200   | Programming fundamentals book       |

  @critical @TC008
  Scenario: Add New Product Success Path(1 TC)
    When I add a product with details:
      | Name         | Price | Category      | Stock | Description              |
      | Test Product | 99.99 | Test Category | 10    | Test product description |
    Then I should see a success notification
    And the product should appear in the products list