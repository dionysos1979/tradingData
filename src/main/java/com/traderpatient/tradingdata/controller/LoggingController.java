package com.traderpatient.tradingdata.controller;

import java.text.DateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class LoggingController {

    Logger logger = LoggerFactory.getLogger(LoggingController.class);

    @RequestMapping({"/", "/index", "/index.html"})
    public String index() {

		logger.info("Requête = [http://localhost:8080/index.html]");

        return "index";
    }
}