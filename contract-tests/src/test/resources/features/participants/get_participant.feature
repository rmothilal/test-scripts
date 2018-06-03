Feature: This feature is used by payer FSP, to determine in which FSP (payee FSP) the required party (payee) is located.

  Scenario Outline: Lookup Participant information using type, payeeid
    Given Payee "<payeeid>" with "<type>" exists in switch under "<payeefsp>"
    When  Payer FSP does a lookup for payee "<payeeid>" and type "<type>" in the switch
    Then Payee FSP information "<payeefsp>" should be returned.
    Examples:
    |  type   |     payeeid     |   payeefsp   |
    | MSISDN  |    1272545117   |  fsp1        |
    | MSISDN  |    1272545111   |  fsp2        |

#  Scenario Outline:  payer FSP can filter the payee FSP results based on currency it supports, if a payee is associated
#  with multiple FSP's with different currencies.
#    Given the payee "<Type>" "<ID>" and currency <currency>
#    When the payer FSP requests switch to get payee FSP information
#    Then the payee FSP information "<FSPID>" specific to that currency, should be returned.
#    Examples:
#    |   Type  |     ID     | currency |  FSPID   |
#    | MSISDN  | 1272545117 |   USD    | payeefsp |
#    | MSISDN  | 1272545111 |   USD    | payerfsp |

  Scenario: Doing a lookup on a receiver that does not exist in switch should result in error
    Given Payee "<payeeid>" with "<type>" does not exist in switch
    When  Payer FSP does a lookup for non existing payee "<payeeid>" and type "<type>" in the switch
    Then error should be returned. Expected values are "<errorcode>" and "<errorDescription>"
    Examples:
    |  type   |     payeeid     |   errorCode   |               errorDescription                                       |
    | MSISDN  |    1272545118   |      3204     |  Party with the provided Identifier, Identifier type was not found   |
    | MSISDN  |    1272545112   |      3204     |  Party with the provided Identifier, Identifier type was not found   |

  Scenario: Missing required Headers should fail the request
  When I send GET /participants request with missing header "FSPIOP-Source"
  Then I should get an error with error code "3102" and the error message containing missing header "FSPIOP-Source"

#
#
#  Scenario: if the destination Fsp does not exist
#
#
#  Scenario:  if the info on participant is private


  Scenario Outline: Sending invalid value for Type, should fail the request
    When  I send a request to GET /participants with a valid "<ID>" and an invalid Type "<Type>" in the request
    Then Return Http response code for invald type is "<ExpectedResponseCode>"
    And Error code for invalid type is "<ExpectedErrorCode>"
    And Error description for invalid type is "<ExpectedErrorDescription>"
    Examples:
      |     Type   |      ID     | ExpectedResponseCode | ExpectedErrorCode |    ExpectedErrorDescription         |
      | CreditCard |  2272545111 |         400          |        3101       | Format of parameter Type is invalid |
      |   MSI*4SDN |  2272545112 |         400          |        3101       | Format of parameter Type is invalid |

#
#
#  Scenario Outline: Test GET /participants for invalid required field <ID>, should fail the request
#    When I send a request to GET /participants with a valid "<Type>" invalid ID "<ID>" in the request
#    Then An error should be returned. Expected error code is "<ExpectedErrorCode>"
#    And error description is "<ExpectedErrorDescription>"
#    And Http response code is "<ExpectedResponseCode>"
#    Examples:
#      |     Type   |      ID     | ExpectedResponseCode | ExpectedErrorCode |    ExpectedErrorDescription      |
#      |   MSISDN   | 01272545111 |         400          |        3101       | Format of parameter is not valid |
#      |   MSISDN   |   12725511  |         400          |        3101       | Format of parameter is not valid |
#      |   MSISDN   |  227254ab11 |         400          |        3101       | Format of parameter is not valid |
#      |   MSISDN   |   @27254/17 |         400          |        3101       | Format of parameter is not valid |
#
#
#
#
#
