Feature: As a Stakeholder responsible for Mojaloop Application, I want to make sure that the golden path for
  a p2p money transfer works without errors

#  Scenario: Able to register FSPs with their callback details
#    When I add "payerfsp" and "payeefsp" to the switch
#    Then They should be successfully added
#
#  Scenario Outline: Test Preparation. Adding users to Payer FSP and Payee FSP
#    When In fsp "<fsp>" when I add user with the following details  MSISDN: "<MSISDN>" Full Name: "<full_name>" First Name: "<first_name>" Last Name: "<last_name>" DOB: "<dob>"
#    Then User "<user>" should be successfully added
#
#    Examples:
#      |      fsp           |           MSISDN           |           full_name          |    first_name    |   last_name    |      dob       |
#      |    payerfsp        |         27713803910        |        Khomotso Makgopa      |     Khomotso     |    Makgopa     |    1/1/1971    |
#      |    payerfsp        |         27713803911        |        Mbuso Makoa           |     Mbuso        |    Makoa       |    2/2/1972    |
#      |    payeefsp        |         27713803912        |        Siabelo Maroka        |     Siabelo      |    Maroka      |    3/3/1973    |
#      |    payeefsp        |         27713803913        |        Nanga Makwetla        |     Nanga        |    Makwetla    |    4/4/1974    |
#
#
#
#  Scenario Outline: Adding users into the switch
#    When I add MSISDN "<MSISDN>" in fsp "<fsp>"
#    Then I want to ensure that MSISDN "<MSISDN>" is successfully added to the switch under fsp "<fsp>"
#
#    Examples:
#    |    user            |    MSISDN        |   fsp       |
#    | Khomotso Makgopa   | 27713803910      | payerfsp    |
#    | Mbuso Makoa        | 27713803911      | payerfsp    |
#    | Siabelo Maroka     | 27713803912      | payeefsp    |
#    | Nanga Makwetla     | 27713803913      | payeefsp    |


#  Scenario Outline: Payer doing a looking on the receiver(payee). This is the first step in p2p money transfer
#    Given Payer "<payer>" in Payer FSP "<payer-fsp>" and Payee "<payee>" in Payee FSP "<payee-fsp>" exists in the switch
#    When Payer "<payer>" with MSISDN "<payer-msisdn>" does a lookup for payee "<payee>" with MSISDN "<payee-msisdn>"
#    Then Payee "<payee>" results should be returned. Expected values are First Name "<payee-firstname>" Last Name "<payee-lastname>" DOB "<payee-dob>"
#
#    Examples:
#    |    payer              |  payer-msisdn     |   payer-fsp   |   payee            |   payee-msisdn   |    payee-fsp   | payee-firstname   | payee-lastname   | payee-dob   |
#    |    Khomotso Makgopa   |  27713803910      |   payerfsp    |   Siabelo Maroka   |   27713803912     |    payeefsp    |     Siabelo       |    Maroka        | 3/3/1973    |
#    |    Mbuso Makoa        |  27713803911      |   payerfsp    |   Nanga Makwetla   |   27713803913     |    payeefsp    |     Nanga         |    Makwetla      | 4/4/1974    |


#  Scenario Outline: Quote. In this step Payer FSP requests a quote to determine fees and commission on the amount that the Payer wants to send
#    When Payer FSP issues a quote to the switch by providing "<amount>" and "<currency>". Payer MSISDN is "<payer-msisdn>" Payee MSISDN is "<payee-msisdn>"
#    Then Payer FSP should see total fee and commission for the "<amount>" specified by payer. Expected payee fsp fee is "<expected-payee-fee>" and Expected payee fsp commission is "<expected-payee-commission>"
#    Examples:
#      |  payer-msisdn    |   payee-msisdn   |  amount  | currency  | expected-payee-fee   |   expected-payee-commission   |
#      |  27713803910      |   27713803912     |   100    |   USD     |          1           |           1                   |
#      |  27713803911      |   27713803913     |   200    |   USD     |          1           |           1                   |
#
  Scenario Outline: Perform the transfer
    Given A quote exists. Payer MSISDN "<payer-msisdn>" Payee MSISDN "<payee-msisdn>" Amount "<amount>"
    When I submit a transfer for amount "<amount>"
    Then I should get a fulfillment response back with a transfer state of "COMMITTED"

    Examples:
      |  payer-msisdn    |   payee-msisdn   |  amount  |
      |  27713803910      |   27713803912     |   100    |
      |  27713803911      |   27713803913     |   200    |