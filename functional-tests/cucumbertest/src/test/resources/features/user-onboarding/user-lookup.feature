#Feature As a DFSP I want to look-up a user based on MSISDN So that P2P transfers can be executed
#
#  Scenario: Look-up a user with a valid MSISDN that exists in the central directory
#
#    Given a user with valid "<MSISDN>" exists in central directory
#    When I look-up for "<MSISDN>"
#    Then I shoud get "<Name>" "<DOB>" "<DFSP>" back

#
#
#  Scenario Outline: : Look-up a user with a valid MSISDN that does not exist in the central directory.
#    Given a user does not exist in central directory with a valid "<MSISDN>"
#    When I look-up for "<MSISDN>"
#    Then I shoud get "<Error Msg>" back.
#    Examples:
#      |MSISDN | Error Msg |
#
#
#   Scenario Outline: : Look-up a user with an Invalid MSISDN
#     Given a user with invalid "<MSISDN>"
#     When I look-up for that invalid "<MSISDN>"
#     Then I should get an error msg "<Error Msg>" back.
#     Examples:
#       |MSISDN | Error Msg |
#
#  Scenario Outline: : Look-up a user with an Invalid MSISDN type
#    Given a user with invalid "<ID Type>"
#    When I look-up for that invalid "<ID Type>"
#    Then I should get an error msg "<Error Msg>" back.
#    Examples:
#      |ID Type | Error Msg |
