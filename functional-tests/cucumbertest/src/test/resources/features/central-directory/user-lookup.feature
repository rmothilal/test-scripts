Feature As a DFSP, I want to look-up a user based on MSISDN. So that, P2P transfers can be executed.

  Scenario Outline: Look-up a user with a valid MSISDN, that exists in the central directory.

    Given a user with MSISDN "<PhNum>"exists in central directory
    When I do a look-up for "<PhNum>"
    Then I shoud get "<ExpectedName>", "<ExpectedDOB>" back.

    Examples:



