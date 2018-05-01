Feature: As a Stakeholder responsible for Mojaloop Application, I want to make sure that the golden path for
  a p2p money transfer works without errors

  Scenario Outline: Test Preparation. Adding users to Payer FSP and Payee FSP
    When In fsp "<fsp>" when I add user with the following details  MSISDN: "<MSISDN>" Full Name: "<full_name>" First Name: "<first_name>" Last Name: "<last_name>" DOB: "<dob>"
    Then User "<user>" should be successfully added

    Examples:
      |      fsp           |           MSISDN          |           full_name          |    first_name    |   last_name    |      dob       |
      |    payerfsp        |         1272545111        |        Khomotso Makgopa      |     Khomotso     |    Makgopa     |    1/1/1971    |
      |    payerfsp        |         1272545112        |        Mbuso Makoa           |     Mbuso        |    Makoa       |    2/2/1972    |
      |    payeefsp        |         1272545117        |        Siabelo Maroka        |     Siabelo      |    Maroka      |    3/3/1973    |
      |    payeefsp        |         1272545118        |        Nanga Makwetla        |     Nanga        |    Makwetla    |    4/4/1974    |



  Scenario Outline: Adding users into the switch
    When I add MSISDN "<MSISDN>" in fsp "<fsp>"
    Then I want to ensure that MSISDN "<MSISDN>" is successfully added to the switch under fsp "<fsp>"

    Examples:
    |    user            |    MSISDN       |   fsp       |
    | Khomotso Makgopa   | 1272545111      | payerfsp    |
    | Mbuso Makoa        | 1272545112      | payerfsp    |
    | Siabelo Maroka     | 1272545117      | payeefsp    |
    | Nanga Makwetla     | 1272545118      | payeefsp    |


  Scenario Outline: Payer doing a looking on the receiver(payee). This is the first step in p2p money transfer
    Given Payer "<payer>" in Payer FSP "<payer-fsp>" and Payee "<payee>" in Payee FSP "<payee-fsp>" exists in the switch
    When Payer "<payer>" with MSISDN "<payer-msisdn>" does a lookup for payee "<payee>" with MSISDN "<payee-msisdn>"
    Then Payee "<payee>" results should be returned. Expected values are First Name "<payee-firstname>" Last Name "<payee-lastname>" DOB "<payee-dob>"

    Examples:
    |    payer              |  payer-msisdn    |   payer-fsp   |   payee            |   payee-msisdn   |    payee-fsp   | payee-firstname   | payee-lastname   | payee-dob   |
    |    Khomotso Makgopa   |  1272545111      |   payerfsp    |   Siabelo Maroka   |   1272545117     |    payeefsp    |     Siabelo       |    Maroka        | 3/3/1973    |
    |    Mbuso Makoa        |  1272545112      |   payerfsp    |   Nanga Makwetla   |   1272545118     |    payeefsp    |     Nanga         |    Makwetla      | 4/4/1974    |

#
  Scenario Outline: Quote. In this step Payer FSP requests a quote to determine fees and commission on the amount that
  the Payer wants to send
    Given Payee "<payee>" details are resolved
    When Payer FSP "<payer-fsp>" sends the quote request by providing "<amount>" and "<currency>"
    Then I should see total fee and commission for the "<amount>" specified by payee
    Examples:
    | payee | payer-fsp | amount | currency |
    | Henrik Karlsson| BankNrOne | 100 | USD |

#  Scenario Outline: Perform the transfer
#    Given the unique transactionID "<transactionID>" and quoteID "<quoteID>"
#    When Payer "<payer>" sends the transfer request
#    Then Transferred amount "<amount>" should be debited from Payer's account
#    And Transferred amount "<amount>" should be credited to Payee's account
#
#    Examples:
