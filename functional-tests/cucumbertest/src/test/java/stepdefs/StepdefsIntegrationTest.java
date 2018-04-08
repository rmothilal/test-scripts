package stepdefs;

import cucumber.api.java.en.*;
import cucumber.api.java.en.Then;
import cucumber.api.PendingException;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static io.restassured.RestAssured.given;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class StepdefsIntegrationTest {

    RequestSpecification reqSpec;
    Response raResponse;

    String correlationId;

    String mojaloopHost = "52.208.201.14";
    String mojaloopUrl = "http://"+mojaloopHost+":8088/interop/switch/v1";

    private ResponseEntity<String> response; // output

//    @When("^the client calls /version$")
//    public void the_client_issues_GET_version() throws Throwable {
//        response = restTemplate.getForEntity("/version", String.class);
//
//    }
//
//    @Then("^the client receives status code of (\\d+)$")
//    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
//        HttpStatus currentStatusCode = response.getStatusCode();
//        assertThat("status code is incorrect : " +
//                response.getBody(), currentStatusCode.value(), is(statusCode));
//    }
//
//    @And("^the client receives server version (.+)$")
//    public void the_client_receives_server_version_body(String version) throws Throwable {
//        assertThat(response.getBody(), is(version));
//    }


//    @Given("^\"([^\"]*)\" does not exist in central directory$")
//    public void doesNotExistInCentralDirectory(String phNum) throws Throwable {
//         raResponse = when().get(mojaloopUrl + "/participants/MSISDN/" + phNum);
//         raResponse.then().statusCode(202);
//
//         Thread.sleep(1000);
//
//        assertThat("Is not true",true==true);
//
//
//    }
//
//    @When("^\"([^\"]*)\" is added in central directory$")
//    public void isAddedInCentralDirectory(String phNum) throws Throwable {
//        correlationId = getNewCorrelationId();
//        raResponse = given()
//                        .body("{\"name\":\"Murthy\"}")
//                        .header("X-Forwarded-For",correlationId)
//                     .when()
//                        .post(mojaloopUrl + "/participants/MSISDN/" + phNum);
//
//        assertThat(response.getStatusCode(),is(202));
//    }
//
//    @Then("^response should contain \"([^\"]*)\" name$")
//    public void responseShouldContainName(String dfsp) throws Throwable {
//        Thread.sleep(2000);
//        response = restTemplate.getForEntity("/correlationid/"+correlationId,String.class);
//
//        assertThat(response.getBody(),containsString(dfsp));
//    }
//
//    public static String getNewCorrelationId(){
//        return UUID.randomUUID().toString();
//    }
}
