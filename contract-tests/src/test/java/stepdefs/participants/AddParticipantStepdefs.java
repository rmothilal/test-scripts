/*****
 License
 --------------
 Copyright © 2017 Bill & Melinda Gates Foundation
 The Mojaloop files are made available by the Bill & Melinda Gates Foundation under the Apache License, Version 2.0 (the "License") and you may not use these files except in compliance with the License. You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, the Mojaloop files are distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 Contributors
 --------------
 This is the official list of the Mojaloop project contributors for this file.
 Names of the original copyright holders (individuals or organizations)
 should be listed with a '*' in the first column. People who have
 contributed from an organization can be listed under the organization
 that actually holds the copyright for their contributions (see the
 Gates Foundation organization for an example). Those individuals should have
 their names indented and be marked with a '-'. Email address can be added
 optionally within square brackets <email>.
 * Gates Foundation
 - Name Sridevi Miriyala <sridevi.miriyala@modusbox.com>
 --------------
 ******/

package stepdefs.participants;

import com.mojaloop.utils.MLResponse;
import com.mojaloop.utils.Utility;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.path.json.JsonPath;
import stepdefs.SpringAcceptanceTest;

import javax.json.Json;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static com.mojaloop.utils.Utility.getRestTemplate;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddParticipantStepdefs extends SpringAcceptanceTest {

    private Logger logger = Logger.getLogger(AddParticipantStepdefs.class.getName());

    MLResponse response;

    String participantsBaseUrl = mojaloopHost+"/interop/switch/v1/participants";


    @When("^I send a request to POST /participants with  Type\"([^\"]*)\" ID \"([^\"]*)\" and  \"([^\"]*)\" with  \"([^\"]*)\"$")
    public void iSendARequestToPOSTParticipantsWithAndWith(String type, String msisdn, String fspId, String currency) throws Throwable {

        String requestJson = Json.createObjectBuilder()
                .add("fspId", fspId)
                .add("currency",currency)
                .build().toString();

        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","");
        headers.put("FSPIOP-Source","payerfsp");

        response = Utility.post(String.join(participantsBaseUrl).join("/",type).join("/", msisdn),requestJson,headers, null,getRestTemplate());
    }

    @Then("^the participant information should be added in the switch\\. Expected FspID in the response is \"([^\"]*)\"$")
    public void theParticipantInformationShouldBeAddedInTheSwitchExpectedFspIDInTheResponseIs(String expectedFspID) throws Throwable {
        assertThat(response.getResponseBody(),containsString(expectedFspID));
    }

    @When("^I send a request to POST /participants with Type is \"([^\"]*)\", ID is \"([^\"]*)\" and FspID \"([^\"]*)\" and do not pass \"([^\"]*)\" in the request$")
    public void iSendARequestToPOSTParticipantsWithTypeIsIDIsAndFspIDAndDoNotPassInTheRequest(String type, String msisdn, String fspId, String currency) throws Throwable {
        String requestJson = Json.createObjectBuilder()
                .add("fspId", fspId)
                .build().toString();

        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","");
        headers.put("FSPIOP-Source","payerfsp");

        response = Utility.post(String.join(participantsBaseUrl).join("/",type).join("/", msisdn),requestJson,headers, null,getRestTemplate());

    }

    @Then("^the participant information should be added in the switch without currency\\. Expected FspID in the response is \"([^\"]*)\"$")
    public void theParticipantInformationShouldBeAddedInTheSwitchWithoutCurrencyExpectedFspIDInTheResponseIs(String expectedFspID) throws Throwable {
        assertThat(response.getResponseBody(),containsString(expectedFspID));
    }

    @Given("^a participant with MSISDN \"([^\"]*)\" exists in switch with a \"([^\"]*)\"$")
    public void aParticipantExistsInSwitchWithA(String id, String fspId) throws Throwable {
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","");
        headers.put("FSPIOP-Source","payerfsp");

        response = Utility.get(String.join(participantsBaseUrl).join("/","MSISDN").join("/", id) ,headers,null,getRestTemplate());

        assertThat(response.getResponseBody(),containsString(fspId));
    }

    @When("^I add the participant with the same \"([^\"]*)\" , \"([^\"]*)\" and \"([^\"]*)\" to the switch$")
    public void iAddTheParticipantWithTheSameAndToTheSwitch(String type, String id, String fspId) throws Throwable {
        String requestJson = Json.createObjectBuilder()
                .add("fspId", fspId)
                .build().toString();

        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","");
        headers.put("FSPIOP-Source","payerfsp");

        response = Utility.post(String.join(participantsBaseUrl).join("/",type).join("/", id),requestJson,headers, null,getRestTemplate());

    }

    @Then("^I should get a response \"([^\"]*)\"$")
    public void iShouldGetAResponse(String expectedErrorMessage) throws Throwable {
        assertThat(response.getResponseBody(),containsString(expectedErrorMessage));
    }

    @When("^I send a request to POST /participants with \"([^\"]*)\" and one of these fileds missing \"([^\"]*)\" \"([^\"]*)\" in the request$")
    public void iSendARequestToPOSTParticipantsWithAndOneOfTheseFiledsMissingInTheRequest(String type, String id, String fspId) throws Throwable {
        String requestJson = Json.createObjectBuilder()
                .add("fspId", fspId)
                .build().toString();

        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","");
        headers.put("FSPIOP-Source","payerfsp");

        response = Utility.post(String.join(participantsBaseUrl).join("/",type).join("/", id),requestJson,headers, null,getRestTemplate());

    }

    @Then("^An error should be returned\\. Expected error code is \"([^\"]*)\"$")
    public void anErrorShouldBeReturnedExpectedErrorCodeIs(String expectedErrorCode) throws Throwable {
        assertThat(response.getResponseBody(),containsString(expectedErrorCode));
    }

    @And("^error description is \"([^\"]*)\"$")
    public void errorDescriptionIs(String expectedErrorDescription) throws Throwable {
        assertThat(response.getResponseBody(),containsString(expectedErrorDescription));
    }

    @When("^I send a request to POST /participants with an invalid FspID \"([^\"]*)\", a valid Type \"([^\"]*)\" and  ID \"([^\"]*)\" in the request$")
    public void iSendARequestToPOSTParticipantsWithAnInvalidFspIDAValidTypeAndIDInTheRequest(String fspId, String type, String id) throws Throwable {
        String requestJson = Json.createObjectBuilder()
                .add("fspId", fspId)
                .build().toString();

        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","");
        headers.put("FSPIOP-Source","payerfsp");

        response = Utility.post(String.join(participantsBaseUrl).join("/",type).join("/", id),requestJson,headers, null,getRestTemplate());

    }
    @Then("^An error should be returned for invalid FspID Expected error code is \"([^\"]*)\"$")
    public void anErrorShouldBeReturnedForInvalidFspIDExpectedErrorCodeIs(String errorcode) throws Throwable {
        JsonPath jPath = JsonPath.from(response.getResponseBody());
        assertThat(jPath.getString("errorInformation.errorCode"), is(errorcode));

    }

    @And("^Error description for invalid FspId is \"([^\"]*)\"$")
    public void errorDescriptionForInvalidFspIdIs(String errorDescription) throws Throwable {
        JsonPath jPath = JsonPath.from(response.getResponseBody());
        assertThat(jPath.getString("errorInformation.errorDescription"), is(errorDescription));
    }

    @And("^Http Response code for invalid FspId is \"([^\"]*)\"$")
    public void httpResponseCodeForInvalidFspIdIs(String expectedResponseCode) throws Throwable {
        assertThat(response.getResponseCode(),is(expectedResponseCode));
    }

    @When("^I send a request to POST /participants with a valid ID \"([^\"]*)\", FspID  \"([^\"]*)\" and invalid Type \"([^\"]*)\" in the request$")
    public void iSendARequestToPOSTParticipantsWithAValidFspIDAndInvalidTypeInTheRequest(String id, String fspId, String type) throws Throwable {
        String requestJson = Json.createObjectBuilder()
                .add("fspId", fspId)
                .build().toString();

        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","");
        headers.put("FSPIOP-Source","payerfsp");

        response = Utility.post(String.join(participantsBaseUrl).join("/",type).join("/", id),requestJson,headers, null,getRestTemplate());

    }

    @Then("^An error should be returned for Invalid Type\\. Expected error code is \"([^\"]*)\"$")
    public void anErrorShouldBeReturnedForInvalidTypeExpectedErrorCodeIs(String expectedErrorCode) throws Throwable {
        JsonPath jPath = JsonPath.from(response.getResponseBody());
        assertThat(jPath.getString("errorInformation.errorCode"), is(expectedErrorCode));
    }

    @And("^error description for Invalid Type is \"([^\"]*)\"$")
    public void errorDescriptionForInvalidTypeIs(String expectedErrorDescription) throws Throwable {
        JsonPath jPath = JsonPath.from(response.getResponseBody());
        assertThat(jPath.getString("errorInformation.errorDescription"), is(expectedErrorDescription));
    }

    @And("^Http response code for Invalid Type is \"([^\"]*)\"$")
    public void httpResponseCodeForInvalidTypeIs(String expectedResponseCode) throws Throwable {
        assertThat(response.getResponseCode(),is(expectedResponseCode));
    }

    @When("^I send a request to POST /participants with a valid FspID \"([^\"]*)\", valid Type \"([^\"]*)\" and invalid ID \"([^\"]*)\" in the request$")
    public void iSendARequestToPOSTParticipantsWithAValidFspIDValidTypeAndInvalidIDInTheRequest(String fspId, String type, String id) throws Throwable {
        String requestJson = Json.createObjectBuilder()
                .add("fspId", fspId)
                .build().toString();

        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","");
        headers.put("FSPIOP-Source","payerfsp");

        response = Utility.post(String.join(participantsBaseUrl).join("/",type).join("/", id),requestJson,headers, null,getRestTemplate());

    }

    @Then("^An error should be returned for invalid ID\\. Expected error code is \"([^\"]*)\"$")
    public void anErrorShouldBeReturnedForInvalidIDExpectedErrorCodeIs(String expectedErrorCode) throws Throwable {
        JsonPath jPath = JsonPath.from(response.getResponseBody());
        assertThat(jPath.getString("errorInformation.errorCode"), is(expectedErrorCode));
    }

    @And("^error description for invalid ID is \"([^\"]*)\"$")
    public void errorDescriptionForInvalidIDIs(String expectedErrorDescription) throws Throwable {
        JsonPath jPath = JsonPath.from(response.getResponseBody());
        assertThat(jPath.getString("errorInformation.errorDescription"), is(expectedErrorDescription));
    }

    @And("^Http response code for invalid ID is \"([^\"]*)\"$")
    public void httpResponseCodeForInvalidIDIs(String expectedResponseCode) throws Throwable {
        assertThat(response.getResponseCode(),is(expectedResponseCode));
    }

    @When("^I send POST /participant request with invalid \"([^\"]*)\" along with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iSendPOSTParticipantRequestWithInvalidAlongWithAnd(String currency, String fspId, String id) throws Throwable {
        String requestJson = Json.createObjectBuilder()
                .add("fspId", fspId)
                .add("currency",currency)
                .build().toString();

        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","");
        headers.put("FSPIOP-Source","payerfsp");

        response = Utility.post(String.join(participantsBaseUrl).join("/","MSISDN").join("/", id),requestJson,headers, null,getRestTemplate());

    }

    @Then("^An error should be returned for invalid currency\\. Expected error code is \"([^\"]*)\"$")
    public void anErrorShouldBeReturnedForInvalidCurrencyExpectedErrorCodeIs(String expectedErrorCode) throws Throwable {
        JsonPath jPath = JsonPath.from(response.getResponseBody());
        assertThat(jPath.getString("errorInformation.errorCode"), is(expectedErrorCode));
    }

    @And("^error description for invalid currency is \"([^\"]*)\"$")
    public void errorDescriptionForInvalidCurrencyIs(String expectedErrorDescription) throws Throwable {
        JsonPath jPath = JsonPath.from(response.getResponseBody());
        assertThat(jPath.getString("errorInformation.errorDescription"), is(expectedErrorDescription));
    }

    @And("^Http response code for invalid currency is \"([^\"]*)\"$")
    public void httpResponseCodeForInvalidCurrencyIs(String expectedResponseCode) throws Throwable {
        assertThat(response.getResponseCode(),is(expectedResponseCode));
    }



}

