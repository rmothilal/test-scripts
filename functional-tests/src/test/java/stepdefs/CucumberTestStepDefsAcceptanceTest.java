package stepdefs;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class CucumberTestStepDefsAcceptanceTest extends SpringAcceptanceTest {

    @When("^I add correlation id \"([^\"]*)\"$")
    public void iAddCorrelationId(String correlationId) throws Throwable {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String reqJson = "{\"correlationId\":\""+correlationId+"\"}";
        HttpEntity<String> entity = new HttpEntity<String>(reqJson,headers);

        ResponseEntity responseEntity = restTemplate.postForEntity("/correlationid",entity,String.class);

        assertThat(responseEntity.getStatusCodeValue(), is(200));
    }

    @Then("^I query for the correlation id \"([^\"]*)\" I should see it$")
    public void iQueryForTheCorrelationIdIShouldSeeIt(String correlationId) throws Throwable {
        ResponseEntity responseEntity = restTemplate.getForEntity("/correlationid/"+correlationId,String.class);

        assertThat("Response does not contain correlationid: "+ responseEntity.getBody(),containsString(correlationId));
    }
}
