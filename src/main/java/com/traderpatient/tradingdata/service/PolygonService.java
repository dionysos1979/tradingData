package com.traderpatient.tradingdata.service;


import com.traderpatient.tradingdata.model.Polygon_Quote;
import com.traderpatient.tradingdata.model.Stock;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@Service
public class PolygonService {
	Logger logger = LoggerFactory.getLogger(PolygonService.class);

	public List getQuotationEOD(String day){
		return null;
	}

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

	public Stock getHistoricalPrices(String url) throws IOException, ParseException {
		Stock stock = new Stock();

		logger.info(" --- getHistoricalPrices ");
		Document doc = getDocument(url);

		// Récupère l'index du tableau de données sur l'action
		Elements table = doc.getElementsByClass("W(100%) M(0)");
		logger.info(" --- table size " + table.size());
		Elements rows = table.select("tr");
		logger.info(" --- rows size " + rows.size());

		/**
		 * Stock data : tableau des historique de cours de l'action
		 */
		for (int i = 1; i < rows.size(); i++) {
		    Element row = rows.get(i);

		    Elements cols = row.select("td");
			Polygon_Quote price = new Polygon_Quote();

			// On teste que la ligne contienne bien une ligne de cotation car il faut exclure les dividendes et autres événements
			try {
				if (cols.get(6).text() == null){
					logger.info(" --- Ligne vide pour le ticker : " + stock.getTicker());
				}
			} catch (IndexOutOfBoundsException e){
				logger.info(" --- Ligne suivante exclue pour le ticker : " + stock.getTicker());
				logger.info(" --- " + row.toString());
				continue;
			}
			// Date du cours
			SimpleDateFormat formatter = new SimpleDateFormat("d MMM y", Locale.FRANCE);
			price.setDate(formatter.parse(cols.get(0).text()));

			// Cours
			NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

			price.setOpen(Double.valueOf(format.parse(cols.get(1).text()).doubleValue()));
			price.setHigh(Double.valueOf(format.parse(cols.get(2).text()).doubleValue()));
			price.setLow(Double.valueOf(format.parse(cols.get(3).text()).doubleValue()));
			price.setClose(Double.valueOf(format.parse(cols.get(4).text()).doubleValue()));
			price.setClose(Double.valueOf(format.parse(cols.get(5).text()).doubleValue())); // +> setAdjustedClose
			price.setV(Double.valueOf(format.parse(cols.get(6).text()).doubleValue()));

			if (!stock.getHistoricalPrices().contains(price))
				stock.getHistoricalPrices().add(price);
		}
		logger.info(" --- Historique récupérée pour le ticker : " + stock.getTicker());
		logger.info(" --- Historique récupérée pour le ticker : " + stock.getHistoricalPrices().size());


		return stock;
	}

}
