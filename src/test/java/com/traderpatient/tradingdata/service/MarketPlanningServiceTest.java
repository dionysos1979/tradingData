package com.traderpatient.tradingdata.service;

import com.traderpatient.tradingdata.service.MarketPlanningService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 */
public class MarketPlanningServiceTest {
    Logger logger = LoggerFactory.getLogger(MarketPlanningServiceTest.class);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);

    @Test
    public void isMarketOpenDayOpened() throws ParseException {
        MarketPlanningService service = new MarketPlanningService();
        logger.info("MarketPlanningServiceTest.isMarketOpenDayOpened()");

        // Vendredi 13 janvier 2023 est ouvert
        Date date = formatter.parse("2023-01-13");

        Assert.notNull(service,  "MarketPlanningService mustn't be null");
        Assert.isTrue(service.isMarketOpenDay(date), "Ce jour devrait être ouvert !");
    }

    @Test
    public void isMarketOpenDayClosed() throws ParseException {
        MarketPlanningService service = new MarketPlanningService();
        logger.info("MarketPlanningServiceTest.isMarketOpenDayClosed()");

        // Samedi 14 janvier 2023 est fermé
        Date date = formatter.parse("2023-01-14");

        Assert.notNull(service,  "MarketPlanningService mustn't be null");
        Assert.isTrue(!service.isMarketOpenDay(date), "Ce jour devrait être fermé !");
    }

    @Test
    public void getNextDayOpen1() throws ParseException {
        MarketPlanningService service = new MarketPlanningService();
        logger.info("MarketPlanningServiceTest.getNextDayOpen()");

        // Samedi 14 janvier 2023 est fermé => prochain jour ouvert = lundi 16 janvier
        Date date = formatter.parse("2023-01-14");
        Date date16 = formatter.parse("2023-01-16");
        Date date2 = service.getNextDayOpen(date);

        logger.info("date:" + formatter.format(date));
        logger.info("date16:" + formatter.format(date16));
        logger.info("date2:" + formatter.format(date2));

        Assert.notNull(service,  "MarketPlanningService mustn't be null");
        Assert.notNull(date2, "getNextDayOpen devrait être non null !");
        Assert.isTrue(date2.equals(date16), "getNextDayOpen devrait être le 16/01 !");
    }

    @Test
    public void getNextDayOpen2() throws ParseException {
        MarketPlanningService service = new MarketPlanningService();
        logger.info("MarketPlanningServiceTest.getNextDayOpen()");

        // Vendredi 06 janvier 2023 est fermé => prochain jour ouvert = lundi 09 janvier
        Date date06 = formatter.parse("2023-01-06");
        Date date09 = formatter.parse("2023-01-09");
        Date date = service.getNextDayOpen(date06);

        logger.info("date06:" + formatter.format(date06));
        logger.info("date09:" + formatter.format(date09));
        logger.info("date:" + formatter.format(date));

        Assert.notNull(service,  "MarketPlanningService mustn't be null");
        Assert.notNull(date, "getNextDayOpen devrait être non null !");
        Assert.isTrue(date.equals(date09), "getNextDayOpen devrait être le 09/01 !");
    }

    @Test
    public void isMarketClosed() throws ParseException {
        MarketPlanningService service = new MarketPlanningService();
        logger.info("MarketPlanningServiceTest.isMarketClosed()");

        Date date23 = formatter.parse("2023-01-23");
        Date date24 = formatter.parse("2023-02-24");
        Date date10 = formatter.parse("2023-01-10");

        Assert.notNull(service,  "MarketPlanningService mustn't be null");
        Assertions.assertFalse(service.isMarketClosed(date24) , "isMarketClosed devrait être false");
        //Assertions.assertFalse(service.isMarketClosed(date23), "isMarketClosed devrait être false");
        Assertions.assertTrue(service.isMarketClosed(date10) ,  "isMarketClosed devrait être true");
        Assertions.assertFalse(service.isMarketClosed(new Date()) ,  "isMarketClosed devrait être false");

    }

    @Test
    public void getPreviousDayOpen1() throws ParseException {
        MarketPlanningService service = new MarketPlanningService();
        logger.info("MarketPlanningServiceTest.getPreviousDayOpen()");

        // Samedi 14 janvier 2023 est fermé => précédenr jour ouvert = vendrei 13 janvier
        Date date = formatter.parse("2023-01-14");
        Date date13 = formatter.parse("2023-01-13");
        Date date2 = service.getPreviousDayOpen(date);

        logger.info("date:" + formatter.format(date));
        logger.info("date13:" + formatter.format(date13));
        logger.info("date2:" + formatter.format(date2));

        Assert.notNull(service,  "MarketPlanningService mustn't be null");
        Assert.notNull(date2, "getPreviousDayOpen devrait être non null !");
        Assert.isTrue(date2.equals(date13), "getPreviousDayOpen devrait être le 13/01 !");
    }

    @Test
    public void getPreviousDayOpen2() throws ParseException {
        MarketPlanningService service = new MarketPlanningService();
        logger.info("MarketPlanningServiceTest.getPreviousDayOpen2()");

        // Lundi 23 janvier 2023 est fermé => précédent jour ouvert = vendredi 20 janvier
        Date date = formatter.parse("2023-01-23");
        Date date13 = formatter.parse("2023-01-20");
        Date date2 = service.getPreviousDayOpen(date);

        logger.info("date:" + formatter.format(date));
        logger.info("date13:" + formatter.format(date13));
        logger.info("date2:" + formatter.format(date2));

        Assert.notNull(service,  "MarketPlanningService mustn't be null");
        Assert.notNull(date2, "getPreviousDayOpen devrait être non null !");
        Assert.isTrue(date2.equals(date13), "getPreviousDayOpen devrait être le 20/01 !");
    }



    @Test
    public void getLastDayOfMonth() throws ParseException {
        MarketPlanningService service = new MarketPlanningService();
        logger.info("MarketPlanningServiceTest.getLastDayOfMonth()");

        // Lundi 23 janvier 2023
        Date date = formatter.parse("2020-02-23");
        Date date2 = service.getLastDayOfMonth(date);
        Date dateVerif = formatter.parse("2020-02-29");

        logger.info("date:" + formatter.format(date));
        logger.info("date2:" + formatter.format(date2));

        Assert.notNull(service,  "MarketPlanningService mustn't be null");
        Assert.notNull(date2, "getLastDayOfMonth devrait être non null !");
        Assert.isTrue(date2.equals(dateVerif), "getLastDayOfMonth devrait être le 31/01 !");
    }

}
