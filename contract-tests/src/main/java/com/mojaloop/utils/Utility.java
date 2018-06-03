/*****
 License
 --------------
 Copyright © 2017 Bill & Melinda Gates Foundation
 The Mojaloop files are made available by the Bill & Melinda Gates Foundation under the Apache License, Version 2.0 (the "License") and you may not use these files except in compliance with the License. You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, the Mojaloop files are distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 Contributors
 --------------
 This is the official list of the Mojaloop project contributors for this file.
 Names of the original copyright holders (individuals or organizations)
 should be listed with a '*' in the first column. People who have
 contributed from an organization can be listed under the organization
 that actually holds the copyright for their contributions (see the
 Gates Foundation organization for an example). Those individuals should have
 their names indented and be marked with a '-'. Email address can be added
 optionally within square brackets <email>.
 * Gates Foundation
 - Name Murthy Kakarlamudi <murthy@modusbox.com>
 --------------
 ******/

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

    public static MLResponse post(String endpoint, String body, Map<String,String> headers, Map<String,String> queryParams, TestRestTemplate restTemplate) throws Exception{
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
}
