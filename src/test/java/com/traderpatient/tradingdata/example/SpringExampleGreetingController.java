package com.traderpatient.tradingdata.example;

import com.traderpatient.tradingdata.controller.GreetingController;
import com.traderpatient.tradingdata.service.GreetingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 *  We could make it more realistic by introducing an extra component to store the greeting (perhaps in a new controller).
 *  The following example (from src/main/java/com/example/testingweb/GreetingController.java) shows how to do so:
 *
 *  We use @MockBean to create and inject a mock for the GreetingService
 *  (if you do not do so, the application context cannot start), and we set its expectations using Mockito.
 */
@WebMvcTest(GreetingController.class)
public class SpringExampleGreetingController {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Spring automatically injects the service dependency into the controller (because of the constructor signature).
     */
    @MockBean
    private GreetingService service;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        when(service.greet()).thenReturn("Hello, Vincent !");

        this.mockMvc.perform(get("/greeting"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, Vincent !")));
    }
}
