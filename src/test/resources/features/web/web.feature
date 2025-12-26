@web
Feature: Web UI - DemoBlaze website

  Background:
    Given I open the homepage

  # ================= POSITIVE TESTS =================

  Scenario: Verify homepage title
    Then the page title should contain "STORE"

  Scenario: View product detail page
    When I click the first product
    Then the product detail page should be displayed

  Scenario: Add product to cart
    When I add the first product to cart
    Then the cart should contain at least 1 item

  Scenario: Remove product from cart
    Given I have a product in the cart
    When I remove the product from the cart
    Then the cart should be empty

  # ================= NEGATIVE TEST =================

  Scenario: Attempt checkout without filling order form
    Given I have a product in the cart
    When I proceed to checkout
    And I submit the order without filling the form
    Then an error message should be displayed

  # ================= END TO END TEST =================

  @e2e
  Scenario: Complete purchase successfully
    When I add the first product to cart
    And I proceed to checkout
    And I fill the order form with valid data
    And I confirm the purchase
    Then the purchase should be successful
