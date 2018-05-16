Feature: Test participant endpoint in the Mojaloop API.


  Scenario Outline: Test POST /participants for participant type MSISDN.
    When  I send a request to POST /participants with MSISDN "<MSISDN>" and FspID "<FspID>" with Currency "<currency>"
    Then the participant information should be added in the switch. Expected FspID is "<ExpectedFspID>"

    Examples:
    |  MSISDN   |    FspID    |    currency    |    ExpectedFspID        |


  Scenario Outline: Test GET /participants for participant type MSISDN
    Given a party with MSISDN "<MSISDN>" exists in FspID "<FspID>"
    When  I do a participant lookup for MSISDN "<MSISDN>"
    Then the response FspID should be "<ExpectedFspID>"

    Examples:
      |  MSISDN   |    FspID    |    ExpectedFspID        |


  Scenario Outline: Test POST /participants for participant type MSISDN without passing in optional currency should not fail the request
    When  I send a request to POST /participants with MSISDN "<MSISDN>" and FspID "<FspID>" and do not pass currency in the request
    Then the participant information should be added in the switch. Expected FspID is "<ExpectedFspID>"
    Examples:
      |  MSISDN   |    FspID    |    currency    |    ExpectedFspID        |


  Scenario Outline: Test POST /participants for participant type MSISDN without passing in required fspID should fail the request
    When  I send a request to POST /participants with MSISDN "<MSISDN>" and do not pass FspID in the request
    Then An error should be returned. Expected error code is "<ExpectedErrorCode>" and error description is "<ExpectedErrorDescription>"
    Examples:
      |  MSISDN   |    ExpectedErrorCode        |    ExpectedErrorDescripton        |