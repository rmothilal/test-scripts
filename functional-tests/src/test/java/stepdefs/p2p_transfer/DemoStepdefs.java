package stepdefs.p2p_transfer;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.ResponseEntity;

import java.util.logging.Logger;

public class DemoStepdefs {
    private Logger logger = Logger.getLogger(P2PTransferGoldenPathStepdefs.class.getName());

    ResponseEntity<String> response;
    String responseJson;

    String mojaloopHost = System.getProperty("mojaloop.host");
    String mojaloopUrl = "http://"+mojaloopHost+":8088/interop/switch/v1";

    @When("^Demo In fsp \"([^\"]*)\" when I add user with the following details  MSISDN: \"([^\"]*)\" Full Name: \"([^\"]*)\" First Name: \"([^\"]*)\" Last Name: \"([^\"]*)\" DOB: \"([^\"]*)\"$")
    public void demoInFspWhenIAddUserWithTheFollowingDetailsMSISDNFullNameFirstNameLastNameDOB(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Demo User \"([^\"]*)\" should be successfully added$")
    public void demoUserShouldBeSuccessfullyAdded(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
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

    @When("^Demo Payer \"([^\"]*)\" with MSISDN \"([^\"]*)\" does a lookup for payee \"([^\"]*)\" with MSISDN \"([^\"]*)\"$")
    public void demoPayerWithMSISDNDoesALookupForPayeeWithMSISDN(String arg0, String arg1, String arg2, String arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Demo Payee \"([^\"]*)\" results should be returned\\. Expected values are First Name \"([^\"]*)\" Last Name \"([^\"]*)\" DOB \"([^\"]*)\"$")
    public void demoPayeeResultsShouldBeReturnedExpectedValuesAreFirstNameLastNameDOB(String arg0, String arg1, String arg2, String arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
