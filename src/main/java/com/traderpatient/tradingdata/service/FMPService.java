package com.traderpatient.tradingdata.service;

import com.traderpatient.tradingdata.dao.CompteurRequeteRepository;
import com.traderpatient.tradingdata.dao.FMP_AnnualIncomeStatementRepository;
import com.traderpatient.tradingdata.model.FMP_AnnualIncomeStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class FMPService extends ApiService{

    Logger logger = LoggerFactory.getLogger(FMPService.class);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

    public static final String SITE_FMP = "https://financialmodelingprep.com";
    public static final int SITE_FMP_MAX_REQ_DAILY = 250;
        public static final String API_KEY = "1cd72908315816b7a47c1318f7c09292";
    private final FMP_AnnualIncomeStatementRepository annualIncomeStatementRepository;
    private final MarketPlanningService marketPlanningService;

    @Autowired
    public FMPService(FMP_AnnualIncomeStatementRepository annualIncomeStatementRepository,
                      MarketPlanningService marketPlanningService,
                      CompteurRequeteRepository compteurRepository) {
        super(compteurRepository);
        this.annualIncomeStatementRepository = annualIncomeStatementRepository;
        this.marketPlanningService = marketPlanningService;
    }

    public FMP_AnnualIncomeStatement getAnnualIncomeStatement(String ticker) throws ParseException {
        logger.info("------------------------------- ANNUAL INCOME STATEMENTS -------------------------------");
        logger.info("0) Mise à jour income statements annual depuis le site financialmodelingprep.com pour le ticker : " + ticker);
        FMP_AnnualIncomeStatement annualIncomeStatement = new FMP_AnnualIncomeStatement();
        Date date = null;
        Date today = new Date();

//        logger.info("1) On teste que cette date est au bon format");
//        try {
//            date = formatter.parse(inDate);
//        } catch (ParseException e) {
//            logger.error("Date en entrée pour le site Polygon n'est pas au bon format");
//            cotationDuJour.setStatus("ERREUR - Mauvais format de date");
//            return cotationDuJour;
//        }
//
//        logger.info("2) On teste que cette date n'est un jour de week-end ou de bourse fermée");
//        if (!marketPlanningService.isMarketOpenDay(date)){
//            logger.error("Date en entrée pour le site Polygon n'est pas un jour de bourse ouvré");
//            cotationDuJour.setStatus("ERREUR - Date en entrée pour le site Polygon n'est pas un jour de bourse ouvré");
//            return cotationDuJour;
//        }
//
//        logger.info("3) On teste que cette date n'est pas supérieure à la date du jour");
//        if (date.after(new Date())){
//            logger.error("Date en entrée pour le site Polygon n'est pas encore arrivée");
//            cotationDuJour.setStatus("ERREUR - Date en entrée pour le site Polygon n'est pas encore arrivée");
//            return cotationDuJour;
//        }
//
//        logger.info("4) On teste que cette date (" + formatter.format(date) + ") n'est pas égale à la date du jour : " + formatter.format(today));
//        if ( formatter.format(date).equals(formatter.format(today)) ){
//            logger.error("La séance du jour n'est pas encore terminée (EOD error)");
//            cotationDuJour.setStatus("ERREUR - Séance du jour n'est pas encore terminée");
//            return cotationDuJour;
//        }
//
//        logger.info("5) On teste que cette date n'existe pas déjà en BDD");
//        List<Polygon_DailyQuote> dailyQuoteList = dailyQuoteRepository.findAllByDate(date);
//        if (dailyQuoteList.size() > 0){
//            Polygon_DailyQuote dailyQuote = dailyQuoteList.get(0);
//            logger.info("Cette journée de bourse a déjà été téléchargée le " + dailyQuote.getMaj());
//            List<Polygon_Quote> quoteList = quoteRepository.findAllByCotationId(dailyQuote.getCotationId());
//            dailyQuote.setResults(quoteList);
//            return dailyQuote;
//        }

        logger.info("6) Controle du nombre maximal de requetes :");
        if (isCompteurRequetesDepasse(SITE_FMP, API_KEY, SITE_FMP_MAX_REQ_DAILY))
            return null;

        logger.info("7) On peut récupérer cet income statements");
        String url = new String("https://financialmodelingprep.com/api/v3/income-statement/" + ticker + "?limit=120&apikey=" + API_KEY);
/*
        RestTemplate rest = new RestTemplate();
        List<FMP_AnnualIncomeStatement> annualIncomeStatementList = rest.getForObject(url, FMP_AnnualIncomeStatement.class);

        for (FMP_AnnualIncomeStatement annualIncomeStatement : annualIncomeStatementList) {
            annualIncomeStatement.setDateMaj(new Date());
        }
        annualIncomeStatementRepository.saveAll(annualIncomeStatement);

        logger.info("8) Récupérer aussi la cotation du jour des indices ! => à faire + tard");
*/
        return null;
    }
}
