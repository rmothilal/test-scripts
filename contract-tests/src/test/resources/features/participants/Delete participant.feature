Feature: Deleting a participant information on the switch / Testing DEL/participant endpoint.

  Scenario Outline: Delete a participant information on the switch, provided <Type> and <ID>

    Given the details type <Type> and ID <ID>
    When I enter <Type> and <ID> and send request to delete the participant info
    Then upon further lookup, no results shd find for that participant from switch
    Examples:

  Scenario Outline:  Delete a participant information on the switch provided <Type> <ID> and optional <SubId>
    Given the details type <Type>, ID <ID> and subId <SubId>
    When I enter <Type> ,<ID> and <SubId> and send request to delete the participant info
    Then upon further lookup, no results shd find for that participant from switch
    Examples:

  Scenario Outline: Testing for error msg if one of the required fileds are missing.
    Given one of the required fields are missing, type <Type> and missing ID
    When I enter <Type> and send request to delete the participant info
    Then I shd get an error msg "<ExpectedErrorMsg>"
    Examples:

  Scenario Outline: Testing for error msg if one of the required fileds are missing.
    Given the one of the required fields are missing, ID <ID> and missing <Type>
    When I enter <ID> and send request to delete the participant info
    Then I shd get an error msg "<ExpectedErrorMsg>"
    Examples:




