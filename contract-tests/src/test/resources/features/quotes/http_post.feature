Feature: The HTTP request POST /quotes is used by payer DFSP to request the creation of a quote for the provided financial transaction in the server.

  Scenario Outline: Sending a quote request to the switch from payerfsp so that the payer will know how much is the fee and commission
  involved to send a specific amount to the payee.
    Given I have all the information to send a quote request
    When I send quote request to the switch by passing quoteId "<quoteId>", transactionId"<transactionId>",payeePartyIdType"<payeePartyIdType>", payeePartyIdentifier "<payeePartyIdentifier>"payerPartyIdType "<payerPartyIdType>", payerPartyIdentifier "<payerPartyIdentifier>", amountType "<amountType>" amount "<amount>", scenario "<scenario>", initiator"<initiator>", initiatorType "<initiatorType>"
    Then I should be getting a quote response with fee and commission
    Examples:
    | quoteId | transactionId | payeePartyIdType | payeePartyIdentifier | payerPartyIdType | payerPartyIdentifier | amountType | amount | scenario | initiator | initiatorType |

  Scenario Outline: Sending a quote request with missing optional fields to the switch from payerfsp should not fail the request. Should generate a quote successfully.
    When I send quote request to the switch by passing all required and optional fields, quoteId "<quoteId>", transactionId"<transactionId>", transactionRequestId "<transactionRequestId>"payeePartyIdType"<payeePartyIdType>", payeePartyIdentifier "<payeePartyIdentifier>" payeePartySubIdOrType "<payeePartySubIdOrType>" payeeFspId "<payeeFspId>" payeeMerchantClassificationCode"<payeeMerchantClassificationCode>" payeeName "<payeeName>" payeeFirstName "<payeeFirstName>" payeeMiddleName "<payeeMiddleName>" payeeLastName "<payeeLastName>" payeeDOB "<payeeDOB>" payerPartyIdType "<payerPartyIdType>", payerPartyIdentifier "<payerPartyIdentifier>", payerPartySubIdOrType "<payerPartySubIdOrType>" payerFspId "<payerFspId>" payerMerchantClassificationCode"<payerMerchantClassificationCode>" payerName "<payerName>" payerFirstName "<payerFirstName>" payerMiddleName "<payerMiddleName>" payerLastName "<payerLastName>" payerDOB "<payerDOB>"amountType "<amountType>" amountcurrency "<currency>" amount "<amount>", feesCurrency "<feeCurrency>", feeAmount"<feeAmount>"scenario "<scenario>", subScenario"<subScenario>" initiator"<initiator>", initiatorType "<initiatorType>"
    Then I should be getting a quote response with fee and commission without errors
    Examples:
      | quoteId | transactionId | transactionRequestId | payeePartyIdType | payeePartyIdentifier | payeePartySubIdOrType | payeeFspId | payeeMerchantClassificationCode | payeeName | payeeFirstName | payeeMiddleName | payeeLastName | payeeDOB | payerPartyIdType | payerPartyIdentifier | payerPartySubIdOrType | payerFspId | payerMerchantClassificationCode | payerName | payerFirstName | payerMiddleName | payerLastName | payerDOB | amountType | currency | amount | feeCurrency | feeAmount |scenario | subScenario | initiator | initiatorType |originalTransactionId | refundReason | balanceOfPayments | latitude | longitude | note | expiration | extensionKey | extensionValue |

  # Testing Error Code 3000, Generic Client Error

  # Testing Error Code 3001, Unacceptable version requested
  Scenario Outline: Sending a quote request with invalid version number should result in a failure
    When I send quote request to the switch with any of the following versions "<QuoteVersion>"
    Then I should be getting a failure response for invalid version with the following values: responsecode"<ExpectedResponseCode>" errorcode "<ExpectedErrorCode>" and errordescription "<ExpectedErrorDescription>"
    Examples:
      |  QuoteVersion | ExpectedResponseCode | ExpectedErrorCode | ExpectedErrorDescription |
      |     5         |      406             |   3001            |  The Client requested an unsupported version, see extension list for supported version(s)  |

  # Testing Error Code 3100, Generic Validation error

  # Testing Error Code 3101, Malformed Syntax
  Scenario Outline: Sending a quote request with invalid syntax in the fields number should result in a failure
    When I send quote request to the switch with any of the following fields having invalid syntax quoteId "<quoteId>", transactionId "<transactionId>", payeePartyIdType "<payeePartyIdType>", payeePartyIdentifier "<payeePartyIdentifier>", payerPartyIdType "<payerPartyIdType>", payerPartyIdentifier "<payerPartyIdentifier>"
    Then I should be getting a failure response for invalid syntax with the following values: responsecode"<ExpectedResponseCode>" errorcode "<ExpectedErrorCode>" and errordescription "<ExpectedErrorDescription>"
    Examples:
      | quoteId | transactionId | payeePartyIdType | payeePartyIdentifier | payerPartyIdType | payerPartyIdentifier | ExpectedResponseCode | ExpectedErrorCode | ExpectedErrorDescription |
      | @123    |  12345        | MSISDN           | 12345                | MSISDN           |  23456               | 400                  |   3101            |                          |
      | 1234    |  @1234        | MSISDN           | 12345                | MSISDN           |  23456               | 400                  |   3101            |                          |
      | 1234    |  12345        | CELLNM           | 12345                | MSISDN           |  23456               | 400                  |   3101            |                          |
      | @123    |  12345        | MSISDN           | @2345                | MSISDN           |  23456               | 400                  |   3101            |                          |
      | @123    |  12345        | MSISDN           | 12345                | CELLNM           |  23456               | 400                  |   3101            |                          |
      | @123    |  12345        | MSISDN           | @2345                | MSISDN           |  @3456               | 400                  |   3101            |                          |

  # Testing Error Code 3102, Missing Mandatory element
  Scenario Outline: Sending a quote request with missing required fields to the switch from payerfsp, should result in failure.
    When I send quote request to the switch with any of the following missing required fileds, quoteId "<quoteId>", transactionId"<transactionId>",payeePartyIdType"<payeePartyIdType>", payeePartyIdentifier "<payeePartyIdentifier>"payerPartyIdType "<payerPartyIdType>", payerPartyIdentifier "<payerPartyIdentifier>", amountType "<amountType>" amount "<amount>", scenario "<scenario>", initiator"<initiator>", initiatorType "<initiatorType>"
    Then I should be getting a missing quote error response with responsecode"<responseCode>" errorcode "<errorCode>" and errordescription "<errorDescription>"
    Examples:
      | quoteId | transactionId | payeePartyIdType | payeePartyIdentifier | payerPartyIdType | payerPartyIdentifier | amountType | amount | scenario | initiator | initiatorType |  responseCode | errorCode | errorDescription |
      |         |    12345      |  MSISDN          |   12345              |   MSISDN         |    5679              |SEND        | 100    |   DEPOSIT|  payer    | CONSUMER      |  400          |           |                  |
      |   12345 |               |  MSISDN          |   12345              |   MSISDN         |    5679              |SEND        | 100    |   DEPOSIT|  payer    | CONSUMER      |  400          |           |                  |
      |   12345 |    12345      |                  |   12345              |   MSISDN         |    5679              |SEND        | 100    |   DEPOSIT|  payer    | CONSUMER      |  400          |           |                  |
      |   12345 |    12345      |  MSISDN          |                      |   MSISDN         |    5679              |SEND        | 100    |   DEPOSIT|  payer    | CONSUMER      |  400          |           |                  |
      |   12345 |    12345      |  MSISDN          |   12345              |                  |    5679              |SEND        | 100    |   DEPOSIT|  payer    | CONSUMER      |  400          |           |                  |
      |   12345 |    12345      |  MSISDN          |   12345              |   MSISDN         |                      |SEND        | 100    |   DEPOSIT|  payer    | CONSUMER      |  400          |           |                  |
      |   12345 |    12345      |  MSISDN          |   12345              |   MSISDN         |    5679              |            | 100    |   DEPOSIT|  payer    | CONSUMER      |  400          |           |                  |
      |   12345 |    12345      |  MSISDN          |   12345              |   MSISDN         |    5679              |SEND        |        |   DEPOSIT|  payer    | CONSUMER      |  400          |           |                  |
      |   12345 |    12345      |  MSISDN          |   12345              |   MSISDN         |    5679              |SEND        | 100    |          |  payer    | CONSUMER      |  400          |           |                  |
      |   12345 |    12345      |  MSISDN          |   12345              |   MSISDN         |    5679              |SEND        | 100    |   DEPOSIT|           | CONSUMER      |  400          |           |                  |
      |   12345 |    12345      |  MSISDN          |   12345              |   MSISDN         |    5679              |SEND        | 100    |   DEPOSIT|  payer    |               |  400          |           |                  |
      |   12345 |    12345      |  MSISDN          |   12345              |   MSISDN         |    5679              |SEND        | 100    |   DEPOSIT|  payer    | CONSUMER      |               |           |                  |

  # Testing Error Code 3103, Too many elements
  Scenario: Sending a quote request with too many array elements in the request should result in a failure
    When I send quote request to the switch with Extension List repeating "17" times when the maximum limit is 16
    Then I should be getting a failure response for too many elements with the following values: responsecode "400" errorcode "3103" and errordescription ""


  # Testing Error Code 3104, Too large payload

  # Testing Error Code 3105, Invalid Signature

  # Testing Error Code 3106, Modified Request
  Scenario Outline: Sending a quote request with modified request to the switch from payerfsp, should result in failure.
    Given A quote exists with the following values I send quote request to the switch with any of the following missing required fileds, quoteId "<quoteId>", transactionId"<transactionId>",payeePartyIdType"<payeePartyIdType>", payeePartyIdentifier "<payeePartyIdentifier>"payerPartyIdType "<payerPartyIdType>", payerPartyIdentifier "<payerPartyIdentifier>", amountType "<amountType>" amount "<amount>", scenario "<scenario>", initiator"<initiator>", initiatorType "<initiatorType>"
    When I send another quote request to the switch with the same quoteId but different payeeId quoteId "<quoteId>", transactionId"<transactionId>",payeePartyIdType"<payeePartyIdType>", SecondPayeePartyIdentifier "<SecondPayeePartyIdentifier>"payerPartyIdType "<payerPartyIdType>", payerPartyIdentifier "<payerPartyIdentifier>", amountType "<amountType>" amount "<amount>", scenario "<scenario>", initiator"<initiator>", initiatorType "<initiatorType>"
    Then I should be getting a missing quote error response with responsecode"<responseCode>" errorcode "<errorCode>" and errordescription "<errorDescription>"
    Examples:
      | quoteId | transactionId | payeePartyIdType | payeePartyIdentifier | SecondPayeePartyIdentifier | payerPartyIdType | payerPartyIdentifier | amountType | amount | scenario | initiator | initiatorType |  responseCode | errorCode | errorDescription |
      |   12345 |    12345      |  MSISDN          |   12345              |     98765                  |   MSISDN         |    5679              |SEND        | 100    |   DEPOSIT|  payer    | CONSUMER      |  400          |      3106 |                  |


  # Testing Error Code 3107, Missing Mandatory extension parameter

  # Testing Error Code 3200, Generic ID error provided by the client

  # Testing Error Code 3201, Destination FSP does not exist or cannot be found
  Scenario Outline: Sending a quote request with a payee fsp that does not exist in the request should result in a failure
    When I send quote request to the switch with payeeFspId that does not exist with the following values: quoteId "<quoteId>", transactionId"<transactionId>",payeePartyIdType"<payeePartyIdType>", payeePartyIdentifier "<payeePartyIdentifier>", payeeFspid "<payeeFspId>", payerPartyIdType "<payerPartyIdType>", payerPartyIdentifier "<payerPartyIdentifier>", payerFspId "<payerFspId>", amountType "<amountType>" amount "<amount>", scenario "<scenario>", initiator"<initiator>", initiatorType "<initiatorType>"
    Then I should be getting a failure response for missing payee fsp with the following values: responsecode"<responseCode>" errorcode "<errorCode>" and errordescription "<errorDescription>"
    Examples:
    Examples:
      | quoteId | transactionId | payeePartyIdType | payeePartyIdentifier | payeeFspId   |  payerPartyIdType | payerPartyIdentifier | payerFspId   |   amountType | amount | scenario | initiator | initiatorType |  responseCode | errorCode | errorDescription |
      |   12345 |    12345      |  MSISDN          |   12345              |   payeefsp   |    MSISDN         |    5679              |   blah       |     SEND     | 100    |   DEPOSIT|  payer    | CONSUMER      |  400          |      3201 |                  |

  # Testing Error Code 3204, Party not found
  Scenario Outline: Sending a quote request with a payee that does not exist in the request should result in a failure
    When I send quote request to the switch with payeeId that does not exist with the following values: quoteId "<quoteId>", transactionId"<transactionId>",payeePartyIdType"<payeePartyIdType>", payeePartyIdentifier "<payeePartyIdentifier>", payeeFspid "<payeeFspId>", payerPartyIdType "<payerPartyIdType>", payerPartyIdentifier "<payerPartyIdentifier>", payerFspId "<payerFspId>", amountType "<amountType>" amount "<amount>", scenario "<scenario>", initiator"<initiator>", initiatorType "<initiatorType>"
    Then I should be getting a failure response for missing payee with the following values: responsecode"<responseCode>" errorcode "<errorCode>" and errordescription "<errorDescription>"
    Examples:
    Examples:
      | quoteId | transactionId | payeePartyIdType | payeePartyIdentifier | payeeFspId   |  payerPartyIdType | payerPartyIdentifier | payerFspId   |   amountType | amount | scenario | initiator | initiatorType |  responseCode | errorCode | errorDescription |
      |   12345 |    12345      |  MSISDN          |   blah               |   payeefsp   |    MSISDN         |    5679              |   payerfsp   |     SEND     | 100    |   DEPOSIT|  payer    | CONSUMER      |  400          |      3204 |                  |


  Scenario Outline: Sending a quote request with invalid required fields to the switch from payerfsp, should result in error code and error description.
    Given I have invalid information to send a quote request
    When I send quote request to the switch with invalid required fileds, quoteId "<quoteId>", transactionId"<transactionId>",payeePartyIdType"<payeePartyIdType>", payeePartyIdentifier "<payeePartyIdentifier>"payerPartyIdType "<payerPartyIdType>", payerPartyIdentifier "<payerPartyIdentifier>", amountType "<amountType>" amount "<amount>", scenario "<scenario>", initiator"<initiator>", initiatorType "<initiatorType>"
    Then I should be getting a invalid quote error response with responsecode"<responseCode>" errorcode "<errorCode>" and errordescription "<errorDescription>"
    Examples:
        | quoteId | transactionId | payeePartyIdType | payeePartyIdentifier | payerPartyIdType | payerPartyIdentifier | amountType | amount | scenario | initiator | initiatorType |  responseCode | errorCode | errorDescription |

  Scenario Outline: Sending a quote request with invalid optional fields to the switch from payerfsp, should result in error code and error description.
    Given I have invalid information on optional quote request fields to send a quote request
    When I send quote request to the switch with any of the invalid optional fileds, quoteId "<quoteId>", transactionId"<transactionId>", transactionRequestId "<transactionRequestId>"payeePartyIdType"<payeePartyIdType>", payeePartyIdentifier "<payeePartyIdentifier>" payeePartySubIdOrType "<payeePartySubIdOrType>" payeeFspId "<payeeFspId>" payeeMerchantClassificationCode"<payeeMerchantClassificationCode>" payeeName "<payeeName>" payeeFirstName "<payeeFirstName>" payeeMiddleName "<payeeMiddleName>" payeeLastName "<payeeLastName>" payeeDOB "<payeeDOB>" payerPartyIdType "<payerPartyIdType>", payerPartyIdentifier "<payerPartyIdentifier>", payerPartySubIdOrType "<payerPartySubIdOrType>" payerFspId "<payerFspId>" payerMerchantClassificationCode"<payerMerchantClassificationCode>" payerName "<payerName>" payerFirstName "<payerFirstName>" payerMiddleName "<payerMiddleName>" payerLastName "<payerLastName>" payerDOB "<payerDOB>"amountType "<amountType>" amountcurrency "<currency>" amount "<amount>", feesCurrency "<feeCurrency>", feeAmount"<feeAmount>"scenario "<scenario>", subScenario"<subScenario>" initiator"<initiator>", initiatorType "<initiatorType>"
    Then I should be getting a invalid optional fields quote error response with responsecode"<responseCode>" errorcode "<errorCode>" and errordescription "<errorDescription>"
    Examples:
      | quoteId | transactionId | transactionRequestId | payeePartyIdType | payeePartyIdentifier | payeePartySubIdOrType | payeeFspId | payeeMerchantClassificationCode | payeeName | payeeFirstName | payeeMiddleName | payeeLastName | payeeDOB | payerPartyIdType | payerPartyIdentifier | payerPartySubIdOrType | payerFspId | payerMerchantClassificationCode | payerName | payerFirstName | payerMiddleName | payerLastName | payerDOB | amountType | currency | amount | feeCurrency | feeAmount |scenario | subScenario | initiator | initiatorType |originalTransactionId | refundReason | balanceOfPayments | latitude | longitude | note | expiration | extensionKey | extensionValue |  responseCode | errorCode | errorDescription |

  # Testing Error Code 4000, Generic Payer Error

  # Testing Error Code 4100, Generic Payer Rejection

  # Testing Error Code 4200, Payer Limit Error
  # Need to get Positions API details from Mojaloop Team

  # Testing Error Code 4300, Payer Permission Error
  # Need to get Authentication Details from Development Team


