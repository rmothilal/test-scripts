Feature: This feature is used by payer FSP, to findout in which FSP (payee FSP) the required party (payee) is located.

  Scenario Outline: Lookup Participant information using <Type> and <ID> for the participant exist in ALS
    Given the payee <Type> <ID>
    When  the payer FSP sent request to get payee FSP information
    Then the payee FSP information <FSPID> should be returned.

    Examples:
    |     Type    |     ID    |     FSPID     |

  Scenario Outline:  payer FSP can filter the payee FSP results based on currency it supports, if a payee is associated
  with many FSP's with different currencices.

    Given the payee <Type> <ID> and currency <currency>
    When the payer FSP sent request to get payee FSP information
    Then the payee FSP information <FSPID> specific to that currency, should be returned.

    Examples:
    |    Type  |   ID    | currency |    FSPID   |


    Scenario: Lookup Participant information using <Type> and <ID> for a participant does not exist in ALS
      Given the payee <Type> <ID>
      When  the payer FSP sent request to get payee FSP information
      Then it should return an <Error msg>
    Examples:
    |     Type    |     ID    |   Error msg      |

