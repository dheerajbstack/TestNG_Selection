Feature: Simple Web Test

  @web
  Scenario: Open application and verify title
    Given I open the web application
    When I check the page title
    Then I should see the title "Enhanced Full-Stack React App"
    
  @api
  Scenario: API test example
    Given I open the web application
    When I check the page title
    Then I should see the title "Enhanced Full-Stack React App"
    
  @web
  Scenario: Web application navigation test
    Given I open the web application
    When I check the page title
    Then I should see the title "Enhanced Full-Stack React App"
    
  @api
  Scenario: Another API validation test
    Given I open the web application
    When I check the page title
    Then I should see the title "Enhanced Full-Stack React App"