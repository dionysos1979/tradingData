package com.traderpatient.tradingdata.controller;


import com.traderpatient.tradingdata.dao.MarketHolidayRepository;
import com.traderpatient.tradingdata.model.MarketHoliday;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class MarketHolidayController {

	Logger logger = LoggerFactory.getLogger(MarketHolidayController.class);

	private final MarketHolidayRepository marketHolidayRepository;

	public MarketHolidayController(MarketHolidayRepository marketHolidayRepository) {
		this.marketHolidayRepository = marketHolidayRepository;
	}

	@GetMapping("/marketHoliday")
	public String marketHoliday(Model model) throws IOException, ParseException {

		String apiKey = "3bnw2ijhgaYNcpiiovqDIe5hVOr7FUuu";
		String url = new String("https://api.polygon.io/v1/marketstatus/upcoming?apiKey=" + apiKey);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<MarketHoliday[]> response = restTemplate.getForEntity(url, MarketHoliday[].class);

		List<MarketHoliday> marketHolidays = Arrays.asList(response.getBody());

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.FRANCE);
		marketHolidays
				.stream()
				.forEach(s -> s.setMaj(new Date()));

		marketHolidayRepository.saveAll(marketHolidays);

		model.addAttribute("marketHoliday", marketHolidays);
		logger.info("return marketHoliday");
		return "marketHoliday";
	}
}