package com.traderpatient.tradingdata.service;

import com.traderpatient.tradingdata.dao.Polygon_DailyQuoteRepository;
import com.traderpatient.tradingdata.dao.Polygon_QuoteRepository;
import com.traderpatient.tradingdata.dao.ScoreRepository;
import com.traderpatient.tradingdata.exceptions.CotationException;
import com.traderpatient.tradingdata.model.Polygon_DailyQuote;
import com.traderpatient.tradingdata.model.Polygon_Quote;
import com.traderpatient.tradingdata.model.Score;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
public class ScoreServiceTest {

    Logger logger = LoggerFactory.getLogger(ScoreServiceTest.class);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private Polygon_DailyQuoteRepository dailyQuoteRepository;

    @Autowired
    private Polygon_QuoteRepository quoteRepository;

    @Autowired
    private ScoreRepository scoreRepository;
    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.driver-class-name}")
    private String datasourceDriver;

    @Test
    public void contextLoads() {
        logger.info("ScoreServiceTest.contextLoads()");
    }

    /**
     * Juste un test sur le streams
     * @throws ParseException
     */
    @Test
    public void streamScore() throws ParseException {
        Polygon_DailyQuote dailyQuote = dailyQuoteRepository.findByDate(formatter.parse("2023-01-13"));
        List<Polygon_Quote> quoteListToday = quoteRepository.findAllByCotationId(dailyQuote.getCotationId());
        logger.info("quoteListToday:" + quoteListToday.size());

        Double closeMSFT = quoteListToday.stream()
                .filter(e -> e.getTicker().equals("MSFT"))
                .peek(e -> System.out.println("filter " + e.getTicker()))
                .findFirst().get().getClose();

        logger.info("closeMSFT:" + closeMSFT);

    }

    @Test
    public void initScore() throws ParseException, CotationException {
        logger.info("ScoreServiceTest.initScore()");
        logger.info("datasourceUrl:" + datasourceUrl);
        logger.info("datasourceDriver:" + datasourceDriver);
        assertThat(datasourceUrl.equals("jdbc:mysql://localhost:3306/canslim?createDatabaseIfNotExist=true"));
        assertThat(datasourceDriver.equals("com.mysql.cj.jdbc.Driver"));

        Assert.notNull(scoreService,  "scoreService mustn't be null");
        Assert.notNull(dailyQuoteRepository,  "dailyQuoteRepository mustn't be null");
        Assert.notNull(quoteRepository,  "quoteRepository mustn't be null");

        List<String> tickersList = Arrays.asList( "SSD", "TTC", "MSFT" );
        List<Score> scoreList = scoreService.initScore(formatter.parse("2023-01-20"), tickersList);
        Assert.notNull(scoreList,  "scoreList mustn't be null");
        Assert.notEmpty(scoreList,  "scoreList mustn't be empty");

        List<Score> scoreMSFT = scoreRepository.findAllByTicker("MSFT");
        Assert.notNull(scoreMSFT,  "scoreMSFT mustn't be null");

    }
}
