Feature: Test participant endpoint in the Mojaloop API.


  Scenario Outline: Test POST /participants for participant type MSISDN.
    When  I send a post request on to the server
    Then the participant infomation should be added in the server.

    Examples:
    |  fspId   |    currency    |    Type    |    ID        |


  Scenario Outline: Create/post a participant information on the server that already exists in the server
    Given <fspId>, <currency> of the participant to add, and <Type> & <ID> of the party that is associated with the participant to be added
    When  I send a post request on to the server
    Then it should return an <Errorcode> and <ErrorDescription>

    Examples:
      |  fspId   |    currency    |    Type    |    ID        |  Errorcode  |  ErrorDescription  |


  Scenario Outline: Create/post a participant information on the server with invalid information
    Given <fspId>, <currency> of the participant to add, and <Type> & <ID> of the party that is associated with the participant to be added
    When  I send a post request on to the server
    Then it should return an <Errorcode> and <ErrorDescription>
    Examples:
      |  fspId   |    currency    |    Type    |    ID        |  Errorcode  |  ErrorDescription |

