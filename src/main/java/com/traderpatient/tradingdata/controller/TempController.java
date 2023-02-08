package com.traderpatient.tradingdata.controller;

import com.traderpatient.tradingdata.dao.Polygon_DailyQuoteRepository;
import com.traderpatient.tradingdata.model.Polygon_DailyQuote;
import com.traderpatient.tradingdata.service.Polygon_DailyQuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class TempController {

	Logger logger = LoggerFactory.getLogger(TempController.class);

	private final Polygon_DailyQuoteService dailyQuoteService;

	private final Polygon_DailyQuoteRepository dailyQuoteRepository;

	public TempController(Polygon_DailyQuoteService dailyQuoteService,
						  Polygon_DailyQuoteRepository dailyQuoteRepository) {
		this.dailyQuoteService = dailyQuoteService;
		this.dailyQuoteRepository = dailyQuoteRepository;
	}

	@GetMapping("/temp")
	public String temp(Model model) throws IOException, ParseException {

		// Toutes les cotations de la BDD
		List<Polygon_DailyQuote> cotations = (List<Polygon_DailyQuote>) dailyQuoteRepository.findAll();
		logger.info("findAll().size : " + cotations.size());
		cotations.sort((c1, c2) -> c1.getDate().compareTo(c2.getDate()));

		model.addAttribute("cotations", cotations);

		// La dernière cotation
		Polygon_DailyQuote last = dailyQuoteService.getLastDailyQuote();
		logger.info("getLastDailyQuote() : " + last.toString());

		// Test update dernière quotations :
		dailyQuoteService.updateCotationsSincelastUpdateDay();

		return "temp";
	}
}