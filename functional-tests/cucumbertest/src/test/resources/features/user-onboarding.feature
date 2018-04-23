Feature: User Onboarding
  As a partner DFSP integrating with the Hub
  I want to add/update/delete users by their MSISDN in the hub

  #Refers to story: lookup request for an MSISDN/DFSP pairing returns the DFSP URL
  Scenario: Adding new user with MSISDN
    Given user "7855501914" does not exist in central directory
    When user "7855501914" that is in "dfsp1" is added in central directory
    Then upon lookup user "7855501914" response should contain "dfsp1" name

  #Refers to story: remove an msisdn-dfsp link
  Scenario: Delete an existing user with MSISDN
    Given user "7855501914" exists in central directory
    When user "7855501914" is deleted from central directory
    Then upon further lookup for user "7855501914", the result should be empty
#
#  Scenario: Update an existing user with MSISDN
#    Given user "7855501914" exists in central directory
#    When user "7855501914" dfsp is updated "dfsp1" to "dfsp2"
#    Then upon further lookup for user "7855501914", "dfsp2" should be returned
#
#  Scenario: Able to update primary dfsp for a user
#    Given user "7855501914" exists in central directory whose dfsp is not primary
#    When user "7855501914" dfsp is set to primary
#    Then upon further lookup for user "7855501914", dfsp should be set to default