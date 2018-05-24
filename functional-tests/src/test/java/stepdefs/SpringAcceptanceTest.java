package stepdefs;

import com.mojaloop.MainApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest(classes = MainApplication.class,webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration()
public class SpringAcceptanceTest {

//    @Autowired
//    protected TestRestTemplate restTemplate;

}
