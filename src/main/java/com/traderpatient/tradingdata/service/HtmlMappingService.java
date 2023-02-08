package com.traderpatient.tradingdata.service;


import com.traderpatient.tradingdata.model.Polygon_Quote;
import com.traderpatient.tradingdata.model.QuarterlyRevenue;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.*;
import java.util.*;

public class HtmlMappingService {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
    Logger logger = LoggerFactory.getLogger(HtmlMappingService.class);

    public List<Object> mapHtmlTableToObject(Document doc, String classTag, String ticker) throws ParseException {
        List<Object> toReturn = new ArrayList<>();

        // Récupère l'index du tableau de données sur l'action
        Elements tables = doc.getElementsByClass(classTag);

        for (int i = 1; i < tables.size(); i++) {
            logger.info(" ----- " + i + "ère TABLE trouvée : " + tables.get(i).select("thead"));
            Elements rows = tables.get(i).select("tr");

            /**
             * Stock data : tableau des historique de cours de l'indice
             */
            for (int j = 1; j < rows.size(); j++) {
                Element row = rows.get(j);
                Elements cols = row.select("td");
                Object object = null;
                if (ticker == "SP500" && classTag.equals(YahooService.TAG_HISTORIQUE_COURS))
                    object = getMappingIndiceForYahoo(cols, ticker);
                if (classTag.equals("W(100%) M(0)"))
                    object = getMappingStockForYahoo(cols, ticker);
                if (classTag.equals(MacroTrendsService.TAG_HISTORIQUE_COURS))
                    object = getMappingRevenueForMacroTrends(cols, ticker, tables.get(i).select("thead"));
                if (object == null) {
                    continue;
                } else {
                    toReturn.add(object);
                }
            }
        }
        return toReturn;
    }

    /** Mapping des historiques de cours des indices de Yahoo
     *
     * @param cols
     * @param ticker
     * @return
     * @throws ParseException
     */
    public Object getMappingIndiceForYahoo(Elements cols, String ticker) throws ParseException {
        Polygon_Quote cotation = new Polygon_Quote();
        cotation.setTicker(ticker);

        // On teste que la ligne contienne bien une ligne de cotation car il faut exclure les dividendes et autres événements
        try {
            if (cols.get(6).text() == null){
                logger.info(" --- Ligne vide pour le ticker : " + cotation.getTicker());
            }
        } catch (IndexOutOfBoundsException e){
            logger.info(" --- Ligne suivante exclue pour le ticker : " + cotation.getTicker());
            return null;
        }
        // Date du cours
        SimpleDateFormat formatter = new SimpleDateFormat("d MMM y", Locale.FRANCE);
        cotation.setDate(formatter.parse(cols.get(0).text()));

        // Cours
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

        cotation.setOpen(Double.valueOf(format.parse(cols.get(1).text()).doubleValue()));
        cotation.setHigh(Double.valueOf(format.parse(cols.get(2).text()).doubleValue()));
        cotation.setLow(Double.valueOf(format.parse(cols.get(3).text()).doubleValue()));
        cotation.setClose(Double.valueOf(format.parse(cols.get(5).text()).doubleValue()));
        cotation.setVolumeWAP(null);
        cotation.setV(Double.valueOf(format.parse(cols.get(6).text()).doubleValue()));

        return cotation;
    }

    /** Mapping des historiques de cours des actions sur Yahoo
     *
     * @param cols
     * @param ticker
     * @return
     * @throws ParseException
     */
    public Object getMappingStockForYahoo(Elements cols, String ticker) throws ParseException {
        Polygon_Quote cotation = new Polygon_Quote();
        cotation.setTicker(ticker);

        // On teste que la ligne contienne bien une ligne de cotation car il faut exclure les dividendes et autres événements
        try {
            if (cols.get(6).text() == null){
                logger.info(" --- Ligne vide pour le ticker : " + cotation.getTicker());
            }
        } catch (IndexOutOfBoundsException e){
            logger.info(" --- Ligne suivante exclue pour le ticker : " + cotation.getTicker());
            return null;
        }
        // Date du cours
        SimpleDateFormat formatter = new SimpleDateFormat("d MMM y", Locale.FRANCE);
        cotation.setDate(formatter.parse(cols.get(0).text()));

        // Cours
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);

        cotation.setOpen(Double.valueOf(format.parse(cols.get(1).text()).doubleValue()));
        cotation.setHigh(Double.valueOf(format.parse(cols.get(2).text()).doubleValue()));
        cotation.setLow(Double.valueOf(format.parse(cols.get(3).text()).doubleValue()));
        cotation.setClose(Double.valueOf(format.parse(cols.get(5).text()).doubleValue()));
        cotation.setVolumeWAP(Double.valueOf(format.parse(cols.get(6).text()).doubleValue()));
        cotation.setV(Double.valueOf(format.parse(cols.get(6).text()).doubleValue()));

        return cotation;
    }

    /** Mapping des historiques de Revenue des actions du site MacroTrends
     *
     * @param cols
     * @param ticker
     * @return
     * @throws ParseException
     */
    public Object getMappingRevenueForMacroTrends(Elements cols, String ticker, Elements thead) throws ParseException {
        if (thead.toString().contains("Consumer Discretionary") ||
                thead.toString().contains("Industry") ||
                thead.toString().contains("Stock Name"))
            return null;

        // Formattage des données sous le format $1,562.02
        NumberFormat formatUSD = NumberFormat.getCurrencyInstance(Locale.US);

        // Historique
        QuarterlyRevenue revenue = new QuarterlyRevenue();
        revenue.setDate(formatter.parse(cols.get(0).text()));
        revenue.setTicker(ticker);
        revenue.setRevenue(formatUSD.parse(cols.get(1).text()).doubleValue());
        logger.info(" --- QuarterlyRevenue : " + revenue.toString());

        return revenue;
    }
}