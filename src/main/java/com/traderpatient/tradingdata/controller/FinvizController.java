package com.traderpatient.tradingdata.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import com.traderpatient.tradingdata.model.Stock;
import com.traderpatient.tradingdata.service.FinvizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FinvizController {

	Logger logger = LoggerFactory.getLogger(FinvizController.class);

	@Autowired
	FinvizService service;

	@GetMapping("/finviz")
	public String finviz(Model model, @RequestParam String ticker) throws IOException {
		logger.info(" --- Requête = [http://localhost:8080/finviz/");

		String url = new String("https://finviz.com/quote.ashx?t=" + ticker);
		Stock stock = service.getStock(url);

		// Affichage des earnings :
		HashMap<String, String> data = stock.getEarning().getData();
		for (HashMap.Entry<String, String> set : data.entrySet()) {
			System.out.println(set.getKey() + " = " + set.getValue());
		}

		System.out.printf(Locale.FRANCE, "Earning().getIncomeThisYear() :[%,f]\n",   stock.getEarning().getIncomeThisYear());
		System.out.printf(Locale.FRANCE, "Earning().getRevenueThisYear():[%,f]\n",   stock.getEarning().getRevenueThisYear());

		model.addAttribute("ticker", ticker);
		model.addAttribute("incomeThisYear", stock.getEarning().getIncomeThisYear());
		model.addAttribute("revenueThisYear", stock.getEarning().getRevenueThisYear());
		
		logger.info(" --- Requête = [http://localhost:8080/finviz/ --- [FIN] ");
		return "finviz";
	}
}