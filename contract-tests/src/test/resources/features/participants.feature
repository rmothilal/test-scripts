Feature: Test participants resource in Mojaloop API

  Scenario: Add a user
    When POST /participants is called for user "12345"
    Then Return code is "200"
    And when user "12345" is looked up, it should exist

  Scenario: Get user details
    Given User "12345" exists in Mojaloop System
    When GET /participants is called for user "12345"
    Then DFSP "dfsp" should be returned in the response