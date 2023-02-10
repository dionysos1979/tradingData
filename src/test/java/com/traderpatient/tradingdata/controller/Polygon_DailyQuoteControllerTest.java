package com.traderpatient.tradingdata.controller;

import com.traderpatient.tradingdata.dao.Polygon_DailyQuoteRepository;
import com.traderpatient.tradingdata.dao.Polygon_QuoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @SpringBootTest :
 * tells Spring Boot to look for a main configuration class (one with @SpringBootApplication, for instance)
 * and use that to start a Spring application context.
 *
 * You can run this test in your IDE or on the command line (by running ./mvnw test or ./gradlew test), and it should pass.
 * To convince yourself that the context is creating your controller, you could add an assertion,
 * as the following example (from package com.traderPatient.controller.SmokeTest.java) shows:
 */
@WebMvcTest(Polygon_DailyQuoteController.class)
public class Polygon_DailyQuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Polygon_DailyQuoteRepository dailyQuoteRepository;

    @MockBean
    private Polygon_QuoteRepository quoteRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void controller() throws Exception {
        this.mockMvc.perform(get("/cotations?date=2023-01-13"))
            .andDo(print())
            .andExpect(status().isOk())
                .andExpect(content().string(containsString("<td><span>MSFT</span></td>")));
    }
}
