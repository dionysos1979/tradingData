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

        // On trie dans l'ordre inverse pour renvoyer le dernier objet (le + récent)
        List<Polygon_DailyQuote> sortedListe =
                liste.stream()
                        .sorted(dateComparator.reversed())
                        .collect(Collectors.toList());

        return sortedListe.get(0);
    }

    public Polygon_DailyQuote updateCotations(Date debut, Date fin) throws ParseException {
        if (fin.compareTo(debut) < 0) {
            logger.error("Merci de préciser une date de début < à date de fin : " + formatter.format(debut) + " > " + formatter.format(fin));
            return null;
        }
        // On récupère la journée suivante
        Date lastUpdateDate = marketPlanningService.getNextDayOpen(debut);

        Polygon_DailyQuote dailyQuote = new Polygon_DailyQuote();
        while (lastUpdateDate.compareTo(fin) < 0) {
            logger.info("Récupération des cotations pour la journée du : " + formatter.format(lastUpdateDate));
            dailyQuote = getDailyQuote(formatter.format(lastUpdateDate));
            lastUpdateDate = marketPlanningService.getNextDayOpen(lastUpdateDate);
        }
        return dailyQuote;
    }

    public Polygon_DailyQuote updateCotationsSincelastUpdateDay() throws ParseException {
        // Dernieres cotations récupérées en BDD
        Polygon_DailyQuote dailyQuote = getLastDailyQuote();
        Date lastUpdateDate = dailyQuote.getDate();
        logger.info("Dernier téléchargement des cotations en date du : " + formatter.format(lastUpdateDate));

        // date du jour
        Date today = new Date();
        logger.info("Date du jour : " + formatter.format(today));

        // On met à jour pour récupérer la journée suivante non téléchargée
        lastUpdateDate = marketPlanningService.getNextDayOpen(lastUpdateDate);

        while (lastUpdateDate.compareTo(today) < 0) {
            logger.info("Récupération des cotations pour la journée du : " + formatter.format(lastUpdateDate));
            dailyQuote = getDailyQuote(formatter.format(lastUpdateDate));
            lastUpdateDate = marketPlanningService.getNextDayOpen(lastUpdateDate);
        }

        return dailyQuote;
    }

    public Polygon_DailyQuote getDailyQuote(String inDate) throws ParseException {
        logger.info("------------------------------- COTATIONS -------------------------------");
        logger.info("0) Mise à jour cotation daily depuis le site Polygon pour la date : " + inDate);
        Polygon_DailyQuote cotationDuJour = new Polygon_DailyQuote();
        Date date = null;
        Date today = new Date();

        logger.info("1) On teste que cette date est au bon format");
        try {
            date = formatter.parse(inDate);
        } catch (ParseException e) {
            logger.error("Date en entrée pour le site Polygon n'est pas au bon format");
            cotationDuJour.setStatus("ERREUR - Mauvais format de date");
            return cotationDuJour;
        }

        logger.info("2) On teste que cette date n'est un jour de week-end ou de bourse fermée");
        if (!marketPlanningService.isMarketOpenDay(date)){
            logger.error("Date en entrée pour le site Polygon n'est pas un jour de bourse ouvré");
            cotationDuJour.setStatus("ERREUR - Date en entrée pour le site Polygon n'est pas un jour de bourse ouvré");
            return cotationDuJour;
        }

        logger.info("3) On teste que cette date n'est pas supérieure à la date du jour");
        if (date.after(new Date())){
            logger.error("Date en entrée pour le site Polygon n'est pas encore arrivée");
            cotationDuJour.setStatus("ERREUR - Date en entrée pour le site Polygon n'est pas encore arrivée");
            return cotationDuJour;
        }

        logger.info("4) On teste que cette date (" + formatter.format(date) + ") n'est pas égale à la date du jour : " + formatter.format(today));
        if ( formatter.format(date).equals(formatter.format(today)) ){
            logger.error("La séance du jour n'est pas encore terminée (EOD error)");
            cotationDuJour.setStatus("ERREUR - Séance du jour n'est pas encore terminée");
            return cotationDuJour;
        }

        logger.info("5) On teste que cette date n'existe pas déjà en BDD");
        List<Polygon_DailyQuote> dailyQuoteList = dailyQuoteRepository.findAllByDate(date);
        if (dailyQuoteList.size() > 0){
            Polygon_DailyQuote dailyQuote = dailyQuoteList.get(0);
            logger.info("Cette journée de bourse a déjà été téléchargée le " + dailyQuote.getMaj());
            List<Polygon_Quote> quoteList = quoteRepository.findAllByCotationId(dailyQuote.getCotationId());
            dailyQuote.setResults(quoteList);
            return dailyQuote;
        }

        logger.info("6) Controle du nombre maximal de requetes :");
        if (isCompteurRequetesDepasse(SITE_POLYGON, API_KEY, SITE_POLYGON_MAX_REQ_DAILY))
            return null;

        logger.info("7) On peut récupérer cette cotation du jour");
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

        logger.info("8) Récupérer aussi la cotation du jour des indices ! => à faire + tard");

        return cotationDuJour;
    }
}
