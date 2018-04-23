package com.mojaloop.utils;

import io.restassured.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;


public class Utility{

    private static Logger logger = Logger.getLogger(Utility.class.getName());

    public static String getNewCorrelationId(){
        return UUID.randomUUID().toString();
    }

    public static String get(String endpoint, String fspiopSource, String fspiopDestination, String queryParam, TestRestTemplate restTemplate) throws Exception {
        String correlationId = getNewCorrelationId();
        Response raResponse =
                given()
                    .header("FSPIOP-Source",fspiopSource)
                    .header("X-Forwarded-For",correlationId)
                    .header("Content-Type", "application/json")
                .when()
                    .get(endpoint);

        Thread.sleep(2000);
        ResponseEntity<String> response = restTemplate.getForEntity("/correlationid/"+correlationId,String.class);
        return response.getBody();
    }

    public static int post( String endpoint, String fspiopSource, String fspiopDestination, String queryParam, String body, TestRestTemplate restTemplate) throws Exception{
        Response raResponse =
                given()
                    .body("{\"fspId\": \"test-dfsp1\",\"currency\": \"USD\"}")
                    .header("FSPIOP-Source",fspiopSource)
                    .header("Content-Type", "application/json")
                .when()
                    .post(endpoint);
        logger.info("post return status: "+raResponse.statusCode());
        return raResponse.statusCode();
    }

    public static int delete( String endpoint, String fspiopSource, String fspiopDestination, String queryParam, String body, TestRestTemplate restTemplate) throws Exception{
        Response raResponse =
                given()
                    .header("FSPIOP-Source",fspiopSource)
                    .header("Content-Type", "application/json")
                .when()
                    .delete(endpoint);
        logger.info("delete return status: "+raResponse.statusCode());
        return raResponse.statusCode();
    }
}
