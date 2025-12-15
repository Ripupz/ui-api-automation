@api
Feature: DummyAPI - User and Tag Endpoints

  Background:
    Given the API has app-id set

  # ---------------- Positive API Tests ----------------

  Scenario: Get list of tags
    When I request GET /tag
    Then the response status should be 200
    And the response should contain a list of tags

  Scenario: Create, update, and delete user
    Given I have user data with firstName "John", lastName "Doe", and email "JohnDoe@example.com"
    When I create the user
    And the response contains firstName "John"
    Then the response status should be 200
    And I update the user's firstName to "John"
    Then the update response status should be 200
    And the response contains firstName "John"
    When I delete the user
    Then the delete response status should be 200

  Scenario: Get list of users
    When I request GET /user
    Then the response status should be 200
    And the response should contain a list of users

  Scenario: Get user by ID
    Given I have an existing user with firstName "Rini", lastName "Agustina", email "riniAgustina@example.com"
    When I request GET /user/{id}
    Then the response status should be 200
    And the response contains firstName "Rini"

  # ---------------- Negative API Test ----------------

  Scenario: Create user without email
    Given I have user data with firstName "Budi" and lastName "Santoso" but no email
    When I create the user
    Then the create response status should be 400

  Scenario: Get user with invalid ID
    When I request GET /user/invalid-id
    Then the response status should be 400
