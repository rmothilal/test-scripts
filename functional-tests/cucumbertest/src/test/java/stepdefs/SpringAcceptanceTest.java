package stepdefs;

import java.util.Collections;

import com.mojaloop.MainApplication;
import cucumber.api.java.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;


@SpringBootTest(classes = MainApplication.class,webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration()
//@WebAppConfiguration
public class SpringAcceptanceTest {

    @Autowired
    protected TestRestTemplate restTemplate;



}
