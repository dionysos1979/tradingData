package com.traderpatient.tradingdata.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Service
public class MarketPlanningService {

    public static final int DAY = 1;
    public static final int WEEK = 5;
    public static final int MONTH = 20;
    public static final int QUARTER = 60;
    public static final int SEMESTER = 120;
    Logger logger = LoggerFactory.getLogger(MarketPlanningService.class);

    SimpleDateFormat formatterDAY_OF_WEEK = new SimpleDateFormat("E", Locale.FRANCE);

    public MarketPlanningService() {

    }

    public Date getLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    public Date getPreviousDayOpen(Date date, int decalage) throws ParseException {
        Calendar yesterday = Calendar.getInstance();
        yesterday.setTime(date);
        switch (decalage){
            case DAY:
                yesterday.add(Calendar.DATE, -1);
                break;
            case WEEK:
                yesterday.add(Calendar.DATE, -7);
                break;
            case MONTH:
                yesterday.add(Calendar.MONTH, -1);
                break;
            case QUARTER:
                yesterday.add(Calendar.MONTH, -3);
                break;
            case SEMESTER:
                yesterday.add(Calendar.MONTH, -6);
                break;
            default:
                yesterday.add(Calendar.DATE, -1);
                break;
        }

        if (!isMarketOpenDay(yesterday.getTime())){
            return getPreviousDayOpen(yesterday.getTime());
        }
        return yesterday.getTime();
    }

    public Date getPreviousDayOpen(Date date) throws ParseException {
        Calendar yesterday = Calendar.getInstance();
        yesterday.setTime(date);
        yesterday.add(Calendar.DATE, -1);

        if (!isMarketOpenDay(yesterday.getTime())){
            return getPreviousDayOpen(yesterday.getTime());
        }
        return yesterday.getTime();
    }

    public Date getNextDayOpen(Date date) throws ParseException {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.setTime(date);
        tomorrow.add(Calendar.DATE, +1);

        if (!isMarketOpenDay(tomorrow.getTime())){
            return getNextDayOpen(tomorrow.getTime());
        }
        return tomorrow.getTime();
    }

    public boolean isMarketOpenDay(Date inDate) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(inDate);

        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY :
            case Calendar.TUESDAY :
            case Calendar.WEDNESDAY :
            case Calendar.THURSDAY :
            case Calendar.FRIDAY :
                return true;
            case Calendar.SATURDAY:
            case Calendar.SUNDAY :
                return false;
        }
        return false;
    }

    public boolean isMarketClosed(Date inDate) {
        return inDate.compareTo(new Date()) < 0 ;
    }
}
