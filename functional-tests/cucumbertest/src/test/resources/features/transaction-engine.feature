Feature: Transaction Engine related functionality
  As a DFSP integrating with the hub
  I should be able to issue a quote targeted for the partner DFSP
  so that I can get the partner DFSP fee&commission

  #Refers to story: The sending DFSP directly queries the Receiving DFSP to check a transaction can be received
  Scenario: Sending DFSP issues a Quote request for the receiving DFSP
    Given Receiver "Alice" is resolved to "test-dfsp2" as part of lookup
    When "Bob" in "test-dfsp1" sends a quote to "Alice" in "test-dfsp2"
    Then "test-dfsp2" fees and commission should come back in the response