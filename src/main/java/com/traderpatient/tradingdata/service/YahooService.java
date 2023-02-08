package com.traderpatient.tradingdata.service;

import com.traderpatient.tradingdata.dao.IndiceRepository;
import com.traderpatient.tradingdata.dao.Polygon_QuoteRepository;
import com.traderpatient.tradingdata.model.Indice;
import com.traderpatient.tradingdata.model.Polygon_Quote;
import com.traderpatient.tradingdata.model.Stock;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class YahooService {

	Logger logger = LoggerFactory.getLogger(YahooService.class);

	private final Polygon_QuoteRepository quoteRepository;
	private final IndiceRepository indiceRepository;

	public static final String TAG_HISTORIQUE_COURS = "W(100%) M(0)";

	@Autowired
	public YahooService(Polygon_QuoteRepository quoteRepository,
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
		
	public Stock getHistoricalPrices(String url, String ticker) throws IOException, ParseException {
		HtmlMappingService htmlMappingService = new HtmlMappingService();
		Stock stock = new Stock();
		stock.setTicker(ticker);

		logger.info(" --- getHistoricalPrices ");
		Document doc = getDocument(url);

		// Récupère l'historique de cotation sur l'action
		List<Object> listCotations = htmlMappingService.mapHtmlTableToObject(doc, TAG_HISTORIQUE_COURS, ticker);
		stock.setHistoricalPrices((List<Polygon_Quote>)(Object)listCotations);

		logger.info(" --- Historique récupérée pour le ticker : " + stock.getTicker());
		logger.info(" --- Nb Historique récupérée pour ce ticker : " + stock.getHistoricalPrices().size());

		return stock;
	}

	public Indice getHistoricalQuotesForIndice(String url, String name, String ticker) throws IOException, ParseException {
		HtmlMappingService htmlMappingService = new HtmlMappingService();
		Indice indice = indiceRepository.findByTicker(ticker);
		if (indice == null) {
			indice = new Indice(name, ticker);
			indiceRepository.save(indice);
		}
		List<Polygon_Quote> historiqueCotation = quoteRepository.findAllByTicker(ticker);
		indice.setHistoricalPrices(historiqueCotation);

		logger.info(" --- getHistoricalQuotesForIndice ");
		Document doc = getDocument(url);

		// Récupère l'historique de cotation sur l'action
		List<Polygon_Quote> listCotations = (List<Polygon_Quote>)(Object)htmlMappingService.mapHtmlTableToObject(doc, TAG_HISTORIQUE_COURS, ticker);
		indice.setHistoricalPrices((List<Polygon_Quote>)(Object)listCotations);

		// Mise à jour de l'historique des cours en BDD si nouvelle cotation
		for (Polygon_Quote quote : listCotations) {
			if (!indice.getHistoricalPrices().contains(quote)) {
				quoteRepository.save(quote);
			}
		}
		return indice;
	}

}
