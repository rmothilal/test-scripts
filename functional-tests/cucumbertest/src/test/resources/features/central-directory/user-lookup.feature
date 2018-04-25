Feature As a DFSP, I want to look-up a user based on MSISDN. So that, P2P transfers can be executed.

  Scenario Look-up a user with a valid MSISDN, that exists in the central directory.

    Given a user exists in central directory with a valid "<MSISDN>"
    When I look-up for "<MSISDN>"
    Then I shoud get Name, DOB, DFSP back.

        Scenario outline Look-up a user in central directory

        Examples

