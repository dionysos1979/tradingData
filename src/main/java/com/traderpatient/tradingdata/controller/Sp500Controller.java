package com.traderpatient.tradingdata.controller;


import com.traderpatient.tradingdata.model.Indice;
import com.traderpatient.tradingdata.model.Polygon_Quote;
import com.traderpatient.tradingdata.service.YahooService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

@Controller
public class Sp500Controller {

	Logger logger = LoggerFactory.getLogger(Sp500Controller.class);

	@Autowired
	YahooService service;

	@GetMapping("/sp500")
	public String yahoo(Model model) throws IOException, ParseException {
		logger.info(" --- Requête = [http://localhost:8080/sp500/");

		String ticker = "";
		String url = new String("https://fr.finance.yahoo.com/quote/%5EGSPC/history?p=%5EGSPC");
		Indice stock = service.getHistoricalQuotesForIndice(url, "Standard & Poors 500", ticker);
		List<Polygon_Quote> listeTriee = stock.getHistoricalPrices();
		Collections.sort(listeTriee);

		model.addAttribute("ticker", ticker);
		model.addAttribute("listeTriee", listeTriee);

		Polygon_Quote today = (Polygon_Quote)listeTriee.get(0);
		Polygon_Quote weekly = (Polygon_Quote)listeTriee.get(5);
		Polygon_Quote monthly = (Polygon_Quote)listeTriee.get(20);
		Polygon_Quote monthly3 = (Polygon_Quote)listeTriee.get(60);

		model.addAttribute("rS1W", new Float( (today.getClose() / weekly.getClose() ) - 1));
		model.addAttribute("rS1M", new Float( (today.getClose() / monthly.getClose() ) - 1));
		model.addAttribute("rS3M", new Float( (today.getClose() / monthly3.getClose() ) - 1));

		logger.info(" --- stock.getHistoricalPrices().size =  " + stock.getHistoricalPrices().size());
		logger.info(" --- Requête = [http://localhost:8080/sp500/ --- [FIN] ");
		return "yahoo";
	}
}