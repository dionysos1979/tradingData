package com.traderpatient.tradingdata.controller;

import com.traderpatient.tradingdata.dao.IndiceRepository;
import com.traderpatient.tradingdata.model.Indice;
import com.traderpatient.tradingdata.model.Polygon_Quote;
import com.traderpatient.tradingdata.model.Stock;
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
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class YahooController {

	Logger logger = LoggerFactory.getLogger(YahooController.class);

	@Autowired
	YahooService service;

	@Autowired
	IndiceRepository indiceRepository;

	@GetMapping("/yahoo")
	public String yahoo(Model model, @RequestParam String ticker) throws IOException, ParseException {
		logger.info(" --- Requête = [http://localhost:8080/yahoo/" + ticker);

		Indice indice = null;
		// à supprimer
		if (ticker.equals("SP500")) {
			indice = indiceRepository.findByTicker("SP500");
			if (indice == null)
				indice = new Indice("Standard & Poors 500", "SP500");
			ticker = "%5EGSPC";
		}

		String url = new String("https://fr.finance.yahoo.com/quote/"+ ticker + "/history?p=" + ticker);
		List<Polygon_Quote> listeTriee = null;

		if (indice.getTicker().equals("SP500")) {
			indice = service.getHistoricalQuotesForIndice(url, indice.getName(), indice.getTicker());
			listeTriee = indice.getHistoricalPrices();
		} else {
			Stock stock = service.getHistoricalPrices(url, indice.getTicker());
			listeTriee = stock.getHistoricalPrices();
		}
		Collections.sort(listeTriee);

		model.addAttribute("ticker", indice.getTicker());
		model.addAttribute("listeTriee", listeTriee);

		Polygon_Quote today = (Polygon_Quote)listeTriee.get(0);
		Polygon_Quote weekly = (Polygon_Quote)listeTriee.get(5);
		Polygon_Quote monthly = (Polygon_Quote)listeTriee.get(20);
		Polygon_Quote monthly3 = (Polygon_Quote)listeTriee.get(60);

		model.addAttribute("rS1W", new Float( (today.getClose() / weekly.getClose() ) - 1));
		model.addAttribute("rS1M", new Float( (today.getClose() / monthly.getClose() ) - 1));
		model.addAttribute("rS3M", new Float( (today.getClose() / monthly3.getClose() ) - 1));

		logger.info(" --- stock.getHistoricalPrices().size =  " + listeTriee.size());
		logger.info(" --- Requête = [http://localhost:8080/yahoo/ --- [FIN] ");
		return "yahoo";
	}
}