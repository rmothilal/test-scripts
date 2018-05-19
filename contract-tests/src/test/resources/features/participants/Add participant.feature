Feature: Test participant endpoint for adding a participant (POST /participant) to the switch.


  Scenario Outline: Test POST /participants for participant type MSISDN.
    When  I send a request to POST /participants with  "<MSISDN>" and  "<FspID>" with  "<Currency>"
    Then the participant information should be added in the switch. Expected FspID in the response is "<ExpectedFspID>"

    Examples:
    |   MSISDN   |    FspID    |  Currency  |  ExpectedFspID   |
    | 1272545111 |   payerfsp  |    USD     |     payerfsp     |
    | 1272545117 |   payeefsp  |    USD     |     payeefsp     |


  Scenario Outline: Test POST /participants for participant type MSISDN  without passing in optional currency, should not fail the request
    When  I send a request to POST /participants with "<Type>" and FspID "<FspID>" and do not pass "<Currency>" in the request
    Then the participant information should be added in the switch. Expected FspID in the response is "<ExpectedFspID>"

    Examples:
      |    Type    |    FspID    |  Currency  |   ExpectedFspID  |
      | 1272545111 |   payerfsp  |            |     payerfsp     |
      | 1272545117 |   payeefsp  |            |     payeefsp     |

   Scenario Outline: Test POST /participant when try to add a participant that already exist in switch, should throw a message
     Given a participant exists in switch with a "<FspID>"
     When I add the participant with the same "<MSISDN>" and with the same "<FspID>" to the switch
     Then I should get a response "<Message>"

     Examples:
       |   MSISDN   |   FspID   |     Message    |
       | 1272545111 | payerfsp  | User already exists |

  Scenario Outline: Test POST /participants for missing required field FspID, should fail the request
    When  I send a request to POST /participants with "<Type>" and do not pass FspID in the request
    Then An error should be returned. Expected error code is "<ExpectedErrorCode>" and error description is "<ExpectedErrorDescription>"

    Examples:
      |    Type    |    ExpectedErrorCode        |    ExpectedErrorDescription    |
      | 1272545111 |
      | 1272545117 |


  Scenario Outline: Test POST /participants for missing required field Type, should fail the request
    When  I send a request to POST /participants for "<FspID>"in the request and do not pass participants Type
    Then An error should be returned. Expected error code is "<ExpectedErrorCode>" and error description is "<ExpectedErrorDescription>"

    Examples:
      |    FspID     |     ExpectedErrorCode        |    ExpectedErrorDescription    |
      |  payerfsp    |
      |  payeefsp    |


  Scenario Outline: Test POST /participants for invalid required field FspID, should fail the request
    When  I send a request to POST /participants with invalid FspID  "<FspID>" and a valid Type "<Type>" in the requestp
    Then An error should be returned. Expected error code is "<ExpectedErrorCode>" and error description is "<ExpectedErrorDescription>"

    Examples:
      |     FspID    |    Type       |   ExpectedErrorCode        |    ExpectedErrorDescription    |
      |  payer1fsp   |  1272545111   |
      |  payee1fsp   |  1272545117   |


  Scenario Outline: Test POST /participants for invalid required field Type, should fail the request
    When  I send a request to POST /participants with a valid FspID  "<FspID>" and invalid ID "<Type>" in the request
    Then An error should be returned. Expected error code is "<ExpectedErrorCode>" and error description is "<ExpectedErrorDescription>"

    Examples:
      |     FspID      |     Type      |     ExpectedErrorCode        |    ExpectedErrorDescription    |
      |    payerfsp    |  2272545111   |
      |    payeefsp    |  2272545117   |


  Scenario Outline: Validating field lenth for FspID
    Given FspID with more than required field length
        And less than required field length
    When I add a participant with "<MSISDN>" and "<FspID>"
    Then I should get error "<ErrorCode>" and "<ErrorDescription>"

    Examples:
      |   FspID  |    MSISDN     |  ErrorCode |   ErrorDescription  |
      |payer1fsp |   1272545111  |
      | payefs    |  1272545117  |

  Scenario Outline: Validating field length for party MSISDN
    Given MSISDN with more than required field length
      And less than required field length
    When I add a participant with "<MSISDN>" and "<FspID>"
    Then I should get error "<ErrorCode>" and "<ErrorDescription>"
    Examples:
        |   FspID   |    MSISDN     |  ErrorCode |   ErrorDescription  |
        | payerfsp  |   01272545111  |
        | payeefsp  |   127254511    |

  Scenario Outline: Validating Field length and data type for the field "<Currency>"
    Given Currency
    When I send POST /participant request with "<Currency> along with "<FspID>" and "<MSISDN>"
    Then I should see the "<ErrorCode>" and "<ErrorDescription>"
    Examples:
      |   FspID  |   MSISDN     | Currency | ErrorCode |   ErrorDescription  |
      | payerfsp |  1272545111  |    USSD  |
      | payeefsp |  1272545117  |    US    |
      | payerfsp |  1272545111  |    USA   |

    Examples:
      |   FspID  |    MSISDN     |  ErrorCode |   ErrorDescription  |
      |payer1fsp |   1272545111  |
      | payefs    |  1272545117  |
