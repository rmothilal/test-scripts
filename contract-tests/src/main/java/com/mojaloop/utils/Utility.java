package com.mojaloop.utils;

import io.restassured.response.Response;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.SSLContext;
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

    public static MLResponse get(String endpoint, Map<String,String> headers, Map<String,String> queryParams, TestRestTemplate restTemplate) throws Exception {
        String correlationId = getNewCorrelationId();
        MLResponse response = new MLResponse();
        Response raResponse =
                given()
                    .header("Accept",headers.get("Accept"))
                    .header("Content-Type", "application/json")
                    .header("FSPIOP-Source",headers.get("FSPIOP-Source"))
                    .header("FSPIOP-Destination",((headers.get("FSPIOP-Destination") != null) ? headers.get("FSPIOP-Destination") : "" ))
                    .header("X-Forwarded-For",correlationId)
                .when()
                    .get(endpoint);

        response.setResponseCode(String.valueOf(raResponse.getStatusCode()));

        Thread.sleep(2000);
        String corrEndpoint = simulatorUrl+"/payerfsp/correlationid/"+correlationId;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(corrEndpoint,String.class);
        response.setResponseBody(responseEntity.getBody());
        return response;
    }

    public static MLResponse post(String endpoint, String body, Map<String,String> headers, Map<String,String> queryParams, TestRestTemplate restTemplate) throws Exception{
        String correlationId = getNewCorrelationId();
        MLResponse response = new MLResponse();
        Response raResponse =
                given()
                    .header("Accept",headers.get("Accept"))
                    .header("Content-Type", "application/json")
                    .header("FSPIOP-Source",headers.get("FSPIOP-Source"))
                    .header("FSPIOP-Destination",((headers.get("FSPIOP-Destination") != null) ? headers.get("FSPIOP-Destination") : "" ))
                    .header("X-Forwarded-For",correlationId)
                    .body(body)
                .when()
                    .post(endpoint);

        Thread.sleep(2000);
        String corrEndpoint = "/payerfsp/correlationid/"+correlationId;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(corrEndpoint,String.class);
        response.setResponseBody(responseEntity.getBody());
        return response;
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
}
