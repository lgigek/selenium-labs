@access
Feature: Access web pages
  Access web pages and verify if the page is displayed correctly

  @google
  Scenario: Access Google home page
    When The user navigates to Google home page
    Then Google home page is displayed

  @facebook
  Scenario: Access Facebook home page
    When The user navigates to Facebook home page
    Then Facebook home page is displayed
