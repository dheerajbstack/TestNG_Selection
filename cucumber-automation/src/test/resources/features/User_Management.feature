@user-management
Feature: User Management System

  Background:
    Given the application is running on "http://localhost:3000"
    And the backend API is available at "http://localhost:5000"
    And I open the application in browser
    And I navigate to the "Users" tab

  @data-validation
  Scenario: Validate Default User Details (2 TC)
    Given I should see the default user with below details
      | Name        | Email            | Role  |
      | John Doe    | john@example.com | ADMIN |
      | Jane Smith  | jane@example.com | USER  |
      | Bob Johnson | bob@example.com  | USER  |
    And I should be able to delete a 1 user from the users list

  @critical @TC004
  Scenario: TC004 - Add New User Success Path ( 2 TC)
    Given I adds a new user with name "Test User", email "test@example.com", and role "user"
    And I adds a new user with name "Test User", email "test1@example.com", and role "Admin"
    When I try to add a new user with existing email "test@example.com" and role "user"
    Then I should see a duplicate email error message
    When I try to add a new user with existing email "test1@example.com" and role "Admin"
    Then I should see a duplicate email error message



#  @medium @TC007
#  Scenario: TC007 - Users List Refresh
#    Given I Store the current user count
#    And I click "Refresh Users" button
#    Then I should see a loading state if applicable
#    And the users should be reloaded
#    And the user count should be accurate


#  @boundary-testing
#  Scenario Outline: User field boundary testing
#    When I enter "<input>" in the "<field>" field
#    Then the system should handle the input appropriately
#
#    Examples:
#      | field | input                    |
#      | name  | A                       |
#      | name  | Very Long Name That Exceeds Normal Length Limits |
#      | email | a@b.c                   |
#      | email | very.long.email.address@very.long.domain.name.com |
