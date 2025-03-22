@Step2
Feature: User Registration and Login

  Scenario: Register and Login with Valid Data
    Given User is on registration page
    When User enters valid registration details from Excel
    Then Registration should be successful
    Given User is on login page
    When User enters invalid login credentials from Excel
    Then Login should fail
    
    
    

  Scenario: Login with Valid Data
   Given User is on login page
    When User enters valid login credentials from Excel
    Then User should be logged in successfully
