package stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class TransactionEngineIntegrationTest extends SpringIntegrationTest {

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
}   
