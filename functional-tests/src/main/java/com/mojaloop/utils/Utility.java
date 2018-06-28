package com.mojaloop.utils;

import io.restassured.response.Response;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;


public class Utility {

    private static Logger logger = Logger.getLogger(Utility.class.getName());

    private static String simulatorUrl = "https://localhost:8444/";

    public static String getNewCorrelationId(){
        return UUID.randomUUID().toString();
    }

    public static MLResponse get(String endpoint, Map<String,String> headers, TestRestTemplate restTemplate) throws Exception {
        String correlationId = getNewCorrelationId();
        MLResponse response = new MLResponse();
        Response raResponse =
                given()
                        .header("Accept",((headers.get("Accept") != null) ? headers.get("Accept") : "" ))
                        .header("Content-Type", ((headers.get("Content-Type") != null) ? headers.get("Content-Type") : "" ))
                        .header("FSPIOP-Source",((headers.get("FSPIOP-Source") != null) ? headers.get("FSPIOP-Source") : "" ))
                        .header("FSPIOP-Destination",((headers.get("FSPIOP-Destination") != null) ? headers.get("FSPIOP-Destination") : "" ))
                        .header("X-Forwarded-For",correlationId)
                        .when()
                        .get(endpoint);

        response.setResponseCode(String.valueOf(raResponse.getStatusCode()));

        if(raResponse.getStatusCode() == 202) {
            Thread.sleep(2000);
            String corrEndpoint = simulatorUrl + "/payerfsp/correlationid/" + correlationId;
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(corrEndpoint, String.class);
            response.setResponseBody(responseEntity.getBody());
            return response;
        } else {
            response.setResponseBody(raResponse.getBody().asString());
            return response;
        }
    }

    public static MLResponse post(String endpoint, String body, Map<String,String> headers, TestRestTemplate restTemplate) throws Exception{
        String correlationId = getNewCorrelationId();
        MLResponse response = new MLResponse();
        Response raResponse =
                given()
                    .header("Accept",((headers.get("Accept") != null) ? headers.get("Accept") : "" ))
                    .header("Content-Type", ((headers.get("Content-Type") != null) ? headers.get("Content-Type") : "" ))
                    .header("FSPIOP-Source",((headers.get("FSPIOP-Source") != null) ? headers.get("FSPIOP-Source") : "" ))
                    .header("FSPIOP-Destination",((headers.get("FSPIOP-Destination") != null) ? headers.get("FSPIOP-Destination") : "" ))
                    .header("X-Forwarded-For",correlationId)
                    .body(body)
                .when()
                    .post(endpoint);

        if(raResponse.getStatusCode() == 202) {
            Thread.sleep(2000);
            String corrEndpoint = simulatorUrl + "/payerfsp/correlationid/" + correlationId;
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(corrEndpoint, String.class);
            response.setResponseBody(responseEntity.getBody());
            return response;
        } else {
            response.setResponseBody(raResponse.getBody().asString());
            return response;
        }
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

    public static TestRestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                return true;
            }
        };
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        TestRestTemplate restTemplate = new TestRestTemplate();
        restTemplate.getRestTemplate().setRequestFactory(requestFactory);
        return restTemplate;
    }

    public static String getLocalHostIp() throws IOException {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                whatismyip.openStream()));

        String ip = in.readLine(); //you get the IP as a String
        return ip;
    }
}
