@navigation @dashboard
Feature: Navigation and Dashboard Functionality

  Background:
    Given the application is running on "http://localhost:3000"
    And the backend API is available at "http://localhost:5000"
    And I open the application in browser

  @critical @TC001
  Scenario: Application Loading and Navigation (1 TC)
    Given the page title should be "Enhanced Full-Stack React App"
    And I should see navigation tabs: "Dashboard, Users, Products, Tasks, Orders, Search, Files & Themes"
    When I click on each navigation tab, the active state and content should update accordingly

  @high @TC002
  Scenario: Dashboard Health Status Display (1 TC)
    Given I navigate to the "Dashboard" tab
    When I view the "Backend Status" section
    Then I should see the health status as "OK"
    And I should see the uptime displayed in seconds

  @high @TC003
  Scenario: Analytics Dashboard Functionality (1 TC)
    Given I scroll to the Analytics Dashboard section
    Then I should see User analytics with total count and admin count
    And I should see Products analytics with total, categories, and total value
    And I should see Tasks analytics with total, completed, pending, and high priority counts
    And I should see Orders analytics if orders exist