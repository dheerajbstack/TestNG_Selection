@file-upload @theme-management
Feature: File Upload and Theme Management

  Background:
    Given I open the application in browser
    And I navigate to the "Files & Themes" tab

  @medium @TC021
  Scenario Outline: TC021 - Theme Selection and Application
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

  @background-customization @tc22
  Scenario: Custom background with uploaded images
    When I click on "Image Background" option
    And I upload multiple image files
    Then the uploaded images should be displayed in the gallery
    When I set one image as background
    Then that image should become the page background
    When I change to a different image background
    Then the background should update to the new image
    And the previous background should no longer be active