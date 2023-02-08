package com.traderpatient.tradingdata.service;


import com.traderpatient.tradingdata.dao.AlphaVantage_EarningsCalendarRepository;
import com.traderpatient.tradingdata.dao.BalanceSheetRepository;
import com.traderpatient.tradingdata.dao.CompteurRequeteRepository;
import com.traderpatient.tradingdata.model.AlphaVantage_BalanceSheetApi;
import com.traderpatient.tradingdata.model.balanceSheet.BalanceSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class BalanceSheetService extends ApiService{

    Logger logger = LoggerFactory.getLogger(BalanceSheetService.class);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

    private final BalanceSheetRepository balanceSheetRepository;
    private final AlphaVantage_EarningsCalendarRepository earningsCalendarRepository;
    private final EarningsCalendarService earningsCalendarService;
    private final MarketPlanningService marketPlanningService;

    private final AlphaVantageService alphaVantageService;

    @Autowired
    public BalanceSheetService(BalanceSheetRepository balanceSheetRepository,
                               AlphaVantage_EarningsCalendarRepository earningsCalendarRepository,
                               EarningsCalendarService earningsCalendarService,
                               MarketPlanningService marketPlanningService,
                               CompteurRequeteRepository compteurRepository,
                               AlphaVantageService alphaVantageService) {
        super(compteurRepository);
        this.balanceSheetRepository = balanceSheetRepository;
        this.earningsCalendarRepository = earningsCalendarRepository;
        this.earningsCalendarService = earningsCalendarService;
        this.marketPlanningService = marketPlanningService;
        this.alphaVantageService = alphaVantageService;
    }

    public List<BalanceSheet> saveAllFromAlphaVantage(String ticker) throws ParseException {
        logger.info("------------------------------- BALANCE SHEET -------------------------------");
        logger.info("0) Mise à jour Balance Sheet depuis [AlphaVantage] pour le ticker : " + ticker);

        String url = new String("https://www.alphavantage.co/query?function=BALANCE_SHEET&symbol="
                + ticker + "&apikey=" + AlphaVantageService.API_KEY);

        logger.info("1) Controle du nombre maximal de requetes pour " + AlphaVantageService.SITE_ALPHA_VANTAGE);
        // Controle du nombre maximal de requetes :
        if (isCompteurRequetesDepasse(AlphaVantageService.SITE_ALPHA_VANTAGE,
                AlphaVantageService.API_KEY,
                AlphaVantageService.SITE_ALPHA_VANTAGE_MAX_REQ_DAILY))
            return null;

        // Requete HTTP
        RestTemplate rest = new RestTemplate();
        AlphaVantage_BalanceSheetApi balanceSheetApi = rest.getForObject(url, AlphaVantage_BalanceSheetApi.class);
        List<BalanceSheet> toSaveAll = new ArrayList<>();

        logger.info("2) On n'ajoute que les balance sheets annuelles qui n'existent pas déjà");
        // On n'ajoute que les rapports annuels qui n'existent pas déjà
        List<BalanceSheet> annualBalanceSheetExistants = balanceSheetRepository.findAllByTickerAndFrequence(ticker, BalanceSheet.ANNUAL);
        List<BalanceSheet> annualBalanceSheets = balanceSheetApi.getAnnualReports();
        for (BalanceSheet annualBalanceSheet : annualBalanceSheets) {
            annualBalanceSheet.setTicker(ticker);
            annualBalanceSheet.setDate(formatter.parse(annualBalanceSheet.getFiscalDateEnding()));
            annualBalanceSheet.setDateMaj(new Date());
            annualBalanceSheet.setFrequence(BalanceSheet.ANNUAL);
            annualBalanceSheet.setDataProvider(AlphaVantageService.SITE_ALPHA_VANTAGE);
            if (!annualBalanceSheetExistants.stream().anyMatch(e -> formatter.format(e.getFiscalDateEnding())
                    .equals(formatter.format(annualBalanceSheet.getFiscalDateEnding()))))
                toSaveAll.add(annualBalanceSheet);
        }
        logger.info("2) Nb Balance sheets annuelles : " + toSaveAll.size());

        logger.info("3) On n'ajoute que les balance sheets trimestrielles qui n'existent pas déjà");
        // On n'ajoute que les rapports trimestrielles qui n'existent pas déjà
        List<BalanceSheet> quarterlyBalanceSheetExistants = balanceSheetRepository.findAllByTickerAndFrequence(ticker, BalanceSheet.QUARTERLY);
        List<BalanceSheet> quarterlyBalanceSheets = balanceSheetApi.getQuarterlyReports();
        for (BalanceSheet quarterlyBalanceSheet : quarterlyBalanceSheets) {
            quarterlyBalanceSheet.setTicker(ticker);
            quarterlyBalanceSheet.setDate(formatter.parse(quarterlyBalanceSheet.getFiscalDateEnding()));
            quarterlyBalanceSheet.setDateMaj(new Date());
            quarterlyBalanceSheet.setFrequence(BalanceSheet.QUARTERLY);
            quarterlyBalanceSheet.setDataProvider(AlphaVantageService.SITE_ALPHA_VANTAGE);
            if (!annualBalanceSheetExistants.stream().anyMatch(e -> formatter.format(e.getFiscalDateEnding())
                    .equals(formatter.format(quarterlyBalanceSheet.getFiscalDateEnding()))))
                toSaveAll.add(quarterlyBalanceSheet);
        }
        balanceSheetRepository.saveAll(toSaveAll);
        logger.info("3) Nb Balance sheets trimestrielles : " + toSaveAll.size());
        logger.info("4) Nb Balance sheets totales : " + toSaveAll.size());
        return toSaveAll;
    }
}
