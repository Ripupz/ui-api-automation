@web
Feature: Web UI - DemoBlaze website

  Background:
    Given I open the homepage

  Scenario: Verify homepage title
    When I open the homepage
    Then the page title should contain "STORE"

  Scenario: Add product to cart
    When I add the first product to cart
    Then the cart should contain at least 1 item
