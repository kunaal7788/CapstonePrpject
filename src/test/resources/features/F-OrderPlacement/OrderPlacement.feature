Feature: Order Placement

  Scenario: Complete an order successfully
    Given User is on the home page to add items
    When User adds a product to the cart
    Then Product should be added to the cart
    When User updates product quantity to 3
    Then Product quantity should be updated
    When User enters shipping details and gets quotes
    When User proceeds to checkout
    When User enters billing details
    When User selects delivery details
    When User selects delivery method
    When User selects payment method and confirms order
    Then Order should be placed successfully
