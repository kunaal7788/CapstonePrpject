@Step1
Feature: Home Page Verification

  Scenario: Verify homepage sections
    Given I open the homepage
    Then I should see the Top Offers section
    And I should see the Recommended for You section
    And The search bar should be visible