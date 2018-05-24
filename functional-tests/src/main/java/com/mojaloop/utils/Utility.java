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

    private static String baseUrl = "https://localhost:8444/";

    public static String getNewCorrelationId(){
        return UUID.randomUUID().toString();
    }

    public static String get(String endpoint, String fspiopSource, String fspiopDestination, String queryParam, TestRestTemplate restTemplate) throws Exception {
        String correlationId = getNewCorrelationId();
        if(fspiopDestination == null) fspiopDestination = "";
        Response raResponse =
                given()
                    .header("FSPIOP-Source",fspiopSource)
                    .header("FSPIOP-Destination",fspiopDestination)
                    .header("X-Forwarded-For",correlationId)
                    .header("Content-Type", "application/json")
                .when()
                    .get(endpoint);

        Thread.sleep(2000);
        String corrEndpoint = baseUrl+fspiopSource+"/correlationid/"+correlationId;
        ResponseEntity<String> response = restTemplate.getForEntity(corrEndpoint,String.class);
        return response.getBody();
    }

    public static String post( String endpoint, String fspiopSource, String fspiopDestination, String queryParam, String body, TestRestTemplate restTemplate) throws Exception{
        String correlationId = getNewCorrelationId();
        if(fspiopDestination == null) fspiopDestination = "";
        Response raResponse =
                given()
                    .body(body)
                    .header("FSPIOP-Source",fspiopSource)
                    .header("FSPIOP-Destination",fspiopDestination)
                    .header("X-Forwarded-For",correlationId)
                    .header("Content-Type", "application/json")
                .when()
                    .post(endpoint);

        Thread.sleep(2000);
        String corrEndpoint = baseUrl+fspiopSource+"/correlationid/"+correlationId;
        ResponseEntity<String> response = restTemplate.getForEntity(corrEndpoint,String.class);
        return response.getBody();
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
