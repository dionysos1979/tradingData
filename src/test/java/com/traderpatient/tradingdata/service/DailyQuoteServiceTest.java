package com.traderpatient.tradingdata.service;

import com.traderpatient.tradingdata.dao.Polygon_DailyQuoteRepository;
import com.traderpatient.tradingdata.dao.Polygon_QuoteRepository;
import com.traderpatient.tradingdata.model.Polygon_DailyQuote;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

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
public class DailyQuoteServiceTest {

    Logger logger = LoggerFactory.getLogger(DailyQuoteServiceTest.class);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
    @Autowired
    private Polygon_DailyQuoteService dailyQuoteService;

    @Autowired
    private Polygon_DailyQuoteRepository dailyQuoteRepository;

    @Autowired
    private Polygon_QuoteRepository quoteRepository;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.driver-class-name}")
    private String datasourceDriver;

    @Test
    public void contextLoads() {
        logger.info("DailyQuoteServiceTest.contextLoads()");
    }

    @Test
    public void getLastDailyQuoteTest() throws ParseException {
        logger.info("DailyQuoteServiceTest.getLastDailyQuoteTest()");
        logger.info("datasourceUrl:" + datasourceUrl);
        logger.info("datasourceDriver:" + datasourceDriver);
        assertThat(datasourceUrl.equals("jdbc:mysql://localhost:3306/canslim?createDatabaseIfNotExist=true"));
        assertThat(datasourceDriver.equals("com.mysql.cj.jdbc.Driver"));

        Assert.notNull(dailyQuoteService,  "dailyQuoteService mustn't be null");
        Assert.notNull(dailyQuoteRepository,  "dailyQuoteRepository mustn't be null");
        Assert.notNull(quoteRepository,  "quoteRepository mustn't be null");

        List<Polygon_DailyQuote> liste = (List<Polygon_DailyQuote>) dailyQuoteRepository.findAll();
        Assert.notNull(liste,  "listeFindAll mustn't be null");
        Assert.isTrue(liste.isEmpty(), "listeFindAll mustn't be empty");

        for (Polygon_DailyQuote dailyQuote : liste ) {
            System.out.println(dailyQuote.toString());
            logger.info("getLastDailyQuoteTest() : " + dailyQuote.toString());
        }

        Polygon_DailyQuote dailyQuote = dailyQuoteService.getLastDailyQuote();
        Assert.notNull(dailyQuote,  "dailyQuote mustn't be null");
        Assert.isInstanceOf(Polygon_DailyQuote.class, dailyQuote);
        assertThat(dailyQuote.getStatus().equals("OK"));
    }

    @Test
    public void updateCotationsSincelastUpdateDay() throws ParseException {
        dailyQuoteService.updateCotationsSincelastUpdateDay();
    }

    @Test
    public void updateCotations() throws ParseException {
        Date debut = formatter.parse("2023-01-26");
        Date fin = formatter.parse("2023-02-03");
        dailyQuoteService.updateCotations(debut, fin);
    }
}
