package com.traderpatient.tradingdata.service;

import com.traderpatient.tradingdata.dao.AlphaVantage_EarningsCalendarRepository;
import com.traderpatient.tradingdata.dao.CompteurRequeteRepository;
import com.traderpatient.tradingdata.dao.IncomeStatementRepository;
import com.traderpatient.tradingdata.model.AlphaVantage_IncomeStatementApi;
import com.traderpatient.tradingdata.model.FMP_AnnualIncomeStatement;
import com.traderpatient.tradingdata.model.balanceSheet.AlphaVantage_AnnualReport;
import com.traderpatient.tradingdata.model.balanceSheet.AlphaVantage_QuarterlyReport;
import com.traderpatient.tradingdata.model.incomeStatement.IncomeStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class IncomeStatementService extends ApiService{

    Logger logger = LoggerFactory.getLogger(IncomeStatementService.class);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

    private final IncomeStatementRepository incomeStatementRepository;
    private final AlphaVantage_EarningsCalendarRepository earningsCalendarRepository;
    private final EarningsCalendarService earningsCalendarService;
    private final MarketPlanningService marketPlanningService;
    private final AlphaVantageService alphaVantageService;

    @Autowired
    public IncomeStatementService(IncomeStatementRepository incomeStatementRepository,
                                  AlphaVantage_EarningsCalendarRepository earningsCalendarRepository,
                                  EarningsCalendarService earningsCalendarService,
                                  MarketPlanningService marketPlanningService,
                                  CompteurRequeteRepository compteurRepository,
                                  AlphaVantageService alphaVantageService) {
        super(compteurRepository);
        this.incomeStatementRepository = incomeStatementRepository;
        this.earningsCalendarRepository = earningsCalendarRepository;
        this.earningsCalendarService = earningsCalendarService;
        this.marketPlanningService = marketPlanningService;
        this.alphaVantageService = alphaVantageService;
    }

    public List<IncomeStatement> saveAllFromAlphaVantage(String ticker) throws ParseException {
        logger.info("------------------------------- INCOME STATEMENT -------------------------------");
        logger.info("0) Mise à jour Income Statements depuis [AlphaVantage] pour le ticker : " + ticker);

        String url = new String("https://www.alphavantage.co/query?function=INCOME_STATEMENT&symbol="
                + ticker + "&apikey=" + AlphaVantageService.API_KEY);

        logger.info("1) Controle du nombre maximal de requetes pour " + AlphaVantageService.SITE_ALPHA_VANTAGE);
        // Controle du nombre maximal de requetes :
        if (isCompteurRequetesDepasse(AlphaVantageService.SITE_ALPHA_VANTAGE,
                AlphaVantageService.API_KEY,
                AlphaVantageService.SITE_ALPHA_VANTAGE_MAX_REQ_DAILY))
            return null;

        // Requete HTTP
        RestTemplate rest = new RestTemplate();
        AlphaVantage_IncomeStatementApi incomeStatementApi = rest.getForObject(url, AlphaVantage_IncomeStatementApi.class);
        List<IncomeStatement> toSaveAll = new ArrayList<>();

        logger.info("1) On n'ajoute que les Income Statements annuels qui n'existent pas déjà");
        // On n'ajoute que les Income Statements annuels qui n'existent pas déjà
        List<IncomeStatement> annualIncomeStatementExistants = incomeStatementRepository.findAllByTickerAndFrequence(ticker, IncomeStatement.ANNUAL);
        List<AlphaVantage_AnnualReport> annualReports = incomeStatementApi.getAnnualReports();
        for (AlphaVantage_AnnualReport annualReport : annualReports) {
            IncomeStatement incomeStatement = new IncomeStatement(annualReport);
            incomeStatement.setFiscalDateEnding( // On met la fin de mois parce que Fiscal date FMP != Fiscal date AlphaVantage
                    formatter.format(marketPlanningService.getLastDayOfMonth(annualReport.getFiscalDateEnding())));
            incomeStatement.setTicker(ticker);
            incomeStatement.setDate(annualReport.getFiscalDateEnding());
            incomeStatement.setDateMaj(new Date());
            incomeStatement.setFrequence(IncomeStatement.ANNUAL);
            incomeStatement.setDataProvider(AlphaVantageService.SITE_ALPHA_VANTAGE);

            if (!annualIncomeStatementExistants.stream().anyMatch(e -> e.getFiscalDateEnding()
                    .equals(incomeStatement.getFiscalDateEnding())))
                toSaveAll.add(incomeStatement);
            logger.info(incomeStatement.toString());
        }
        logger.info("2) Nb Income Statements annuels : " + toSaveAll.size());

        logger.info("3) On n'ajoute que les Income Statements trimestriels qui n'existent pas déjà");
        // On n'ajoute que les Income Statements trimestriels qui n'existent pas déjà
        List<IncomeStatement> quarterlyIncomeStatementExistants = incomeStatementRepository.findAllByTickerAndFrequence(ticker, IncomeStatement.QUARTERLY);
        List<AlphaVantage_QuarterlyReport> quarterlyReports = incomeStatementApi.getQuarterlyReports();
        for (AlphaVantage_QuarterlyReport quarterlyReport : quarterlyReports) {
            IncomeStatement incomeStatement = new IncomeStatement(quarterlyReport);
            incomeStatement.setFiscalDateEnding( // On met la fin de mois parce que Fiscal date FMP != Fiscal date AlphaVantage
                    formatter.format(marketPlanningService.getLastDayOfMonth(quarterlyReport.getFiscalDateEnding())));
            incomeStatement.setTicker(ticker);
            incomeStatement.setDate(quarterlyReport.getFiscalDateEnding());
            incomeStatement.setDateMaj(new Date());
            incomeStatement.setFrequence(IncomeStatement.QUARTERLY);
            incomeStatement.setDataProvider(AlphaVantageService.SITE_ALPHA_VANTAGE);

            if (!quarterlyIncomeStatementExistants.stream().anyMatch(e -> e.getFiscalDateEnding()
                    .equals(incomeStatement.getFiscalDateEnding())))
                toSaveAll.add(incomeStatement);
        }
        incomeStatementRepository.saveAll(toSaveAll);
        logger.info("4) save() : Nb Income Statements trimestrielles : " + toSaveAll.size());
        logger.info("5) save() : Nb Income Statements totales : " + toSaveAll.size());
        return toSaveAll;
    }

    public List<IncomeStatement> saveAllAnnualFromFMP(String ticker) throws ParseException {
        logger.info("------------------------------- INCOME STATEMENT - Annual -------------------------------");
        logger.info("0) Mise à jour Income Statements Annuels depuis [FMP] pour le ticker : " + ticker);

        String url = new String("https://financialmodelingprep.com/api/v3/income-statement/"
                + ticker + "?limit=120&apikey=" + FMPService.API_KEY);

        logger.info("1) Controle du nombre maximal de requetes pour " + FMPService.SITE_FMP);
        // Controle du nombre maximal de requetes :
        if (isCompteurRequetesDepasse(FMPService.SITE_FMP,
                FMPService.API_KEY,
                FMPService.SITE_FMP_MAX_REQ_DAILY))
            return null;

        // Requete HTTP
        RestTemplate rest = new RestTemplate();
        FMP_AnnualIncomeStatement[] incomeStatementApiList = rest.getForObject(url, FMP_AnnualIncomeStatement[].class);
        List<IncomeStatement> toSaveAll = new ArrayList<>();

        logger.info("2) On n'ajoute que les Income Statements annuels qui n'existent pas déjà");
        // On n'ajoute que les Income Statements annuels qui n'existent pas déjà
        List<IncomeStatement> annualIncomeStatementExistants = incomeStatementRepository.findAllByTickerAndFrequence(ticker, IncomeStatement.ANNUAL);
        List<FMP_AnnualIncomeStatement> annualIncomeStatements = Arrays.asList(incomeStatementApiList);
        for (FMP_AnnualIncomeStatement annualReport : annualIncomeStatements) {
            IncomeStatement incomeStatement = new IncomeStatement(annualReport);
            incomeStatement.setFiscalDateEnding( // On met la fin de mois parce que Fiscal date FMP != Fiscal date AlphaVantage
                    formatter.format(marketPlanningService.getLastDayOfMonth(formatter.parse(annualReport.getDate()))));
            // Avec FMP : on force la date
            incomeStatement.setTicker(ticker);
            incomeStatement.setDate(formatter.parse(incomeStatement.getFiscalDateEnding()));
            incomeStatement.setDateMaj(new Date());
            incomeStatement.setFrequence(IncomeStatement.ANNUAL);
            incomeStatement.setDataProvider(FMPService.SITE_FMP);

            if (!annualIncomeStatementExistants
                    .stream()
                    .anyMatch(e -> e.getFiscalDateEnding()
                        .equals(incomeStatement.getFiscalDateEnding())))
                toSaveAll.add(incomeStatement);
        }

        incomeStatementRepository.saveAll(toSaveAll);
        logger.info("3) Nb Income Statements totales : " + toSaveAll.size());
        return toSaveAll;
    }
}
