Feature: This feature is used by payer FSP, to determine in which FSP (payee FSP) the required party (payee) is located.

  Scenario Outline: Lookup Participant information using <type>,<payeeid> for the participant that exist in switch
    Given Payee "<payeeid>" with "<type>" exists in switch under "<payeefsp>"
    When  Payer FSP does a lookup for payee "<payeeid>" and type "<type>" in the switch
    Then Payee FSP information "<payeefsp>" should be returned.

    Examples:
    |  type   |     payeeid     |   payeefsp   |
    | MSISDN  |    1272545117   |  fsp1        |
    | MSISDN  |    1272545111   |  fsp2        |

#  Scenario Outline:  payer FSP can filter the payee FSP results based on currency it supports, if a payee is associated
#  with multiple FSP's with different currencies.
#
#    Given the payee "<Type>" "<ID>" and currency <currency>
#    When the payer FSP requests switch to get payee FSP information
#    Then the payee FSP information "<FSPID>" specific to that currency, should be returned.
#
#    Examples:
#    |   Type  |     ID     | currency |  FSPID   |
#    | MSISDN  | 1272545117 |   USD    | payeefsp |
#    | MSISDN  | 1272545111 |   USD    | payerfsp |
#
#  Scenario: Lookup Participant information using <Type> and <ID> for a participant that does not exist in switch, should return error msg
#      Given the payee "<Type>" and "<ID>"
#      When  the payer FSP sent request to get payee FSP information
#      Then it should return an "<ErrorCode>" and "<ExpectedErrormsg>"
#
#
#  Scenario Outline: Lookup participant information with one of the missing required fields, should return error msg
#        When I send a request with one of the missing required field either "<ID>" or "<Type>"
#        Then  I should get the Error response code is "<ResponseCode>" Error code is "<ErrorCode>" and Expected error message is "<ExpectedErrorMessage>"
#      Examples:
#        |   Type   |     ID      |  ResponseCode  |  ErrorCode | ExpectedErrorMessage  |
#
#  Scenario Outline: Lookup participant info with missing one of the required Headers should return error msg
#  When I send request GET /participants with missing "<Headers>" and required fileds "<Type>" and "<ID>"
#  Then I should get an "<ResponseCode>" with an "<ErrorCode>" and "<ExpectedErrorMsg>"
#  Examples:
#  |   Type   |     ID      |    Headers    |   ResponseCode  |  ErrorCode | ExpectedErrorMsg  |
#
#
#  Scenario: if the destination Fsp does not exist
#
#
#  Scenario:  if the info on participant is private
#
#
#  Scenario Outline: Test GET /participants for invalid required field <Type>, should fail the request
#    When  I send a request to GET /participants with a valid "<ID>" and an invalid Type "<Type>" in the request
#    Then An error should be returned. Expected error code is "<ExpectedErrorCode>"
#    And error description is "<ExpectedErrorDescription>"
#    And Http response code is "<ExpectedResponseCode>"
#    Examples:
#      |     Type   |      ID     | ExpectedResponseCode | ExpectedErrorCode |    ExpectedErrorDescription      |
#      | CreditCard |  2272545111 |         400          |        3101       | Format of parameter is not valid |
#      |   MSI*4SDN |  2272545112 |         400          |        3101       | Format of parameter is not valid |
#
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
