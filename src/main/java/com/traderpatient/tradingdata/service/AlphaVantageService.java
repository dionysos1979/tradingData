package com.traderpatient.tradingdata.service;

import com.traderpatient.tradingdata.dao.*;
import com.traderpatient.tradingdata.exceptions.EarningsException;
import com.traderpatient.tradingdata.model.*;
import com.traderpatient.tradingdata.model.balanceSheet.AlphaVantage_AnnualReport;
import com.traderpatient.tradingdata.model.balanceSheet.AlphaVantage_QuarterlyReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AlphaVantageService extends ApiService {
    public static  final String API_KEY = "ZNDFDHN53PUFCLLT";
    public static  final String SITE_ALPHA_VANTAGE = "https://www.alphavantage.co";
    public static  final int SITE_ALPHA_VANTAGE_MAX_REQ_DAILY = 200;

    Logger logger = LoggerFactory.getLogger(AlphaVantageService.class);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
    private AlphaVantage_EarningsApiRepository earningsApiRepository;
    private AlphaVantage_QuarterlyEarningsRepository quarterlyEarningsRepository;
    private AlphaVantage_AnnualEarningsRepository annualEarningsRepository;
    private AlphaVantage_EarningsCalendarRepository earningsCalendarRepository;
    private AlphaVantage_AnnualReportRepository annualReportRepository;
    private AlphaVantage_QuarterlyReportRepository quarterlyReportRepository;
    public AlphaVantageService(AlphaVantage_EarningsApiRepository earningsApiRepository,
                             AlphaVantage_QuarterlyEarningsRepository quarterlyEarningsRepository,
                             AlphaVantage_AnnualEarningsRepository annualEarningsRepository,
                               AlphaVantage_EarningsCalendarRepository earningsCalendarRepository,
                               AlphaVantage_AnnualReportRepository annualReportRepository,
                               AlphaVantage_QuarterlyReportRepository quarterlyReportRepository,
                               CompteurRequeteRepository compteurRepository) {
        super(compteurRepository);
        this.earningsApiRepository = earningsApiRepository;
        this.quarterlyEarningsRepository = quarterlyEarningsRepository;
        this.annualEarningsRepository = annualEarningsRepository;
        this.earningsCalendarRepository = earningsCalendarRepository;
        this.annualReportRepository = annualReportRepository;
        this.quarterlyReportRepository = quarterlyReportRepository;
    }

    public AlphaVantage_EarningsApi getEarnings(String ticker) throws ParseException, EarningsException {
        logger.info("------------------------------- EARNINGS - AlphaVantage -------------------------------");
        logger.info("0) Mise à jour EPS depuis le site Alpha Vantage pour le ticker : " + ticker);
        String url = new String("https://www.alphavantage.co/query?function=EARNINGS&symbol=" + ticker + "&apikey=" + API_KEY);

        // Controle du nombre maximal de requetes :
        if (isCompteurRequetesDepasse(SITE_ALPHA_VANTAGE, API_KEY, SITE_ALPHA_VANTAGE_MAX_REQ_DAILY))
            return null;

        logger.info("1) Récupération des données API XML");
        RestTemplate rest = new RestTemplate();
        AlphaVantage_EarningsApi earnings = rest.getForObject(url, AlphaVantage_EarningsApi.class);

        if (earnings.getAnnualEarnings() == null
                || earnings.getQuarterlyEarnings() == null
                || earnings.getTicker() == null) {
            logger.warn("Pas de earnings retournés pour cette API pour le ticker : " + ticker);
            return null;
        }

        logger.info("2) Récupération des ANNUAL earnings");
        // Annual Earnings
        List<AlphaVantage_AnnualEarnings> annualEarningsExistants = annualEarningsRepository.findAllByTicker(ticker);
        List<AlphaVantage_AnnualEarnings> annualEarnings = earnings.getAnnualEarnings();
        for (AlphaVantage_AnnualEarnings annualEarning : annualEarnings) {
            annualEarning.setTicker(ticker);
            annualEarning.setDate(annualEarning.getFiscalDateEnding());
            annualEarning.setDateMaj(new Date());
            if (!annualEarningsExistants.stream().anyMatch(e -> formatter.format(e.getFiscalDateEnding())
                    .equals(formatter.format(annualEarning.getFiscalDateEnding()))))
                annualEarningsRepository.save(annualEarning);
            else
                logger.warn("ANNUAL earnings déjà existant du " + annualEarning.getFiscalDateEnding() );
        }

        logger.info("3) Récupération des QUARTERLY earnings");
        // Quarterly Earnings
        List<AlphaVantage_QuarterlyEarnings> quarterlyEarningsExistants = quarterlyEarningsRepository.findAllByTicker(ticker);
        List<AlphaVantage_QuarterlyEarnings> quarterlyEarnings = earnings.getQuarterlyEarnings();
        for (AlphaVantage_QuarterlyEarnings quarterlyEarning : quarterlyEarnings) {
            quarterlyEarning.setTicker(ticker);
            quarterlyEarning.setDate(quarterlyEarning.getFiscalDateEnding());
            quarterlyEarning.setDateMaj(new Date());
            if (!quarterlyEarningsExistants.stream().anyMatch(e -> formatter.format(e.getFiscalDateEnding())
                    .equals(formatter.format(quarterlyEarning.getFiscalDateEnding()))))
                quarterlyEarningsRepository.save(quarterlyEarning);
            else
                logger.warn("QUARTERLY earnings déjà existant du " + quarterlyEarning.getFiscalDateEnding() );
        }

        return earnings;
    }

    public List<AlphaVantage_EarningsCalendar> getEarningsCalendar(String ticker) throws ParseException {
        return earningsCalendarRepository.findAllByTicker(ticker);
    }

    public void saveIncomeStatements(String ticker) throws ParseException {
        logger.info("------------------------------- INCOME_STATEMENT - AlphaVantage -------------------------------");
        logger.info("0) Mise à jour INCOME_STATEMENTS depuis le site Alpha Vantage pour le ticker : " + ticker);
        String url = new String("https://www.alphavantage.co/query?function=INCOME_STATEMENT&symbol=" + ticker + "&apikey=" + API_KEY);

        // Controle du nombre maximal de requetes :
        if (isCompteurRequetesDepasse(SITE_ALPHA_VANTAGE, API_KEY, SITE_ALPHA_VANTAGE_MAX_REQ_DAILY))
            return;

        // Requete HTTP
        RestTemplate rest = new RestTemplate();
        AlphaVantage_IncomeStatementApi incomeStatement = rest.getForObject(url, AlphaVantage_IncomeStatementApi.class);

        logger.info("1) On n'ajoute que les rapports annuels qui n'existent pas déjà");
        // On n'ajoute que les rapports annuels qui n'existent pas déjà
        List<AlphaVantage_AnnualReport> annualReportsExistants = annualReportRepository.findAllByTicker(ticker);
        List<AlphaVantage_AnnualReport> annualReports = incomeStatement.getAnnualReports();
        for (AlphaVantage_AnnualReport annualReport : annualReports) {
            annualReport.setTicker(ticker);
            annualReport.setDate(annualReport.getFiscalDateEnding());
            annualReport.setDateMaj(new Date());
            if (!annualReportsExistants.stream().anyMatch(e -> formatter.format(e.getFiscalDateEnding())
                    .equals(formatter.format(annualReport.getFiscalDateEnding()))))
                annualReportRepository.save(annualReport);
        }

        logger.info("2) On n'ajoute que les rapports trimestriels qui n'existent pas déjà");
        // On n'ajoute que les rapports trimestriels qui n'existent pas déjà
        List<AlphaVantage_QuarterlyReport> quarterlyReportsExistants = quarterlyReportRepository.findAllByTicker(ticker);
        List<AlphaVantage_QuarterlyReport> quarterlyReports = incomeStatement.getQuarterlyReports();
        for (AlphaVantage_QuarterlyReport quarterlyReport : quarterlyReports) {
            quarterlyReport.setTicker(ticker);
            quarterlyReport.setDate(quarterlyReport.getFiscalDateEnding());
            quarterlyReport.setDateMaj(new Date());
            if (!quarterlyReportsExistants.stream().anyMatch(e -> formatter.format(e.getFiscalDateEnding())
                    .equals(formatter.format(quarterlyReport.getFiscalDateEnding()))))
                quarterlyReportRepository.save(quarterlyReport);
        }
    }

    public void getAllEarnings() throws ParseException, EarningsException {
        List<String> tickersList = Arrays.asList( "SSD", "PCGpA" );
//        List<String> tickersList = Arrays.asList("CECO", "SWAV", "RES", "TRMD", "CPT", "BSM", "KRP", "LTHM", "LNTH", "SRTS", "BPT", "MPC", "ATI", "PI" );
//                , "TALO", "CECE", "CECO", "SWAV", "RES", "TRMD", "CPT", "BSM", "KRP", "LTHM", "LNTH", "SRTS", "BPT", "MPC", "ATI", "PI" );

        for (String ticker : tickersList ) {
            AlphaVantage_EarningsApi earning = getEarnings(ticker);
            saveIncomeStatements(ticker);
        }
    }
}
