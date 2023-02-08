package com.traderpatient.tradingdata.controller;


import com.traderpatient.tradingdata.dao.Polygon_DailyQuoteRepository;
import com.traderpatient.tradingdata.dao.Polygon_QuoteRepository;
import com.traderpatient.tradingdata.model.Polygon_DailyQuote;
import com.traderpatient.tradingdata.model.Polygon_Quote;
import com.traderpatient.tradingdata.service.Polygon_DailyQuoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class Polygon_DailyQuoteController {

	Logger logger = LoggerFactory.getLogger(Polygon_DailyQuoteController.class);

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
	private final Polygon_DailyQuoteRepository dailyQuoteRepository;
	private final Polygon_QuoteRepository quoteRepository;

	private final Polygon_DailyQuoteService polygon_dailyQuoteService;

	public Polygon_DailyQuoteController(Polygon_DailyQuoteRepository dailyQuoteRepository,
							   			Polygon_QuoteRepository quoteRepository,
										Polygon_DailyQuoteService polygon_dailyQuoteService) {
		this.dailyQuoteRepository = dailyQuoteRepository;
		this.quoteRepository = quoteRepository;
		this.polygon_dailyQuoteService = polygon_dailyQuoteService;
	}

	@GetMapping("/cotations")
	public String cotations(Model model, @RequestParam String date) throws IOException, ParseException {

		Polygon_DailyQuote cotationDuJour = polygon_dailyQuoteService.getDailyQuote(date);

		model.addAttribute("cotations", cotationDuJour);
		logger.info("return cotations");
		return "cotations";
	}

	@GetMapping("/updateCotations")
	public String updateCotations(Model model) throws IOException, ParseException {
		Polygon_DailyQuote cotationDuJour = polygon_dailyQuoteService.updateCotationsSincelastUpdateDay();
		model.addAttribute("cotations", cotationDuJour);
		logger.info("return cotations");
		return "cotations";
	}

	@GetMapping("/deleteCotations")
	public String deleteCotations(Model model, @RequestParam String date) throws IOException, ParseException {
		Date dateToDelete = formatter.parse(date);

		List<Polygon_DailyQuote> cotationDuJour = dailyQuoteRepository.findAllByDate(dateToDelete);
		if (cotationDuJour == null || cotationDuJour.size() == 0)
			logger.info("Pas de cotations trouvées pour : " + date);

		for (Polygon_DailyQuote dailyQuote : cotationDuJour) {
			List<Polygon_Quote> quoteList = quoteRepository.findAllByCotationId(dailyQuote.getCotationId());
			quoteRepository.deleteAll(quoteList);
			dailyQuoteRepository.delete(dailyQuote);
		}
		model.addAttribute("cotations", cotationDuJour);
		logger.info("return cotations");
		return "cotations";
	}
}