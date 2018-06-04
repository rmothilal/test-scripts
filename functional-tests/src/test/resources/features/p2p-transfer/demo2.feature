Feature: As a Stakeholder responsible for Mojaloop Application, I want to make sure that the golden path for
  a p2p money transfer works without errors

  @Demo
  Scenario Outline: Demo Test Preparation. Adding users to Payer FSP and Payee FSP
    When Demo In fsp "<fsp>" when I add user with the following details  MSISDN: "<MSISDN>" Full Name: "<full_name>" First Name: "<first_name>" Last Name: "<last_name>" DOB: "<dob>"
    Then Demo User "<user>" should be successfully added
    Examples:
      |      fsp           |           MSISDN          |           full_name          |    first_name    |   last_name    |      dob       |
      |    payerfsp        |         1272545111        |        Khomotso Makgopa      |     Khomotso     |    Makgopa     |    1/1/1971    |
      |    payerfsp        |         1272545112        |        Mbuso Makoa           |     Mbuso        |    Makoa       |    2/2/1972    |
      |    payeefsp        |         1272545117        |        Siabelo Maroka        |     Siabelo      |    Maroka      |    3/3/1973    |
      |    payeefsp        |         1272545118        |        Nanga Makwetla        |     Nanga        |    Makwetla    |    4/4/1974    |

  @Demo
  Scenario Outline: Demo Adding users into the switch
    When Demo I add MSISDN "<MSISDN>" in fsp "<fsp>"
    Then Demo I want to ensure that MSISDN "<MSISDN>" is successfully added to the switch under fsp "<fsp>"

    Examples:
      |    MSISDN       |   fsp       |
      | 1272545111      | payerfsp    |
      | 1272545112      | payerfsp    |
      | 1272545117      | payeefsp    |
      | 1272545118      | payeefsp    |

  @Demo
  Scenario Outline: Demo Payer doing a looking on the receiver(payee). This is the first step in p2p money transfer
    Given Demo Payer "<payer>" in Payer FSP "<payer-fsp>" and Payee "<payee>" in Payee FSP "<payee-fsp>" exists in the switch
    When Demo Payer "<payer>" with MSISDN "<payer-msisdn>" does a lookup for payee MSISDN "<payee-msisdn>"
    Then Demo Payee "<payee>" results should be returned. Expected values are First Name "<payee-firstname>" Last Name "<payee-lastname>" DOB "<payee-dob>"

    Examples:
      |    payer              |  payer-msisdn    |   payer-fsp   |   payee            |   payee-msisdn   |    payee-fsp   | payee-firstname   | payee-lastname   | payee-dob   |
      |    Khomotso Makgopa   |  1272545111      |   payerfsp    |   Siabelo Maroka   |   1272545117     |    payeefsp    |     Siabelo       |    Maroka        | 3/3/1973    |
      |    Mbuso Makoa        |  1272545112      |   payerfsp    |   Nanga Makwetla   |   1272545118     |    payeefsp    |     Nanga         |    Makwetla      | 4/4/1974    |