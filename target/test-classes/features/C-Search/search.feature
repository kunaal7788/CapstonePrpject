@Step3
Feature: Search Functionality

  Scenario: Verify search functionality
    Given I am on the homepage
    When I search for "MacBook"
    Then I should see search results related to "MacBook"

  Scenario: Filter search results by category
    Given I am on the search page 
    When I Search for "Phone"
    And I filter product by category "Phones & PDAs"
    Then I should see only "Phones" results


  Scenario: Sort search results by rating high to low
    Given I search for "Camera"
    When I sort results by "Rating (Highest)"
    Then I should see products sorted from highest to lowest rating

  Scenario: Sort search results by rating low to high
    Given I search for "Camera"
    When I sort results by "Rating (Lowest)"
    Then I should see products sorted from lowest to highest rating
