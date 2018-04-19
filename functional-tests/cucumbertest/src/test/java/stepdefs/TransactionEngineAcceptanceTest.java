package stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class TransactionEngineAcceptanceTest extends SpringAcceptanceTest {

    @Given("^Receiver \"([^\"]*)\" is resolved to \"([^\"]*)\" as part of lookup$")
    public void receiverIsResolvedToAsPartOfLookup(String arg0, String arg1) throws Throwable {
        //TODO
        Assert.assertFalse("This step needs to be implemented",false);
    }

    @When("^\"([^\"]*)\" in \"([^\"]*)\" sends a quote to \"([^\"]*)\" in \"([^\"]*)\"$")
    public void inSendsAQuoteToIn(String arg0, String arg1, String arg2, String arg3) throws Throwable {
        //TODO
        Assert.assertFalse("This step needs to be implemented",false);
    }

    @Then("^\"([^\"]*)\" fees and commission should come back in the response$")
    public void feesAndCommissionShouldComeBackInTheResponse(String arg0) throws Throwable {
        //TODO
        Assert.assertFalse("This step needs to be implemented",false);
    }

    @When("^Sending DFSP \"([^\"]*)\" sends a quote request with the SEND amount$")
    public void sendingDFSPSendsAQuoteRequestWithTheSENDAmount(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^fees and commission should be returned$")
    public void feesAndCommissionShouldBeReturned() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^Sending DFSP \"([^\"]*)\" sends a quote request with the RECEIVE amount$")
    public void sendingDFSPSendsAQuoteRequestWithTheRECEIVEAmount(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^A transfer was successful$")
    public void aTransferWasSuccessful() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^Receiving DFSP initiates a reverse transaction$")
    public void receivingDFSPInitiatesAReverseTransaction() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Original amount less fees should be returned to sending DFSP$")
    public void originalAmountLessFeesShouldBeReturnedToSendingDFSP() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
