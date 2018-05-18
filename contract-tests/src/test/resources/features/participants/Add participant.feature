Feature: Test participant endpoint for adding a participant to the switch.


  Scenario Outline: Test POST /participants for participant type MSISDN.
    When  I send a request to POST /participants with  "<MSISDN>" and  "<FspID>" with  "<currency>"
    Then the participant information should be added in the switch. Expected FspID in the response is "<ExpectedFspID>"

    Examples:
    |  MSISDN   |    FspID    |    currency    |    ExpectedFspID        |



  Scenario Outline: Test POST /participants for participant type  without passing in optional currency should not fail the request
    When  I send a request to POST /participants with "<participant_type>" and FspID "<FspID>" and do not pass currency in the request
    Then the participant information should be added in the switch. Expected FspID in the response is "<ExpectedFspID>"
    Examples:
      |  participant_type   |    FspID    |    currency    |    ExpectedFspID        |


  Scenario Outline: Test POST /participants for participant type without passing in required fspID should fail the request
    When  I send a request to POST /participants with "<participant_type>" and do not pass FspID in the request
    Then An error should be returned. Expected error code is "<ExpectedErrorCode>" and error description is "<ExpectedErrorDescription>"
    Examples:
      |  participant_type   |    ExpectedErrorCode        |    ExpectedErrorDescription    |

  Scenario Outline: Test POST /participants without passing participant type should fail the request
    When  I send a request to POST /participants for "<MSISDN>"  in "<FspId>"in the request and do not pass participants type
    Then An error should be returned. Expected error code is "<ExpectedErrorCode>" and error description is "<ExpectedErrorDescription>"
    Examples:
      |   MSISDN   |   FspId        |     ExpectedErrorCode        |    ExpectedErrorDescription    |


  Scenario Outline: Test POST /participants for missing required field (participant ID), should fail the request
    When  I send a request to POST /participants with FspID  "<FspID>" and Type "<Type>" in the request and do not pass participants ID
    Then An error should be returned. Expected error code is "<ExpectedErrorCode>" and error description is "<ExpectedErrorDescription>"
    Examples:
      |      FspID    |    Type       |   ExpectedErrorCode        |    ExpectedErrorDescription    |


  Scenario Outline: Test POST /participants for invalid required field participant ID, should fail the request
    When  I send a request to POST /participants with FspID  "<FspID>" and invalid ID "<ID>" in the request
    Then An error should be returned. Expected error code is "<ExpectedErrorCode>" and error description is "<ExpectedErrorDescription>"
    Examples:
      |     FspID      |          ID           |     ExpectedErrorCode        |    ExpectedErrorDescription    |




