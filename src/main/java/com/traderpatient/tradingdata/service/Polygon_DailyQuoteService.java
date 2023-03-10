package com.traderpatient.tradingdata.service;

import com.traderpatient.tradingdata.dao.CompteurRequeteRepository;
import com.traderpatient.tradingdata.dao.Polygon_DailyQuoteRepository;
import com.traderpatient.tradingdata.dao.Polygon_QuoteRepository;
import com.traderpatient.tradingdata.model.Polygon_DailyQuote;
import com.traderpatient.tradingdata.model.Polygon_Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class Polygon_DailyQuoteService extends ApiService{

    Logger logger = LoggerFactory.getLogger(Polygon_DailyQuoteService.class);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

    public static final String SITE_POLYGON = "https://api.polygon.io";

    //SITE_POLYGON_MAX_REQ_DAILY = 5 API Calls / Minute
    public static final int SITE_POLYGON_MAX_REQ_DAILY = 200;
    public static final String API_KEY = "3bnw2ijhgaYNcpiiovqDIe5hVOr7FUuu";

    private final Polygon_DailyQuoteRepository dailyQuoteRepository;
    private final Polygon_QuoteRepository quoteRepository;
    private final MarketPlanningService marketPlanningService;

    @Autowired
    public Polygon_DailyQuoteService(Polygon_DailyQuoteRepository dailyQuoteRepository,
                                    Polygon_QuoteRepository quoteRepository,
                                     MarketPlanningService marketPlanningService,
                                     CompteurRequeteRepository compteurRepository) {
        super(compteurRepository);
        this.dailyQuoteRepository = dailyQuoteRepository;
        this.quoteRepository = quoteRepository;
        this.marketPlanningService = marketPlanningService;
    }

    public Polygon_DailyQuote getLastDailyQuote() throws ParseException {
        List<Polygon_DailyQuote> liste = (List<Polygon_DailyQuote>) dailyQuoteRepository.findAll();
        Comparator<Polygon_DailyQuote> dateComparator = (d1, d2) -> d1.getDate().compareTo(d2.getDate());

        // On trie dans l'ordre inverse pour renvoyer le dernier objet (le + r??cent)
        List<Polygon_DailyQuote> sortedListe =
                liste.stream()
                        .sorted(dateComparator.reversed())
                        .collect(Collectors.toList());

        return sortedListe.get(0);
    }

    public Polygon_DailyQuote updateCotations(Date debut, Date fin) throws ParseException {
        if (fin.compareTo(debut) < 0) {
            logger.error("Merci de pr??ciser une date de d??but < ?? date de fin : " + formatter.format(debut) + " > " + formatter.format(fin));
            return null;
        }
        // On r??cup??re la journ??e suivante
        Date lastUpdateDate = marketPlanningService.getNextDayOpen(debut);

        Polygon_DailyQuote dailyQuote = new Polygon_DailyQuote();
        while (lastUpdateDate.compareTo(fin) < 0) {
            logger.info("R??cup??ration des cotations pour la journ??e du : " + formatter.format(lastUpdateDate));
            dailyQuote = getDailyQuote(formatter.format(lastUpdateDate));
            lastUpdateDate = marketPlanningService.getNextDayOpen(lastUpdateDate);
        }
        return dailyQuote;
    }

    public Polygon_DailyQuote updateCotationsSincelastUpdateDay() throws ParseException {
        // Dernieres cotations r??cup??r??es en BDD
        Polygon_DailyQuote dailyQuote = getLastDailyQuote();
        Date lastUpdateDate = dailyQuote.getDate();
        logger.info("Dernier t??l??chargement des cotations en date du : " + formatter.format(lastUpdateDate));

        // date du jour
        Date today = new Date();
        logger.info("Date du jour : " + formatter.format(today));

        // On met ?? jour pour r??cup??rer la journ??e suivante non t??l??charg??e
        lastUpdateDate = marketPlanningService.getNextDayOpen(lastUpdateDate);

        while (lastUpdateDate.compareTo(today) < 0) {
            logger.info("R??cup??ration des cotations pour la journ??e du : " + formatter.format(lastUpdateDate));
            dailyQuote = getDailyQuote(formatter.format(lastUpdateDate));
            lastUpdateDate = marketPlanningService.getNextDayOpen(lastUpdateDate);
        }

        return dailyQuote;
    }

    public Polygon_DailyQuote getDailyQuote(String inDate) throws ParseException {
        logger.info("------------------------------- COTATIONS -------------------------------");
        logger.info("0) Mise ?? jour cotation daily depuis le site Polygon pour la date : " + inDate);
        Polygon_DailyQuote cotationDuJour = new Polygon_DailyQuote();
        Date date = null;
        Date today = new Date();

        logger.info("1) On teste que cette date est au bon format");
        try {
            date = formatter.parse(inDate);
        } catch (ParseException e) {
            logger.error("Date en entr??e pour le site Polygon n'est pas au bon format");
            cotationDuJour.setStatus("ERREUR - Mauvais format de date");
            return cotationDuJour;
        }

        logger.info("2) On teste que cette date n'est un jour de week-end ou de bourse ferm??e");
        if (!marketPlanningService.isMarketOpenDay(date)){
            logger.error("Date en entr??e pour le site Polygon n'est pas un jour de bourse ouvr??");
            cotationDuJour.setStatus("ERREUR - Date en entr??e pour le site Polygon n'est pas un jour de bourse ouvr??");
            return cotationDuJour;
        }

        logger.info("3) On teste que cette date n'est pas sup??rieure ?? la date du jour");
        if (date.after(new Date())){
            logger.error("Date en entr??e pour le site Polygon n'est pas encore arriv??e");
            cotationDuJour.setStatus("ERREUR - Date en entr??e pour le site Polygon n'est pas encore arriv??e");
            return cotationDuJour;
        }

        logger.info("4) On teste que cette date (" + formatter.format(date) + ") n'est pas ??gale ?? la date du jour : " + formatter.format(today));
        if ( formatter.format(date).equals(formatter.format(today)) ){
            logger.error("La s??ance du jour n'est pas encore termin??e (EOD error)");
            cotationDuJour.setStatus("ERREUR - S??ance du jour n'est pas encore termin??e");
            return cotationDuJour;
        }

        logger.info("5) On teste que cette date n'existe pas d??j?? en BDD");
        List<Polygon_DailyQuote> dailyQuoteList = dailyQuoteRepository.findAllByDate(date);
        if (dailyQuoteList.size() > 0){
            Polygon_DailyQuote dailyQuote = dailyQuoteList.get(0);
            logger.info("Cette journ??e de bourse a d??j?? ??t?? t??l??charg??e le " + dailyQuote.getMaj());
            List<Polygon_Quote> quoteList = quoteRepository.findAllByCotationId(dailyQuote.getCotationId());
            dailyQuote.setResults(quoteList);
            return dailyQuote;
        }

        logger.info("6) Controle du nombre maximal de requetes :");
        if (isCompteurRequetesDepasse(SITE_POLYGON, API_KEY, SITE_POLYGON_MAX_REQ_DAILY))
            return null;

        logger.info("7) On peut r??cup??rer cette cotation du jour");
        String url = new String("https://api.polygon.io/v2/aggs/grouped/locale/us/market/stocks/"+inDate+"?adjusted=true&apiKey=" + API_KEY);

        RestTemplate rest = new RestTemplate();
        cotationDuJour = rest.getForObject(url, Polygon_DailyQuote.class);
        cotationDuJour.setDate(date);
        cotationDuJour.setMaj(new Date());

        dailyQuoteRepository.save(cotationDuJour);

        List<Polygon_Quote> cotations = cotationDuJour.getResults();
        for (Polygon_Quote quote : cotations) {
            quote.setCotationId(cotationDuJour.getCotationId());
            quote.setDate(formatter.parse(inDate));
        }
        quoteRepository.saveAll(cotations);

        logger.info("8) R??cup??rer aussi la cotation du jour des indices ! => ?? faire + tard");

        return cotationDuJour;
    }
}
