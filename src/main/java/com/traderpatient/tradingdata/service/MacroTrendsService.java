package com.traderpatient.tradingdata.service;

import com.traderpatient.tradingdata.dao.IndiceRepository;
import com.traderpatient.tradingdata.dao.Polygon_QuoteRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@Service
public class MacroTrendsService {

	Logger logger = LoggerFactory.getLogger(MacroTrendsService.class);

	private final Polygon_QuoteRepository quoteRepository;
	private final IndiceRepository indiceRepository;

	public static final String TAG_HISTORIQUE_COURS = "historical_data_table table";

	@Autowired
	public MacroTrendsService(Polygon_QuoteRepository quoteRepository,
                              IndiceRepository indiceRepository) {
		this.quoteRepository = quoteRepository;
		this.indiceRepository = indiceRepository;
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
		
	public void getHistoricalRevenues(String ticker) throws IOException, ParseException {
		HtmlMappingService htmlMappingService = new HtmlMappingService();

		logger.info(" --- getHistoricalPrices ");
		Document doc = getDocument("https://www.macrotrends.net/stocks/charts/TSLA/tesla/revenue");

		// Récupère l'historique de cotation sur l'action
		List<HashMap<String, String>> listCotations =
				(List<HashMap<String, String>>) (Object)htmlMappingService.mapHtmlTableToObject(doc, TAG_HISTORIQUE_COURS, ticker);

	}


}
