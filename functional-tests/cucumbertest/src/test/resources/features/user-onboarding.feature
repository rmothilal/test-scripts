Feature: User Onboarding
  As a partner DFSP integrating with the Hub
  I want to add/update/delete users by their MSISDN in the hub

  #Refers to story:
  Scenario: Adding new user with MSISDN
    Given user "7855501914" does not exist in central directory
    When user "7855501914" that is in "dfsp1" is added in central directory
    Then response should contain "dfsp1" name

  #Refers to story
  Scenario: Delete an existing user with MSISDN
    Given user "7855501914" exists in central directory
    When user "7855501914" is deleted from central directory
    Then upon further lookup for user "7855501914", the result should be empty