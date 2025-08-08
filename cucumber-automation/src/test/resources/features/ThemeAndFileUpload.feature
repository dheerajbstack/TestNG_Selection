@file-upload @theme-management
Feature: File Upload and Theme Management

  Background:
    Given I open the application in browser
    And I navigate to the "Files & Themes" tab

  @medium
  Scenario: Default Dark Theme Selection and Application
    When I select theme "Default Dark" and validate background changes immediately
    When I switch to another tab and return
    Then the "Default Dark" theme should persist across navigation

  @medium
  Scenario: Light Mode Theme Selection and Application
    When I select theme "Light Mode" and validate background changes immediately
    When I switch to another tab and return
    Then the "Light Mode" theme should persist across navigation

  @medium
  Scenario: Ocean Blue Theme Selection and Application
    When I select theme "Ocean Blue" and validate background changes immediately
    When I switch to another tab and return
    Then the "Ocean Blue" theme should persist across navigation

  @medium
  Scenario: Forest Green Theme Selection and Application
    When I select theme "Forest Green" and validate background changes immediately
    When I switch to another tab and return
    Then the "Forest Green" theme should persist across navigation

  @medium
  Scenario: Royal Purple Theme Selection and Application
    When I select theme "Royal Purple" and validate background changes immediately
    When I switch to another tab and return
    Then the "Royal Purple" theme should persist across navigation

 @medium @TC021
  Scenario: Sunset Gradient Theme Selection and Application
    When I select theme "Sunset Gradient" and validate background changes immediately
    When I switch to another tab and return
    Then the "Sunset Gradient" theme should persist across navigation

  @medium @TC022
  Scenario Outline: Theme Selection and Application in all
    When I select theme "<theme_name>" and validate background changes immediately
    When I switch to another tab and return
    Then the "<theme_name>" theme should persist across navigation
    Examples:
      | theme_name       |
      | Default Dark     |
      | Light Mode       |
      | Ocean Blue       |
      | Forest Green     |
      | Royal Purple     |
      | Sunset Gradient  |
      | Image Background |

  @background-customization @tc23
  Scenario: Custom background with uploaded images
    When I select theme "Image Background" and validate background changes immediately
    And I upload multiple image files
    Then the uploaded images should be displayed in the gallery
    When I set one image as background
    Then that image should become the page background
    When I change to a different image background
    Then the background should update to the new image
    And the previous background should no longer be active