package stepdefs.p2p_transfer;

import com.mojaloop.utils.HttpClient;
import com.mojaloop.utils.Utility;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import stepdefs.SpringAcceptanceTest;

import javax.json.Json;

import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class P2PTransferGoldenPathStepdefs extends SpringAcceptanceTest {

    private Logger logger = Logger.getLogger(P2PTransferGoldenPathStepdefs.class.getName());

    ResponseEntity<String> response;

    String mojaloopHost = "13.58.148.157";
    String mojaloopUrl = "http://"+mojaloopHost+":8088/interop/switch/v1";

    @When("^In fsp \"([^\"]*)\" when I add user with the following details  MSISDN: \"([^\"]*)\" Full Name: \"([^\"]*)\" First Name: \"([^\"]*)\" Last Name: \"([^\"]*)\" DOB: \"([^\"]*)\"$")
    public void inFspWhenIAddUserWithTheFollowingDetailsMSISDNFullNameFirstNameLastNameDOB(String fsp, String msisdn, String fullName, String firstName, String lastName, String dob) throws Throwable {
        String data = Json.createObjectBuilder()
                            .add("party", Json.createObjectBuilder()
                                .add("partyIdInfo", Json.createObjectBuilder()
                                        .add("partyIdType","MSISDN")
                                        .add("partyIdentifier", msisdn)
                                        .add("fspId", fsp)
                                )
                                .add("name", fullName)
                                .add("personalInfo", Json.createObjectBuilder()
                                    .add("complexName",Json.createObjectBuilder()
                                            .add("firstName", firstName)
                                            .add("lastName", lastName)
                                    )
                                    .add("dateOfBirth",dob)
                                )
                            )
                            .build()
                            .toString();

        logger.info("Maven property: "+System.getProperty("test"));

        String endPoint = "/"+fsp+"/parties/MSISDN/"+msisdn;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(data,headers);
        response = restTemplate.postForEntity(endPoint,entity,String.class);

    }

    @Then("^User \"([^\"]*)\" should be successfully added$")
    public void userShouldBeSuccessfullyAdded(String arg0) throws Throwable {
        assertThat(response.getStatusCodeValue(), is(200));
    }


    @When("^I add MSISDN \"([^\"]*)\" in fsp \"([^\"]*)\"$")
    public void iAddMSISDNInFsp(String msisdn, String fsp) throws Throwable {
        String requestJson = Json.createObjectBuilder()
                                    .add("fspId", fsp)
                                    .add("currency","USD")
                                    .build().toString();
        int status = Utility.post(mojaloopUrl + "/participants/MSISDN/" + msisdn,fsp,null,null,requestJson,restTemplate);
        assertThat(status,is(200));
    }

    @Then("^I want to ensure that MSISDN \"([^\"]*)\" is successfully added to the switch under fsp \"([^\"]*)\"$")
    public void iWantToEnsureThatMSISDNIsSuccessfullyAddedToTheSwitch(String msisdn, String fsp) throws Throwable {
        String responseJson = Utility.get(mojaloopUrl + "/participants/MSISDN/"+msisdn,fsp,null,null,restTemplate);
        assertThat(responseJson,containsString(fsp));
    }

    @Given("^Payer \"([^\"]*)\" in Payer FSP \"([^\"]*)\" and Payee \"([^\"]*)\" in Payee FSP \"([^\"]*)\" exists in the switch$")
    public void payerInPayerFSPAndPayeeInPayeeFSPExistsInTheSwitch(String arg0, String arg1, String arg2, String arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^Payer \"([^\"]*)\" with MSISDN \"([^\"]*)\" does a lookup for payee \"([^\"]*)\" with MSISDN \"([^\"]*)\"$")
    public void payerWithMSISDNDoesALookupForPayeeWithMSISDN(String arg0, String arg1, String arg2, String arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Payee \"([^\"]*)\" results should be returned\\. Expected values are First Name \"([^\"]*)\" Last Name \"([^\"]*)\" DOB \"([^\"]*)\"$")
    public void payeeResultsShouldBeReturnedExpectedValuesAreFirstNameLastNameDOB(String arg0, String arg1, String arg2, String arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
