package com.traderpatient.tradingdata.service;

import com.traderpatient.tradingdata.dao.*;
import com.traderpatient.tradingdata.exceptions.CotationException;
import com.traderpatient.tradingdata.exceptions.EarningsException;
import com.traderpatient.tradingdata.model.*;
import com.traderpatient.tradingdata.model.balanceSheet.AlphaVantage_AnnualReport;
import com.traderpatient.tradingdata.model.balanceSheet.AlphaVantage_QuarterlyReport;
import com.traderpatient.tradingdata.model.balanceSheet.BalanceSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ScoreService {

    Logger logger = LoggerFactory.getLogger(ScoreService.class);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
    SimpleDateFormat formatterFr = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    private final Polygon_DailyQuoteRepository dailyQuoteRepository;
    private final Polygon_QuoteRepository quoteRepository;
    private final ScoreRepository scoreRepository;
    private final PriceRepository priceRepository;
    private final MarketPlanningService marketPlanningService;
    private AlphaVantage_EarningsApiRepository earningsApiRepository;
    private AlphaVantage_QuarterlyEarningsRepository quarterlyEarningsRepository;
    private AlphaVantage_AnnualEarningsRepository annualEarningsRepository;
    private AlphaVantage_EarningsCalendarRepository earningsCalendarRepository;
    private AlphaVantage_AnnualReportRepository annualReportRepository;
    private AlphaVantage_QuarterlyReportRepository quarterlyReportRepository;
    private BalanceSheetRepository balanceSheetRepository;
    private AlphaVantageService alphaVantageService;
    private BalanceSheetService balanceSheetService;
    @Autowired
    public ScoreService(Polygon_DailyQuoteRepository dailyQuoteRepository,
                                     Polygon_QuoteRepository quoteRepository,
                        ScoreRepository scoreRepository,
                        PriceRepository priceRepository,
                        MarketPlanningService marketPlanningService,
                        AlphaVantage_EarningsApiRepository earningsApiRepository,
                        AlphaVantage_QuarterlyEarningsRepository quarterlyEarningsRepository,
                        AlphaVantage_AnnualEarningsRepository annualEarningsRepository,
                        AlphaVantage_EarningsCalendarRepository earningsCalendarRepository,
                        AlphaVantage_AnnualReportRepository annualReportRepository,
                        AlphaVantage_QuarterlyReportRepository quarterlyReportRepository,
                        BalanceSheetRepository balanceSheetRepository,
                        AlphaVantageService alphaVantageService,
                        BalanceSheetService balanceSheetService) {
        this.dailyQuoteRepository = dailyQuoteRepository;
        this.quoteRepository = quoteRepository;
        this.scoreRepository = scoreRepository;
        this.priceRepository = priceRepository;
        this.marketPlanningService = marketPlanningService;
        this.earningsApiRepository = earningsApiRepository;
        this.quarterlyEarningsRepository = quarterlyEarningsRepository;
        this.annualEarningsRepository = annualEarningsRepository;
        this.earningsCalendarRepository = earningsCalendarRepository;
        this.annualReportRepository = annualReportRepository;
        this.quarterlyReportRepository = quarterlyReportRepository;
        this.balanceSheetRepository = balanceSheetRepository;
        this.alphaVantageService = alphaVantageService;
        this.balanceSheetService = balanceSheetService;
    }

    public List<Score> initScore(Date date, List<String> tickersListe) throws ParseException, CotationException {
        logger.info("------------------------------- SCORES -------------------------------");
        logger.info("0) Mise à jour score daily pour la date : " + formatter.format(date));

        // Cotations du jour
        Polygon_DailyQuote dailyQuote = dailyQuoteRepository.findByDate(date);
        List<Polygon_Quote> quoteListToday = quoteRepository.findAllByCotationId(dailyQuote.getCotationId());
        logger.info("1) On récupère les cotations du jour (" +
                formatter.format(marketPlanningService.getPreviousDayOpen(date)) +") : " + quoteListToday.size());

        // Cotations de la veille
        Polygon_DailyQuote dailyQuoteYesterday = dailyQuoteRepository.findByDate(marketPlanningService.getPreviousDayOpen(date));
        List<Polygon_Quote> quoteListYesterday = quoteRepository.findAllByCotationId(dailyQuoteYesterday.getCotationId());
        logger.info("quoteListYesterday:" + quoteListYesterday.size());
        logger.info("2) On récupère les cotations de la veille (" +
                formatter.format(marketPlanningService.getPreviousDayOpen(date)) +") : " + quoteListYesterday.size());

        // Cotations de la semaine dernière
        Polygon_DailyQuote dailyQuoteWeek = dailyQuoteRepository.findByDate(marketPlanningService.getPreviousDayOpen(date, MarketPlanningService.WEEK));
        if (dailyQuoteWeek == null)
            throw new CotationException("Impossible de trouver les cotations pour " + marketPlanningService.getPreviousDayOpen(date, MarketPlanningService.WEEK));
        List<Polygon_Quote> quoteListWeek = quoteRepository.findAllByCotationId(dailyQuoteWeek.getCotationId());
        logger.info("3) On récupère les cotations de la semaine dernières (" +
                formatter.format(marketPlanningService.getPreviousDayOpen(date, MarketPlanningService.WEEK)) +") : " + quoteListWeek.size());

        // Cotations du mois dernier
        Polygon_DailyQuote dailyQuoteMonth = dailyQuoteRepository.findByDate(marketPlanningService.getPreviousDayOpen(date, MarketPlanningService.MONTH));
        if (dailyQuoteMonth == null)
            throw new CotationException("Impossible de trouver les cotations pour " + marketPlanningService.getPreviousDayOpen(date, MarketPlanningService.MONTH));
        List<Polygon_Quote> quoteListMonth = quoteRepository.findAllByCotationId(dailyQuoteMonth.getCotationId());
        logger.info("4) On récupère les cotations du mois dernier (" +
                formatter.format(marketPlanningService.getPreviousDayOpen(date, MarketPlanningService.MONTH)) +") : " + quoteListMonth.size());

        // Cotations de 3 mois en arrière
        Polygon_DailyQuote dailyQuote3Month = dailyQuoteRepository.findByDate(marketPlanningService.getPreviousDayOpen(date, MarketPlanningService.QUARTER));
        if (dailyQuote3Month == null)
            throw new CotationException("Impossible de trouver les cotations pour " + marketPlanningService.getPreviousDayOpen(date, MarketPlanningService.QUARTER));
        List<Polygon_Quote> quoteList3Month = quoteRepository.findAllByCotationId(dailyQuote3Month.getCotationId());
        logger.info("5) On récupère les cotations de 3 mois en arrière (" +
                formatter.format(marketPlanningService.getPreviousDayOpen(date, MarketPlanningService.QUARTER)) +") : " + quoteList3Month.size());

        // Cotations de 6 mois en arrière
//        Polygon_DailyQuote dailyQuote6Month = dailyQuoteRepository.findByDate(marketPlanningService.getPreviousDayOpen(date, MarketPlanningService.SEMESTER));
//        if (dailyQuote6Month == null)
//            throw new CotationException("Impossible de trouver les cotations pour " + marketPlanningService.getPreviousDayOpen(date, MarketPlanningService.SEMESTER));
//        List<Polygon_Quote> quoteList6Month = quoteRepository.findAllByCotationId(dailyQuote6Month.getCotationId());
//        logger.info("5) On récupère les cotations de 6 mois en arrière (" +
//                formatter.format(marketPlanningService.getPreviousDayOpen(date, MarketPlanningService.SEMESTER)) +") : " + quoteList6Month.size());

        // Cotations du SP500
        List<Polygon_Quote> cotationsSP500 = quoteRepository.findAllByTickerOrderByDateDesc("SP500");
        logger.info("6) On récupère les cotations de l'indide de référence SP500 : " + cotationsSP500.size());

        // EPS
        //List<AlphaVantage_EarningsCalendar earningsCalendars = quarterlyEarningsRepository.findAllByAlphaVantageEarningsApiId();

        //
        List<Score> scoreList = new ArrayList<>();
        List<String> noScoreList = new ArrayList<>();
        logger.info("7) On calcule les scores.");

        // On parcours la liste des cotations pour les tickers passés en paramètres
        List<Polygon_Quote> quoteListTemp = new ArrayList<>();
        for ( String tickerToAdd : tickersListe) {
            quoteListTemp.add(quoteListToday.stream().filter(e -> e.getTicker().equals(tickerToAdd)).findFirst().get());
        }

        for ( Polygon_Quote quote : quoteListTemp) {
            try {
                // ----------------- Rapports trimestriels INCOME_STATEMENTS : -----------------
                List<AlphaVantage_QuarterlyReport> quarterlyReportList = quarterlyReportRepository.findAllByTicker(quote.getTicker());
                if (quarterlyReportList == null || quarterlyReportList.size() == 0 ) {
                    logger.warn(quote.getTicker() + " -> Liste vide : quarterlyReportList");
                    // Mise à jour des données manquantes
                    alphaVantageService.saveIncomeStatements(quote.getTicker());
                    quarterlyReportList = quarterlyReportRepository.findAllByTicker(quote.getTicker());
                }

                Collections.sort(quarterlyReportList, AlphaVantage_QuarterlyReport.SORT_BY_FISCAL_DATE);

                AlphaVantage_QuarterlyReport quarterlyReport_Q_0 = quarterlyReportList.get(0);
                AlphaVantage_QuarterlyReport quarterlyReport_Q_1 = quarterlyReportList.get(1);
                AlphaVantage_QuarterlyReport quarterlyReport_Q_2 = quarterlyReportList.get(2);

                // ----------------- Rapports Annuels INCOME_STATEMENTS : -----------------
                List<AlphaVantage_AnnualReport> annualReportList = annualReportRepository.findAllByTicker(quote.getTicker());
                if (annualReportList == null || annualReportList.size() == 0 )
                    logger.warn( quote.getTicker() + " -> Liste vide : annualReportList");

                Collections.sort(annualReportList, AlphaVantage_AnnualReport.SORT_BY_FISCAL_DATE);

                AlphaVantage_AnnualReport annualReport_Y_0 = annualReportList.get(0);
                AlphaVantage_AnnualReport annualReport_Y_1 = annualReportList.get(1);
                AlphaVantage_AnnualReport annualReport_Y_2 = annualReportList.get(2);

                // ----------------- Rapports trimestriels BALANCE SHEET : -----------------
                List<BalanceSheet> quarterlyBalanceSheetList = balanceSheetRepository.findAllByTickerAndFrequence(quote.getTicker(), BalanceSheet.QUARTERLY);
                if (quarterlyBalanceSheetList == null || quarterlyBalanceSheetList.size() == 0 ) {
                    logger.warn(quote.getTicker() + " -> Liste vide : quarterlyBalanceSheetList");
                    // Mise à jour des données manquantes
                    balanceSheetService.saveAllFromAlphaVantage(quote.getTicker());
                    quarterlyBalanceSheetList = balanceSheetRepository.findAllByTickerAndFrequence(quote.getTicker(), BalanceSheet.QUARTERLY);
                }
                Collections.sort(quarterlyBalanceSheetList, BalanceSheet.SORT_BY_FISCAL_DATE);

                BalanceSheet quarterlyBalanceSheet_Q_0 = quarterlyBalanceSheetList.get(0);
                BalanceSheet quarterlyBalanceSheet_Q_1 = quarterlyBalanceSheetList.get(1);

                // ----------------- Rapports Annuels BALANCE SHEET : -----------------
                List<BalanceSheet> annualBalanceSheetList = balanceSheetRepository.findAllByTickerAndFrequence(quote.getTicker(), BalanceSheet.ANNUAL);
                if (annualBalanceSheetList == null || annualBalanceSheetList.size() == 0 )
                    logger.warn( quote.getTicker() + " -> Liste vide : annualBalanceSheetList");
                Collections.sort(annualBalanceSheetList, BalanceSheet.SORT_BY_FISCAL_DATE);

                BalanceSheet annualBalanceSheet_Y_0 = annualBalanceSheetList.get(0);
                BalanceSheet annualBalanceSheet_Y_1 = annualBalanceSheetList.get(1);
                // ------------------------------------------------------------------------------

                // ----------------- Rapports Annuels EARNINGS : -----------------
                // On tri les trimestres des Earnings pour réaliser les % growth QoQ
                List<AlphaVantage_QuarterlyEarnings> quarterlyEarnings = quarterlyEarningsRepository.findAllByTicker(quote.getTicker());
                if (quarterlyEarnings == null || quarterlyEarnings.size() == 0 ) {
                    logger.warn(quote.getTicker() + " -> Liste vide : quarterlyEarnings");
                    // Mise à jour des données manquantes
                    alphaVantageService.getEarnings(quote.getTicker());
                    quarterlyEarnings =  quarterlyEarningsRepository.findAllByTicker(quote.getTicker());
                }
                Collections.sort(quarterlyEarnings, AlphaVantage_QuarterlyEarnings.SORT_BY_REPORTED_DATE);

                // Récupère le dernier Rapport Annuel des Earnings
                List<AlphaVantage_AnnualEarnings> annualEarnings = annualEarningsRepository.findAllByTicker(quote.getTicker());
                if (annualEarnings == null || annualEarnings.size() == 0 )
                    logger.warn( quote.getTicker() + " -> Liste vide : annualEarnings");
                AlphaVantage_AnnualEarnings annualEarning = annualEarnings.stream()
                        .sorted(Comparator.comparing(AlphaVantage_AnnualEarnings::getFiscalDateEnding).reversed())
                        .findFirst().get();
                // ------------------------------------------------------------------------------

                // Calcul des perf et rs p/r SP500
                Polygon_Quote toDay = quoteListToday.stream().filter(e -> e.getTicker().equals(quote.getTicker())) .findFirst().get();
                Polygon_Quote yesterday = quoteListYesterday.stream().filter(e -> e.getTicker().equals(quote.getTicker())).findFirst().get();
                Polygon_Quote lastWeek = quoteListWeek.stream().filter(e -> e.getTicker().equals(quote.getTicker())).findFirst().get();
                Polygon_Quote lastMonth = quoteListMonth.stream().filter(e -> e.getTicker().equals(quote.getTicker())).findFirst().get();
                Polygon_Quote last3Month = quoteList3Month.stream().filter(e -> e.getTicker().equals(quote.getTicker())).findFirst().get();

                // Relative Strentgh SP500
                Polygon_Quote sp500Today = cotationsSP500.stream().filter(e -> e.getDate().equals(toDay.getDate())) .findFirst().get();
                Polygon_Quote sp500Yesterday = cotationsSP500.stream().filter(e -> e.getDate().equals(yesterday.getDate())) .findFirst().get();
                Polygon_Quote sp500LastWeek = cotationsSP500.stream().filter(e -> e.getDate().equals(lastWeek.getDate())) .findFirst().get();
                Polygon_Quote sp500LastMonth = cotationsSP500.stream().filter(e -> e.getDate().equals(lastMonth.getDate())) .findFirst().get();
                Polygon_Quote sp500Last3Month = cotationsSP500.stream().filter(e -> e.getDate().equals(last3Month.getDate())) .findFirst().get();

                // Initialisation du score pour ce ticker
                Score score = new Score(
                        quote.getTicker(),
                        date,
                        // Cotations du ticker pour calcul des performances
                        toDay.getClose(),
                        yesterday.getClose(),
                        lastWeek.getClose(),
                        lastMonth.getClose(),
                        last3Month.getClose(),
                        // Earnings
                        Double.parseDouble(quarterlyEarnings.get(0).getReportedEPS()),
                        quarterlyEarnings.get(0).getReportedDate(),
                        Double.parseDouble(quarterlyEarnings.get(0).getEstimatedEPS()),
                        quarterlyEarnings.get(0).getFiscalDateEnding(),
                        Double.parseDouble(quarterlyEarnings.get(0).getSurprise()),
                        Double.parseDouble(quarterlyEarnings.get(0).getSurprisePercentage()),
                        // % croissance EPS QoQ
                        calculPourcentageGrowth(Double.parseDouble(quarterlyEarnings.get(0).getReportedEPS()), Double.parseDouble( quarterlyEarnings.get(4).getReportedEPS())) ,
                        calculPourcentageGrowth(Double.parseDouble(quarterlyEarnings.get(1).getReportedEPS()), Double.parseDouble( quarterlyEarnings.get(5).getReportedEPS())) ,
                        calculPourcentageGrowth(Double.parseDouble(quarterlyEarnings.get(2).getReportedEPS()), Double.parseDouble( quarterlyEarnings.get(6).getReportedEPS())) ,
                        // RS p/r SP500
                        calculPourcentageGrowth(sp500Today.getClose(), sp500Yesterday.getClose()),
                        calculPourcentageGrowth(sp500Today.getClose(), sp500LastWeek.getClose()),
                        calculPourcentageGrowth(sp500Today.getClose(), sp500LastMonth.getClose()),
                        calculPourcentageGrowth(sp500Today.getClose(), sp500Last3Month.getClose()),
                        // Revenues
                        Double.parseDouble(quarterlyReport_Q_0.getTotalRevenue()),
                        Double.parseDouble(annualReport_Y_0.getTotalRevenue()),
                        // % croissance Revenues QoQ
                        calculPourcentageGrowth(Double.parseDouble(quarterlyReportList.get(0).getTotalRevenue()), Double.parseDouble( quarterlyReportList.get(4).getTotalRevenue())) ,
                        calculPourcentageGrowth(Double.parseDouble(quarterlyReportList.get(1).getTotalRevenue()), Double.parseDouble( quarterlyReportList.get(5).getTotalRevenue())) ,
                        calculPourcentageGrowth(Double.parseDouble(quarterlyReportList.get(2).getTotalRevenue()), Double.parseDouble( quarterlyReportList.get(6).getTotalRevenue())) ,
                        // Ratios : roe & roi
                        Double.parseDouble(annualReport_Y_0.getNetIncome()) / Double.parseDouble(annualBalanceSheet_Y_0.getTotalShareholderEquity()),
                        // Marges
                        Double.parseDouble(quarterlyReport_Q_0.getGrossProfit()) / Double.parseDouble(quarterlyReport_Q_0.getTotalRevenue()),
                        Double.parseDouble(quarterlyReport_Q_0.getNetIncome()) / Double.parseDouble(quarterlyReport_Q_0.getTotalRevenue()),
                        Double.parseDouble(quarterlyReport_Q_0.getOperatingIncome()) / Double.parseDouble(quarterlyReport_Q_0.getTotalRevenue())
                        );
                scoreList.add(score);
            } catch (NoSuchElementException e){
                logger.info("NoSuchElementException pour :" + quote.getTicker());
                noScoreList.add(quote.getTicker());
            } catch (IndexOutOfBoundsException e){
                logger.info("IndexOutOfBoundsException pour :" + quote.getTicker());
                noScoreList.add(quote.getTicker());
            } catch (EarningsException e) {
                throw new RuntimeException(e);
            }
        }
        logger.info("8) Sauvegarde en BDD des scores pour la date : " + formatter.format(date));
        scoreRepository.saveAll(scoreList);
        logger.info("9) Nb scores sauvegardés : " + scoreList.size());
        logger.info("10) Nb scores NON sauvegardés : " + noScoreList.size());
        logger.info("------------------------------- SCORES -------------------------------");
        return scoreList;
    }

    public List<Score> calculScore(Date date) {
        List<Score> scoreList = scoreRepository.findAllByDate(date);
//        if (scoreList.isEmpty() && date.before(new Date())){
//            initScore(date);
//        }
        return scoreList;
    }

    public Double calculPourcentageGrowth(Double q0, Double q1){
        if (q0 == null || q1 == null)
            return 0d;
        return ( q0 - q1 ) / Math.abs(q1);
    }

}
