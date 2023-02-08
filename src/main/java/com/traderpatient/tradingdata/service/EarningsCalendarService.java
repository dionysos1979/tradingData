package com.traderpatient.tradingdata.service;

import com.traderpatient.tradingdata.dao.AlphaVantage_EarningsCalendarRepository;
import com.traderpatient.tradingdata.model.AlphaVantage_EarningsCalendar;
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
public class EarningsCalendarService {

    Logger logger = LoggerFactory.getLogger(EarningsCalendarService.class);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
    private AlphaVantage_EarningsCalendarRepository earningsCalendarRepository;

    private AlphaVantageService alphaVantageService;

    public EarningsCalendarService(AlphaVantage_EarningsCalendarRepository earningsCalendarRepository,
                                   AlphaVantageService alphaVantageService) {
        this.earningsCalendarRepository = earningsCalendarRepository;
        this.alphaVantageService = alphaVantageService;
    }

    /**
     * Vérification si le calendrier des EPS est à jour par rapport à aujourdhui
     *
     * @return
     */
    public boolean isUpToDate(){
        // On teste la date de la dernière mise à jour du calendrier
        AlphaVantage_EarningsCalendar lastCalendar = earningsCalendarRepository.findTopByOrderByDateDesc();
        if (lastCalendar == null || lastCalendar.getDate().before(new Date()))
            return false;
        return true;
    }

    /**
     * Mise à jour du calendrier des EPS avec les API AlphaVantage pour tous les tickers
     * @return
     * @throws ParseException
     */
    public List<AlphaVantage_EarningsCalendar> saveEarningsCalendar() throws ParseException {
        logger.info("------------------------------- Calendrier EARNINGS - AlphaVantage -------------------------------");
        logger.info("0) Mise à jour du calendrier des EPS depuis le site Alpha Vantage pour tous les tickers." );
        String url = new String("https://www.alphavantage.co/query?function=EARNINGS_CALENDAR&horizon=3month&apikey=" +
                alphaVantageService.API_KEY);

        // Controle du nombre maximal de requetes :
        if (alphaVantageService.isCompteurRequetesDepasse(alphaVantageService.SITE_ALPHA_VANTAGE,
                alphaVantageService.API_KEY,
                alphaVantageService.SITE_ALPHA_VANTAGE_MAX_REQ_DAILY))
            return null;

        logger.info("1) On teste la dernière date de mise à jour du calendrier et on renvoie la liste si à jour." );
        if (isUpToDate())
            return earningsCalendarRepository.findAllByDate(new Date());

        logger.info("2) On met à jour le dernier calendrier sur le site Alpha_Vantage." );
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        List<AlphaVantage_EarningsCalendar> calendarList = new ArrayList<>();
        List<AlphaVantage_EarningsCalendar> calendarListExistant = new ArrayList<>();
        Date dateMaj = new Date();

        BufferedReader br = null;
        String line = "";
        String splitBy = ",";
        try {
            InputStream targetStream = new ByteArrayInputStream(response.getBody().getBytes());
            br = new BufferedReader(new InputStreamReader(targetStream));
            while ((line = br.readLine()) != null) { //symbol,name,reportDate,fiscalDateEnding,estimate,currency
                String[] stocks = line.split(splitBy);
                // On ne prend pas en compte la 1ère ligne
                // On teste que la ligne n'existe pas déjà en BDD pour ce ticker et cette date de publication
                if (stocks[0].equalsIgnoreCase("symbol")
                        && stocks[3].equalsIgnoreCase("fiscalDateEnding"))
                    continue;
                AlphaVantage_EarningsCalendar calendar = new AlphaVantage_EarningsCalendar();
                calendar.setDate(dateMaj);
                calendar.setTicker(stocks[0]);
                calendar.setName(stocks[1]);
                calendar.setReportDate(formatter.parse(stocks[2]));
                calendar.setFiscalDateEnding(formatter.parse(stocks[3]));
                calendar.setEstimate(stocks[4]);
                calendar.setCurrency(stocks[5]);

                // Teste que ce calendrier n'existe pas déjà en BDD
                calendarListExistant = earningsCalendarRepository.findAllByTicker(calendar.getTicker());
                if (calendarListExistant == null
                        || calendarListExistant.size() == 0
                        || !calendarListExistant.contains(calendar) ) {
                    calendarList.add(calendar);
                    logger.info("3) Sauvegarde du calendrier EPS : " + calendar.toString());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // On sauvegarde tous les calendriers avec des valeurs différentes
        earningsCalendarRepository.saveAll(calendarList);

        return calendarList;
    }
}
