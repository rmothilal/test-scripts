package stepdefs.p2p_transfer;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class P2PTransferGoldenPathStepdefs {
    @Given("^User \"([^\"]*)\" does not exist in the switch$")
    public void userDoesNotExistInTheSwitch(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I add the user \"([^\"]*)\" that exists in fsp \"([^\"]*)\"$")
    public void iAddTheUserThatExistsInFsp(String arg0, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I want to ensure that \"([^\"]*)\" is successfully added to the switch$")
    public void iWantToEnsureThatIsSuccessfullyAddedToTheSwitch(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^Payer \"([^\"]*)\" in Payer FSP \"([^\"]*)\" and Payee \"([^\"]*)\" in Payee FSP \"([^\"]*)\" exists in the switch$")
    public void payerInPayerFSPAndPayeeInPayeeFSPExistsInTheSwitch(String arg0, String arg1, String arg2, String arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^Payer \"([^\"]*)\" in Payer FSP \"([^\"]*)\" does a lookup for payee \"([^\"]*)\" that is in Payee FSP \"([^\"]*)\"$")
    public void payerInPayerFSPDoesALookupForPayeeThatIsInPayeeFSP(String arg0, String arg1, String arg2, String arg3) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Expected Payee \"([^\"]*)\" results should be returned$")
    public void expectedPayeeResultsShouldBeReturned(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^Payee \"([^\"]*)\" details are resolved$")
    public void payeeDetailsAreResolved(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^Payer FSP \"([^\"]*)\" sends the quote request$")
    public void payerFSPSendsTheQuoteRequest(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I should see Payee FSP fees and commission$")
    public void iShouldSeePayeeFSPFeesAndCommission() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^Payer \"([^\"]*)\" sends the transfer request$")
    public void payerSendsTheTransferRequest(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Transferred amount \"([^\"]*)\" should be debited from Payer's account$")
    public void transferredAmountShouldBeDebitedFromPayerSAccount(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("^Transferred amount \"([^\"]*)\" should be credited to Payee's account$")
    public void transferredAmountShouldBeCreditedToPayeeSAccount(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
