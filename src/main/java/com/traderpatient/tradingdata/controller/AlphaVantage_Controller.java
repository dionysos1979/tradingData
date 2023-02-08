package com.traderpatient.tradingdata.controller;


import com.traderpatient.tradingdata.dao.AlphaVantage_AnnualEarningsRepository;
import com.traderpatient.tradingdata.dao.AlphaVantage_EarningsApiRepository;
import com.traderpatient.tradingdata.dao.AlphaVantage_QuarterlyEarningsRepository;
import com.traderpatient.tradingdata.exceptions.EarningsException;
import com.traderpatient.tradingdata.model.AlphaVantage_AnnualEarnings;
import com.traderpatient.tradingdata.model.AlphaVantage_EarningsApi;
import com.traderpatient.tradingdata.model.AlphaVantage_EarningsCalendar;
import com.traderpatient.tradingdata.model.AlphaVantage_QuarterlyEarnings;
import com.traderpatient.tradingdata.service.AlphaVantageService;
import com.traderpatient.tradingdata.service.EarningsCalendarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class AlphaVantage_Controller {

	Logger logger = LoggerFactory.getLogger(AlphaVantage_Controller.class);

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
	private final AlphaVantage_AnnualEarningsRepository annualEarningsRepository;
	private final AlphaVantage_QuarterlyEarningsRepository quarterlyEarningsRepository;
	private final EarningsCalendarService earningsCalendarService;
	private final AlphaVantageService alphaVantageService;

	@Autowired
	public AlphaVantage_Controller(AlphaVantage_EarningsApiRepository earningsApiRepository,
								   AlphaVantage_AnnualEarningsRepository annualEarningsRepository,
								   AlphaVantage_QuarterlyEarningsRepository quarterlyEarningsRepository,
								   AlphaVantageService alphaVantageService,
								   EarningsCalendarService earningsCalendarService) {
		this.annualEarningsRepository = annualEarningsRepository;
		this.quarterlyEarningsRepository = quarterlyEarningsRepository;
		this.earningsCalendarService = earningsCalendarService;
		this.alphaVantageService = alphaVantageService;
	}

	@GetMapping("/earnings")
	public String earnings(Model model, @RequestParam String ticker) throws IOException, ParseException, EarningsException {

		AlphaVantage_EarningsApi earnings = alphaVantageService.getEarnings(ticker);

		model.addAttribute("earnings", earnings);
		logger.info("return earnings");
		return "earnings";
	}

	@GetMapping("/earningsCalendar")
	public String earningsCalendar(Model model) throws IOException, ParseException {

		List<AlphaVantage_EarningsCalendar>  calendarList = earningsCalendarService.saveEarningsCalendar();

		model.addAttribute("earningsCalendar", calendarList);
		logger.info("return earningsCalendar");
		return "earningsCalendar";
	}

	@GetMapping("/deleteEarnings")
	public String deleteEarnings(Model model, @RequestParam String ticker) throws IOException, ParseException {

		List<AlphaVantage_AnnualEarnings> annualEarnings = annualEarningsRepository.findAllByTicker(ticker);
		if (annualEarnings == null || annualEarnings.size() == 0)
			logger.info("Pas de annual earnings trouvés pour : " + ticker);

		List<AlphaVantage_QuarterlyEarnings> quarterlyEarnings = quarterlyEarningsRepository.findAllByTicker(ticker);
		if (annualEarnings == null || annualEarnings.size() == 0)
			logger.info("Pas de quarterly earnings trouvés pour : " + ticker);

		annualEarningsRepository.deleteAll(annualEarnings);
		quarterlyEarningsRepository.deleteAll(quarterlyEarnings);

		model.addAttribute("earnings", null);
		logger.info("return earnings");
		return "earnings";
	}
}