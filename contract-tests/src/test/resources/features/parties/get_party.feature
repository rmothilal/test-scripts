Feature: This feature is used by PayerFSP to findout the details of the required party (payee).

  Scenario Outline: Lookup party information that exists in switch should return party details
    Given the payee "<payeeid>" with details "<firstname>","<middlename>","<lastname>", and "<dob>" exists in switch
    When  the payer FSP does a lookup for payee id "<payeeid>"
    Then the payee details "<firstname>","<middlename>" "<lastname>", and "<dob>" should be returned.
    Examples:
      |  payeeid   |   fspId  |   firstname |   middlename  |   lastname  |  dob
      | 1272545117 | payeefsp |
      | 1272545118 | payeefsp |

 Scenario Outline:  payer FSP can filter the payee FSP results based on currency, if a payee is associated
   Given the payee "<Type>" "<ID>" and currency <currency>
    When the payer FSP requests switch to get payee information by sending "<payeeid>" and "<type>"
    Then the payee information "<firstname>" "<middlename>" "<lastname>" "<fspId>" and "<dob>" specific to that currency, should be returned.
    Examples:
     |   Type  |     ID     | currency |   fspId  | firstname | middlename | lastname |  dob   |
     | MSISDN  | 1272545117 |   USD    | payeefsp |
     | MSISDN  | 1272545111 |   USD    | payerfsp |


  Scenario Outline: Lookup party's information with one of the missing required fields, should return error msg
   When I send a request with one of the missing required field either "<ID>" or "<Type>"
    Then  I should get the Error response code is "<ResponseCode>" Error code is "<ErrorCode>" and Expected error message is "<ExpectedErrorMessage>"
    Examples:
      |   Type   |     ID      |  ResponseCode  |  ErrorCode | ExpectedErrorMessage  |
      |  MSISDN  |  1272545117 |

  Scenario Outline: Lookup party's information that does not exist in switch, should return error msg
    When I send a request with one of the missing required field either "<ID>" or "<Type>"
    Then  I should get the Error response code is "<ResponseCode>" Error code is "<ErrorCode>" and Expected error message is "<ExpectedErrorMessage>"
    Examples:
      |   Type   |     ID      |  ResponseCode  |  ErrorCode | ExpectedErrorMessage  |
      |  MSISDN  |  1272545117 |

 Scenario Outline: Lookup party's info with missing Headers should return error msg
   When I send request GET /parties with missing "<Headers>" with required fileds "<Type>" and "<ID>"
   Then I should get Header error msg with "<ResponseCode>", "<ErrorCode>" and "<ExpectedErrorMsg>"
   Examples:
    |   Type   |     ID      |    Headers    |   ResponseCode  |  ErrorCode | ExpectedErrorMsg  |
    |  MSISDN  |  1272545117 |

   Scenario: Lookup parties info with invalid Accept headers, should return error msg
     When I send request GET /parties with invalid "version 10" in Accept headers
     Then I should get invalid header version error msg with response code "406" with error message "Unsupported version in Accept headers"

  Scenario Outline: Lookup parties info with invalid Date headers, should return error msg
    When I send request GET /parties with invalid "<Date>" in headers
    Then I should get invalid Date error msg with response code "<Responsecode>" errorcode "<Errorcode>" and errormsg as "<Errormessage>"
    Examples:
    |   Type   |     ID       |     Date     |  Responsecode |   Errorcode   |          Errormessage           |
    |  MSISDN  |  1272545117  |   03192010   |     400       |    3101       | Invalid Date headers in request |
    |  MSISDN  |  1272545117  |   abcdefgh   |     400       |    3101       | Invalid Date headers in request |
    |  MSISDN  |  1272545117  |   00000000   |     400       |    3101       | Invalid Date headers in request |
#
#  Scenario: if the destination Fsp does not exist
#
#
#  Scenario:  if the info on participant is private (Need more clarification from Dev team)
#
#
 Scenario Outline: Test GET /parties for invalid required field <Type>, should fail the request
   When  I send a request to GET /parties with a valid "<ID>" and an invalid Type "<Type>" in the request
   Then An error should be returned for invalid Type. Expected error code is "<ExpectedErrorCode>"
   And error description for invalid Type is "<ExpectedErrorDescription>"
   And Http response code for invalid Type is "<ExpectedResponseCode>"
   Examples:
#      |     Type   |      ID     | ExpectedResponseCode | ExpectedErrorCode |    ExpectedErrorDescription      |
#      | CreditCard |  2272545111 |         400          |        3101       | Format of parameter is not valid |
#      |   MSI*4SDN |  2272545112 |         400          |        3101       | Format of parameter is not valid |
#
#
#
 Scenario Outline: Test GET /parties for invalid required field <ID>, should fail the request
   When I send a request to GET /parties with a valid "<Type>" invalid ID "<ID>" in the request
   Then An error should be returned for invalid ID. Expected error code is "<ExpectedErrorCode>"
   And error description for invalid ID is "<ExpectedErrorDescription>"
   And Http response code for invalid ID is "<ExpectedResponseCode>"
    Examples:
     |     Type   |      ID     | ExpectedResponseCode | ExpectedErrorCode |    ExpectedErrorDescription      |
     |   MSISDN   | 01272545111 |         400          |        3101       | Format of parameter is not valid |
     |   MSISDN   |   12725511  |         400          |        3101       | Format of parameter is not valid |
     |   MSISDN   |  227254ab11 |         400          |        3101       | Format of parameter is not valid |
     |   MSISDN   |   @27254/17 |         400          |        3101       | Format of parameter is not valid |
#
#
#
