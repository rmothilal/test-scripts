Feature: Test participant endpoint for adding a participant (POST /participant) to the switch.


  Scenario Outline: Test POST /participants for participant type MSISDN.
    When  I send a request to POST /participants with Type "<Type>" ID "<ID>" and  "<FspID>" with  "<Currency>"
    Then the participant information should be added in the switch. Expected FspID in the response is "<ExpectedFspID>"

    Examples:
      |  Type   |     ID     |    FspID    |  Currency  |  ExpectedFspID   |
      | MSISDN  | 1272545111 |   payerfsp  |    USD     |     payerfsp     |
      | MSISDN  | 1272545117 |   payeefsp  |    USD     |     payeefsp     |


  Scenario Outline: Test POST /participants for participant type MSISDN  without passing in optional currency, should not fail the request
    When  I send a request to POST /participants with Type is "<Type>", ID is "<ID>" and FspID "<FspID>" and do not pass "<Currency>" in the request
    Then the participant information should be added in the switch without currency. Expected FspID in the response is "<ExpectedFspID>"
    Examples:
      |   Type    |    ID    |    FspID    |  Currency  |   ExpectedFspID  |
      |  MSISDN   |1272545111 |   payerfsp  |            |     payerfsp     |
      |  MSISDN   |1272545117 |   payeefsp  |            |     payeefsp     |

  Scenario Outline: Test POST /participant when try to add a participant that already exist in switch, should throw a message
    Given a participant with MSISDN "<ID>" exists in switch with a "<FspID>"
    When I add the participant with the same "<Type>" , "<ID>" and "<FspID>" to the switch
    Then I should get a response "<Message>"

    Examples:
      |   Type   |     ID     |    FspID   |      Message    |
      |  MSISDN  | 1272545111 |  payerfsp  | User already exists |

  Scenario Outline: Test POST /participants for missing any of the required fields, should fail the request
    When  I send a request to POST /participants with "<Type>" and one of these fileds missing "<ID>" "<FspID>" in the request
    Then An error should be returned. Expected error code is "<ExpectedErrorCode>"
    And error description is "<ExpectedErrorDescription>"

    Examples:
      |    Type   |     ID     |   FspID   | ExpectedResponseCode |  ExpectedErrorCode  |             ExpectedErrorDescription            |
      |   MSISDN  |            |  payerfsp |         404          |        3102         | Mandatory eliment in  the data model was missing|
      |   MSISDN  | 1272545117 |           |         404          |        3102         | Mandatory eliment in the datta model was missing|



  Scenario Outline: Test POST /participants for invalid required field FspID, should fail the request
    When  I send a request to POST /participants with an invalid FspID  "<FspID>", a valid Type "<Type>" and "<ID>" in the request
    Then An error should be returned for invalid FspID. Expected error code is "<ExpectedErrorCode>"
    And Error description for invalid FspId is "<ExpectedErrorDescription>"
    And Http Response code for invalid FspId is "<ExpectedResponseCode>"

    Examples:
      |     FspID    |    Type       |     ID      |ExpectedResponseCode |  ExpectedErrorCode  |     ExpectedErrorDescription     |
      |  payer12fsp  |    MSISDN     | 1272545111  |       400           |        3101         | Format of parameter is not valid |
      |  payefsp     |    MSISDN     | 1272545117  |       400           |        3101         | Format of parameter is not valid |
      |   pa?yer*    |    MSISDN     | 1272545112  |       400           |        3101         | Format of parameter is not valid |

  Scenario Outline: Test POST /participants for invalid field Type, should fail the request
    When  I send a request to POST /participants with a valid FspID  "<FspID>" and invalid Type "<Type>" in the request
    Then An error should be returned. Expected error code is "<ExpectedErrorCode>"
    And error description is "<ExpectedErrorDescription>"
    And Http response code is "<ExpectedResponseCode>"

    Examples:
      |     FspID      |     Type     |     ID      | ExpectedResponseCode | ExpectedErrorCode |    ExpectedErrorDescription    |
      |    payerfsp    |  CreditCard  | 2272545111  |           400        |        3101       | Format of parameter is not valid |
      |    payeefsp    |   MSSIS**N   | 2272545117  |           400        |        3101       | Format of parameter is not valid |

  Scenario Outline: Test POST /participants for invalid required field ID, should fail the request
    When  I send a request to POST /participants with a valid FspID "<FspID>" and valid Type "<Type>" invalid ID "<ID>" in the request
    Then An error should be returned. Expected error code is "<ExpectedErrorCode>"
    And error description is "<ExpectedErrorDescription>"
    And Http response code is "<ExpectedResponseCode>"
    Examples:
      |     FspID      |     Type   |      ID     | ExpectedResponseCode | ExpectedErrorCode |    ExpectedErrorDescription      |
      |    payerfsp    |   MSISDN   | 01272545111 |         400          |        3101       | Format of parameter is not valid |
      |    payeefsp    |   MSISDN   |   12725511  |         400          |        3101       | Format of parameter is not valid |
      |    payerfsp    |   MSISDN   |  227254ab11 |         400          |        3101       | Format of parameter is not valid |
      |    payeefsp    |   MSISDN   |   @27254/17 |         400          |        3101       | Format of parameter is not valid |


  Scenario Outline: Validating Field length and data type for the field "<Currency>"
    Given Currency
    When I send POST /participant request with "<Currency> along with "<FspID>","<Type>" and "<ID>"
    Then I should see the "<ErrorCode>"
    And "<ErrorDescription>"
    And Http Response code is "<ExpectedResponseCode>"
    Examples:
      |   FspID  |   MSISDN     | Currency | ExpectedResponseCode | ErrorCode |          ErrorDescription        |
      | payerfsp |  1272545111  |    USSD  |      400             |    3101   | Format of parameter is not valid |
      | payeefsp |  1272545117  |    US    |      400             |    3101   | Format of parameter is not valid |
      | payerfsp |  1272545111  |    USA   |      400             |    3101   | Format of parameter is not valid |


  Scenario: Testing Http Response code 401
    When I submit a request wihtout authorization
    Then I should get response code "401"

    #To do
      #Scenario: Testing Http Response code 401- unauthorized (missing/bad token - need to test when the application is available)
      #Scenario: Testing Http Response code 403 - Forbidden
      #Scenario: Testing Http Response code 501 is not applicable on POST /participants and is being tested as part of 501

  Scenario Outline: Testing Http Response code 405, for PATCH operation on POST /participants, should give an Error msg
    When I send a Http request for the Patch operation with the fields type "<Type>" ID "<ID>" and FspID "<FspID>"
    Then I should get "<ExpectedResponseCode>" with an "<ErrorMsg>"

    Examples:
      |  Type   |     ID     |    FspID    | ExpectedResponseCode |  ErrorMsg  |
      | MSISDN  | 1272545111 |   payerfsp  |          405         |            |
      | MSISDN  | 1272545117 |   payeefsp  |          405         |            |

  Scenario: Testing Http Response code 406, i.e throwing an error response code for unsupported versions in the required Accept header from the client request,ing
    When I send a request with unsupported version 2, in the required Accept header
    Then I should see the Response code  "406" with an Error code of "<3001>" and Error description of "<Client request to use a protocol version which is not supported by the server>"


    # Scenario: Testing Http Response code 503 - need to work with Dev team to test this.

  Scenario: Testing Error code 3003 - Error during adding/updating information regarding a party
    When I send a valid request on POST /participants
    Then I should get an error code of "<3003>" with an error message of "<Error occured while adding party information>"

  Scenario: Testing ErrorCode 3201 - Destination Fsp does not exist or can't be found
    When I send a


  Scenario Outline: Testing Error code for missing the required Headers in Http request
    When I send a request to POST /participants with one of the missing required "<Headers>"
    Then I should get the Response code "<ResponseCode>" with an error code "<ErrorCode>" and Error msg "<ErrorMessage>"

    Examples:
      |   Type   |     ID     |    FspID   |    Headers    | ResponseCode |  ErrorCode   |  ErrorMessage  |
      |  MSISDN  | 1272545111 |  payerfsp  |               |     400      |              |                |