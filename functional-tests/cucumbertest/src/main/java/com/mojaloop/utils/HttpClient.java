package com.mojaloop.utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class HttpClient {

    public static String get(String endpoint){
        Response raResponse =
                given()
                    .header("Content-Type", "application/json")
                .when()
                    .get(endpoint);

        return raResponse.then().extract().jsonPath().toString();
    }

    public static int post(String endpoint, String body){
        Response raResponse =
                given()
                    .header("Content-Type", "application/json")
                    .body(body)
                .when()
                    .post(endpoint);

        return raResponse.statusCode();
    }
}
