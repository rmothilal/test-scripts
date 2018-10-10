Feature: This feature is used by PayerFSP to findout the details of the required party (payee).

  Scenario Outline: Lookup party information that exists in switch should return party details
    Given the payee "<payeeid>" with details "<firstname>","<lastname>", and "<dob>" exists in switch
    When  the payer FSP does a lookup for payee id "<payeeid>"
    Then the payee details "<firstname>","<lastname>", and "<dob>" should be returned.
    Examples:
      |  payeeid   |   fspId  |   firstname |   lastname  |   dob    |
      | 1272545117 | payeefsp |   Siabelo   |    Maroka   | 3/3/1973 |
      | 1272545118 | payeefsp |   Nanga     |    Makwetla | 4/4/1974 |

  Scenario Outline:  payer FSP can filter the payee FSP results based on currency, if a payee is associated
    Given the payee with "<Type>" "<ID>" exists in the switch under fsp "<fspId>" with 2 different currencies "<currency1>" and "<currency2>"
    When the payer FSP requests switch to get payee information by sending "<payeeid>", "<type>"  and "<currency1>"
    Then the payee information "<firstname>" "<middlename>" "<lastname>" "<fspId>" and "<dob>" specific to that currency, should be returned.
    Examples:
     |   Type  |     ID     |   fspId     | currency1 | currency2 | firstname | lastname |  dob      |
     | MSISDN  | 1272545117 |   payerfsp  |     USD   |   ZAR     |  Siabelo  |  Maroka  |  3/3/1973 |


  Scenario Outline: Lookup party's information with one of the missing required fields, should return error msg
    When I send a request with one of the missing required field either "<ID>" or "<Type>"
    Then  I should get the Error response code "<ExpectedResponseCode>" Error code "<ExpectedErrorCode>" and error message "<ExpectedErrorMessage>"
    Examples:
      |   Type   |     ID      |  ExpectedResponseCode  |  ExpectedErrorCode | ExpectedErrorMessage  |
      |  MSISDN  |  1272545117 |

  Scenario Outline: Lookup party's information that does not exist in switch, should return error msg
    Given the payee with "<Type>" "<ID>" does not exist in the switch
    When I lookup for the payee with "<Type>" and "<ID>"
    Then  I should get the Error response code "<ExpectedResponseCode>" Error code "<ExpectedErrorCode>" and Expected error message "<ExpectedErrorMessage>"
    Examples:
      |   Type   |     ID      |  ExpectedResponseCode  |  ExpectedErrorCode | ExpectedErrorMessage  |
      |  MSISDN  |  1272545113 |

 Scenario Outline: Lookup party's info with missing Headers should return error msg
   When I lookup for a party with "<Type>" and "<ID>" with missing "<Headers>"
   Then I should get a response  with "<ExpectedResponseCode>", "<ExpectedErrorCode>" and "<ExpectedErrorMsg>"
   Examples:
    |   Type   |     ID      |    Headers      |   ExpectedResponseCode  |  ExpectedErrorCode | ExpectedErrorMsg  |
    |  MSISDN  |  1272545117 |     Accept      |
    |  MSISDN  |  1272545117 | FSPIOP-Source   |
    |  MSISDN  |  1272545117 |      Date       |

  Scenario: Lookup parties info with invalid Accept header, should return error msg
     When I lookup for a payee with invalid version "10" in Accept headers
     Then I should get invalid header version error msg with response code "406" and error message "Unsupported version in Accept headers"

  Scenario Outline: Lookup parties info with invalid Date headers, should return error msg
    When I lookup for a payee with invalid "<Date>" in headers
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
 Scenario Outline: Looking for a payee with invalid Type, should fail the request
   When  I lookup for a payee with a valid "<ID>" and an invalid Type "<Type>"
   Then An error should be returned. Expected values are  "<ExpectedResponseCode>" "<ExpectedErrorCode>" "<ExpectedErrorDescription>"
   Examples:
      |     Type   |      ID     | ExpectedResponseCode | ExpectedErrorCode |    ExpectedErrorDescription      |
      | CreditCard |  2272545111 |         400          |        3101       | Format of parameter is not valid |
      |   MSI*4SDN |  2272545112 |         400          |        3101       | Format of parameter is not valid |
#
#
#
 Scenario Outline: Looking up for a payee with invalid MSISDN ID, should fail the request
   When I lookup for a payee with invalid MSISDN "<msisdn>" in the request
   Then An error should be returned with the following values "<ExpectedResponseCode>" "<ExpectedErrorCode>" "<ExpectedErrorDescription>"
    Examples:
     |     Type   |      ID     | ExpectedResponseCode | ExpectedErrorCode |    ExpectedErrorDescription      |
     |   MSISDN   | 01272545111 |         400          |        3101       | Format of parameter is not valid |
     |   MSISDN   |   12725511  |         400          |        3101       | Format of parameter is not valid |
     |   MSISDN   |  227254ab11 |         400          |        3101       | Format of parameter is not valid |
     |   MSISDN   |   @27254/17 |         400          |        3101       | Format of parameter is not valid |
