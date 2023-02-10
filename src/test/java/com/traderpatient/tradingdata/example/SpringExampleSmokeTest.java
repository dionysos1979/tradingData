package com.traderpatient.tradingdata.example;

import com.traderpatient.tradingdata.controller.FinvizController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Spring interprets the @Autowired annotation, and the controller is injected before the test methods are run.
 * We use AssertJ (which provides assertThat() and other methods) to express the test assertions.
 */
@SpringBootTest
public class SpringExampleSmokeTest {
    @Autowired
    private FinvizController controller;

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}
