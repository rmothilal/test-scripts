Feature: Testing Delete participant information feature on the switch / Testing DEL/participant endpoint.

  Scenario Outline: Delete a participant information on the switch, provided <MSISDN> and <FspID>

    Given the details type <MSISDN> and ID <FspID>
    When I enter "<MSISDN>" and "<FspID>" and send request to delete the participant info
    Then upon further lookup, no results shd find for that participant from switch
    Examples:

  Scenario Outline:  Delete a participant information on the switch provided <MSISDN> <FspID> and <SubId>
    Given the details type "<MSISDN>", ID "<FspID>" and subId "<SubId>"
    When I enter "<MSISDN>" ,"<FspID>" and "<SubId>" and send request to delete the participant info
    Then upon further lookup, no results shd find for that participant from switch
    Examples:

  Scenario Outline:  Delete a participant information on the switch provided <MSISDN> <FspID> and optional <SubId>
    Given the details type "<MSISDN>", ID "<FspID>"
    When I enter "<MSISDN>" ,"<FspID>" and "<SubId>" and send request to delete the participant info
    Then upon further lookup, no results shd find for that participant from switch
    Examples:

  Scenario Outline: Testing for error msg if one of the required fileds, FspID is missing.
    Given type <Type> and missing ID
    When I enter "<Type>" and send request to delete the participant info
    Then I shd get an error msg "<ErrorCode>" and "<ExpectedErrorMsg>"
    Examples:

  Scenario Outline: Testing for error msg if one of the required fileds, "Type" is missing.
    Given the FspID "<FspID>" and missing "<Type>"
    When I enter <FspID> and send request to delete the participant info
    Then I shd get an error msg "<ErrorCode>" and "<ExpectedErrorMsg>"
    Examples:

  Scenario Outline: The participant information can not be deleted if the participant does not exists in the switch.
    Given the details type "<MSISDN>", ID "<FspID>" and subId "<SubId>"
    When I enter "<MSISDN>" ,"<FspID>" and "<SubId>" and send request to delete the participant info
    Then I should get an error msg "<ErrorCode>" and "<ExpectedErrorDescription>"
    Examples:



