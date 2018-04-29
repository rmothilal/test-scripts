Feature: As a DFSP, I shoud be able to add a user so that P2P transfers can be executed

  Scenario Outline: : Add a user with a valid MSISDN type and FSP ID
    Given a user does not exists in central directory
    When I add a user with "<MSISDN>" and "<FSP ID>"
    Then the user should be added in the central directory.
    Examples:
      |MSISDN  | FSP ID | Currency |

  Scenario Outline: : Add a user with invalid MSISDN type and valid FSP ID
     Given a user does not exists in central directory
     When I add a user with an invalid "<MSISDN>" and a valid "<FSP ID>"
     Then I shoud see the error msg "<Error Msg>"
    Examples:
    |MSISDN  | FSP ID | Currency | Error Msg |

  Scenario Outline: : Add a user with a valid MSISDN type and an invalid FSP ID
    Given a user does not exists in central directory
    When I add a user with valid "<MSISDN>" and an invalid "<FSP ID>"
    Then I shoud see the error msg "<Error Msg>"
    Examples:
      |MSISDN  | FSP ID | Currency | Error Msg |

  Scenario Outline: Add a user with invalid MSISDN type and invalid FSP ID
    Given a user does not exists in central directory
    When I add a user with invalid "<MSISDN>" and invalid "<FSP ID>"
    Then I shoud see the error msg "<Error Msg>"
    Examples:
      |MSISDN  | FSP ID | Currency | Error Msg |

  Scenario Outline: Add a user with a valid MSISDN type and a valid FSP ID with invalid currency
    Given a user does not exists in central directory
    When I add a user with "<MSISDN>","<FSP ID>" and invalid "<Currency>"
    Then I shoud see the error msg "<Error Msg>"
    Examples:
      |MSISDN  | FSP ID | Currency | Error Msg |

  Scenario Outline: Add a user with a valid MSISDN type and a valid FSP ID that exists in Central directory.
    Given a user does exists in central directory
    When I add a user with "<MSISDN>","<FSP ID>"
    Then I shoud see the error msg "<Error Msg>"
    Examples:
      |MSISDN  | FSP ID | Currency | Error Msg |