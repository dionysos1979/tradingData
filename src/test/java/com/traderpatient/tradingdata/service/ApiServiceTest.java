package com.traderpatient.tradingdata.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @SpringBootTest :
 * tells Spring Boot to look for a main configuration class (one with @SpringBootApplication, for instance)
 * and use that to start a Spring application context.
 *
 * You can run this test in your IDE or on the command line (by running ./mvnw test or ./gradlew test), and it should pass.
 * To convince yourself that the context is creating your controller, you could add an assertion,
 * as the following example (from package com.traderPatient.controller.SmokeTest.java) shows:
 *
 * ----------------------------------------------------------------------------------------------------------------
 *
 * Spring Boot provides @SpringBootTest annotation for Integration testing.
 * This annotation creates an application context and loads the full application context.
 *
 * @SpringBootTest will bootstrap the full application context,
 * which means we can @Autowire any bean that’s picked up by component scanning into our test.
 *
 * It starts the embedded server, creates a web environment, and then enables @Test methods to do integration testing.
 *
 * By default, @SpringBootTest does not start a server.
 * We need to add the attribute webEnvironment to further refine how your tests run. It has several options:
 *
 * MOCK(Default): Loads a web ApplicationContext and provides a mock web environment.
 * RANDOM_PORT: Loads a WebServerApplicationContext and provides a real web environment.
 *      The embedded server is started and listened to a random port.
 *      This is the one that should be used for the integration test.
 * DEFINED_PORT: Loads a WebServerApplicationContext and provides a real web environment.
 * NONE: Loads an ApplicationContext by using SpringApplication but does not provide any web environment.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ApiServiceTest {

    Logger logger = LoggerFactory.getLogger(ApiServiceTest.class);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
    @Autowired
    private AlphaVantageService service;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.driver-class-name}")
    private String datasourceDriver;

    @Test
    public void contextLoads() {
        logger.info("ApiServiceTest.contextLoads()");
    }

    @Test
    public void isCompteurRequetesDepasse() throws ParseException {
        service.isCompteurRequetesDepasse(AlphaVantageService.SITE_ALPHA_VANTAGE,
                AlphaVantageService.API_KEY,
                AlphaVantageService.SITE_ALPHA_VANTAGE_MAX_REQ_DAILY);
        service.incrementeCompteurRequetes(Polygon_DailyQuoteService.SITE_POLYGON,
                Polygon_DailyQuoteService.API_KEY,
                Polygon_DailyQuoteService.SITE_POLYGON_MAX_REQ_DAILY);
        logger.info("ApiServiceTest.isCompteurRequetesDepasse:OK");
    }
}
