@Step4
Feature: Product Details Page Validation

  Scenario: Validate Product Title, Description, Price, and Images
    Given User is on the product details page
    Then Product title, description, price, images should be displayed correctly

  Scenario: Add Product to Wishlist
    Given User is on the product details page
    When User clicks on "Add to Wishlist"
    Then Wishlist success message should be displayed

  Scenario: Add Product to Cart
    Given User is on the product details page
    When User clicks on "Add to Cart"
    Then The product should be added to the cart
