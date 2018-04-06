@Test
Feature: test
  As a Business User
  I want Test Central Directory
  So that I meet test business reason

  Scenario: Test for Mojaloop
    Given "user" exists in central directory
    When I do a lookup for that "user"
    Then "user" "dfsp" is returned