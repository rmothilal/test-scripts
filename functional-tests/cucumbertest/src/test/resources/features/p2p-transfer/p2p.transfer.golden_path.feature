Feature: As a Stakeholder responsible for Mojaloop Application, I want to make sure that the golden path for
  a p2p money transfer works without errors

  Scenario Outline: Adding users into the switch
    Given User "<user>" does not exist in the switch
    When I add the user "<user>" that exists in fsp "<fsp>"
    Then I want to ensure that "<user>" is successfully added to the switch

    Examples:
    |         user        |           fsp          |
    | Mats Hagman         | BankNrOne              |
    | Henrik Karlsson     | Mobile Money           |


  Scenario Outline: Payer doing a looking on the receiver(payee). This is the first step in p2p money transfer
    Given Payer "<payer>" in Payer FSP "<payer-fsp>" and Payee "<payee>" in Payee FSP "<payee-fsp>" exists in the switch
    When Payer "<payer>" in Payer FSP "<payer-fsp>" does a lookup for payee "<payee>" that is in Payee FSP "<payee-fsp>"
    Then Expected Payee "<payee>" results should be returned

    Examples:
    |    payer   |   payer-fsp   |   payee   |   payee-fsp   |
    | Mats Hagman| BankNrOne     | Henrik Karlsson| Mobile Money   |

  Scenario Outline: Quote. In this step Payer FSP requests a quote to determine fees and commission on the amount that
  the Payer wants to send
    Given Payee "<payee>" details are resolved
    When Payer FSP "<payer-fsp>" sends the quote request by providing "<amount>" and "<currency>"
    Then I should see total fee and commission for the "<amount>" specified by payee
    Examples:
    | payee | payer-fsp | amount | currency |
    | Henrik Karlsson| BankNrOne | 100 | USD |

  Scenario Outline: Perform the transfer
    Given the unique transactionID "<transactionID>" and quoteID "<quoteID>"
    When Payer "<payer>" sends the transfer request
    Then Transferred amount "<amount>" should be debited from Payer's account
    And Transferred amount "<amount>" should be credited to Payee's account

    Examples: