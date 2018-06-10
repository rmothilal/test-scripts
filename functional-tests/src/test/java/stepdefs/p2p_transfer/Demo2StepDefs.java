package stepdefs.p2p_transfer;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.json.Json;
import java.util.logging.Logger;

import static com.mojaloop.utils.Utility.getRestTemplate;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Demo2StepDefs {

    private Logger logger = Logger.getLogger(P2PTransferGoldenPathStepdefs.class.getName());

    ResponseEntity<String> response;
    String responseJson;

    String mojaloopHost = System.getProperty("mojaloop.host");
    String mojaloopUrl = "http://"+mojaloopHost+":8088/interop/switch/v1";

    @When("^Demo In fsp \"([^\"]*)\" when I add user with the following details  MSISDN: \"([^\"]*)\" Full Name: \"([^\"]*)\" First Name: \"([^\"]*)\" Last Name: \"([^\"]*)\" DOB: \"([^\"]*)\"$")
    public void demoInFspWhenIAddUserWithTheFollowingDetailsMSISDNFullNameFirstNameLastNameDOB(String fsp, String msisdn, String fullName, String firstName, String lastName, String dob) throws Throwable {
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

        response = getRestTemplate().postForEntity("https://localhost:8444"+endPoint,entity,String.class);
    }

    @Then("^Demo User \"([^\"]*)\" should be successfully added$")
    public void demoUserShouldBeSuccessfullyAdded(String arg0) throws Throwable {
        assertThat(response.getStatusCodeValue(), is(200));
    }

    @When("^Demo I add MSISDN \"([^\"]*)\" in fsp \"([^\"]*)\"$")
    public void demoIAddMSISDNInFsp(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Demo I want to ensure that MSISDN \"([^\"]*)\" is successfully added to the switch under fsp \"([^\"]*)\"$")
    public void demoIWantToEnsureThatMSISDNIsSuccessfullyAddedToTheSwitchUnderFsp(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^Demo Payer \"([^\"]*)\" in Payer FSP \"([^\"]*)\" and Payee \"([^\"]*)\" in Payee FSP \"([^\"]*)\" exists in the switch$")
    public void demoPayerInPayerFSPAndPayeeInPayeeFSPExistsInTheSwitch(String arg0, String arg1, String arg2, String arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^Demo Payer \"([^\"]*)\" with MSISDN \"([^\"]*)\" does a lookup for payee MSISDN \"([^\"]*)\"$")
    public void demoPayerWithMSISDNDoesALookupForPayeeMSISDN(String arg0, String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Demo Payee \"([^\"]*)\" results should be returned\\. Expected values are First Name \"([^\"]*)\" Last Name \"([^\"]*)\" DOB \"([^\"]*)\"$")
    public void demoPayeeResultsShouldBeReturnedExpectedValuesAreFirstNameLastNameDOB(String arg0, String arg1, String arg2, String arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
