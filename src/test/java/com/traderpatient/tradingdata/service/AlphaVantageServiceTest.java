package com.traderpatient.tradingdata.service;

import com.traderpatient.tradingdata.dao.AlphaVantage_QuarterlyReportRepository;
import com.traderpatient.tradingdata.exceptions.EarningsException;
import com.traderpatient.tradingdata.model.balanceSheet.AlphaVantage_QuarterlyReport;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
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
public class AlphaVantageServiceTest {

    Logger logger = LoggerFactory.getLogger(AlphaVantageServiceTest.class);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
    @Autowired
    private AlphaVantageService service;
    @Autowired
    private AlphaVantage_QuarterlyReportRepository quarterlyReportRepository;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.driver-class-name}")
    private String datasourceDriver;

    @Test
    public void contextLoads() {
        logger.info("MacrotrendsServiceTest.contextLoads()");
    }

    @Test
    public void getIncomeStatements() throws ParseException {
        service.saveIncomeStatements("CALM");
        logger.info("AlphaVantageServiceTest.getIncomeStatements:OK");
    }

    @Test
    public void getAllEarnings() throws ParseException, EarningsException {
        service.getAllEarnings();
        logger.info("AlphaVantageServiceTest.getAllEarnings:OK");
    }

    @Test
    public void getQuarterlyReport_LastQ() throws ParseException {
        List<AlphaVantage_QuarterlyReport> quarterlyReportList = quarterlyReportRepository.findAllByTicker("SSD");

        Collections.sort(quarterlyReportList, AlphaVantage_QuarterlyReport.SORT_BY_FISCAL_DATE);
        quarterlyReportList.stream().forEach(e -> System.out.println(e.toString()));
        AlphaVantage_QuarterlyReport quarterlyReport_Q_0 = quarterlyReportList.get(0);
        AlphaVantage_QuarterlyReport quarterlyReport_Q_1 = quarterlyReportList.get(1);
        AlphaVantage_QuarterlyReport quarterlyReport_Q_2 = quarterlyReportList.get(2);

        logger.info("Q : " + quarterlyReport_Q_0.getTotalRevenue() + " - " + quarterlyReport_Q_0.getFiscalDateEnding());
        logger.info("Q-1 : " + quarterlyReport_Q_1.getTotalRevenue() + " - " + quarterlyReport_Q_1.getFiscalDateEnding());
        logger.info("Q-2 : " + quarterlyReport_Q_2.getTotalRevenue() + " - " + quarterlyReport_Q_2.getFiscalDateEnding());
    }
}
