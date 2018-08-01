Feature: This feature can be used by payer fsp or payee fsp to know the details about the previous quote
  The HTTP request GET /quotes/ is used to get information regarding an earlier created or requested quote.
  In the URI should contain the quoteId that was used for the creation of the quote.

  Scenario Outline: Getting the quote details of the previously created quote
    Given an existing quote ID
    When I send request GET /Quotes for details of the previously created quote with "<Quote ID>"
    Then I shoud be getting the quote details

    Examples:
    |     Quote ID    |

  Scenario Outline:  Testing the quotes endpoint for an error by not sending Quote ID
    Given a quote Id exists
    When I send request GET /quotes without passing required URI parameter "<Quote ID>"
    Then I shoud be getting an error code "<ErrorCode>" and "<ErrorDescription>"
    Examples:
        |    Quote ID    | ErrorCode |  ErrorDescription  |
        |    Null        |   404     |                    |

    # Testing Error Code 3205, Quote ID not found
  Scenario Outline:  Testing the quotes endpoint for an error by sending non existing Quote ID
    Given a quote Id that does not exists
    When I send request GET /quotes with non existing Quote ID "<Quote ID>"
    Then I shoud be getting an error code "<ErrorCode>" and "<ErrorDescription>"
    Examples:
      |    Quote ID    |  ErrorCode |  ErrorDescription  |
      |     12345      |   400      |                    |
      |     00000      |   400      |                    |
      |     abcde      |   400      |                    |
      |     #23a*0     |   400      |                    |
      |     tuti.4     |   400      |                    |

  Scenario Outline:  Testing the quotes endpoint for an error by sending invalild Quote ID
    When I send request GET /quotes with invalid Quote ID "<Quote ID>"
    Then I shoud be getting a response code "<ResponseCode>" an error code "<ErrorCode>" and "<ErrorDescription>"
    Examples:
      |    Quote ID    |ResponseCode|  ErrorCode      |ErrorDescription  |
      |     00000      |   400      |                    |             |
      |     abcde      |   400      |                    |             |
      |     #23a*0     |   400      |                    |             |
      |     tuti.4     |   400      |                    |             |

  Scenario Outline: Getting a pre existing Quote info with missing Headers should return error msg
    When I try to get quote info with "<QuoteID>" and with missing "<Headers>"
    Then I should get a response  with "<ExpectedResponseCode>", "<ExpectedErrorCode>" and "<ExpectedErrorMsg>"
    Examples:
      |   QuoteID   |    Headers      |   ExpectedResponseCode  |  ExpectedErrorCode | ExpectedErrorMsg  |
      |  1272545117 |     Accept      |                         |                    |                   |
      |  1272545117 | FSPIOP-Source   |                         |                    |                   |
      |  1272545117 |      Date       |                         |                    |                   |

