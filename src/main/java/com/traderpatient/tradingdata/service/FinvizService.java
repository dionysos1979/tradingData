package com.traderpatient.tradingdata.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Date;
import java.util.HashMap;

import com.traderpatient.tradingdata.model.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FinvizService {

	Logger logger = LoggerFactory.getLogger(FinvizService.class);

	public Document getDocument(String url) throws IOException {
		return Jsoup.connect(url).get();
	}

	public String getTitle(String url) throws IOException {
		return getDocument(url).title();
	}

	public String getTicker(String url) throws IOException {
		Document doc = getDocument(url);
		Element ticker = doc.getElementById("ticker");
		return ticker.text();
	}
		
	public Stock getStock(String url) throws IOException {
		Document doc = getDocument(url);
		Elements table = doc.getElementsByClass("fullview-title");
		Elements liens = table.select("a[href]");

		/**
		 * Stock infos : 1er tableau des données de finviz sur Pays, secteur etc ...
		 */
		Industry industry = new Industry();
		industry.setIndustry(((Element) liens.get(3)).text());

		Sector sector = new Sector();
		sector.setSector(((Element) liens.get(2)).text());
		industry.setSector(sector);

		Country country = new Country();
		country.setPays(((Element) liens.get(4)).text());

		Stock stock = new Stock();
		stock.setTicker(((Element) liens.get(0)).text());
		stock.setName(((Element) liens.get(1)).text());
		stock.setCountry(country);
		stock.setIndustry(industry);

		/**
		 * Stock data : tableau des données de finviz
		 */
		Earning earning = new Earning();
		HashMap<String, String> valeursTable = new HashMap<String, String>();
		
		Elements dataTable = doc.getElementsByClass("snapshot-table2");
		Elements rows = dataTable.select("tr");
		logger.info(" --- rows size " + rows.size());
		
		for (int i = 0; i < rows.size(); i++) { 
		    Element row = rows.get(i);
		    Elements cols = row.select("td");
			for (int j = 0; j < cols.size(); j++) { 
				if (j % 2 != 0) {
					valeursTable.put(cols.get(j-1).text(), cols.get(j).text());
				}
			}
		}
		earning.setData(valeursTable);
		earning.setStock(stock);
		
		stock.setEarning(earning);
		
		return stock;
	}

	public ArrayList<Stock> getData(ArrayList<String> tickers, String url) throws IOException {

		ArrayList<Stock> stocks = new ArrayList<Stock>();
		
		for (String ticker : tickers) {			
			stocks.add(getStock(url + ticker));
		}
		return stocks;
	}
	
	public static Double convertDigit(String str) {

		if (str.charAt(0) == '-') 
			return 0.0;
		
		if (str.charAt(str.length() - 1) == '%') 
			return Double.valueOf(str.substring(0, str.length() - 1));
		
		if (str.charAt(str.length() - 1) == 'B') 
			return Double.valueOf(str.substring(0, str.length() - 1)) * 1_000_000_000;

		if (str.charAt(str.length() - 1) == 'M') 
			return Double.valueOf(str.substring(0, str.length() - 1)) * 1_000_000;

		if (str.charAt(str.length() - 1) == 'K') 
			return Double.valueOf(str.substring(0, str.length() - 1)) * 1_000;
		
		return Double.valueOf(str.substring(0, str.length() - 1));		
	}
}
