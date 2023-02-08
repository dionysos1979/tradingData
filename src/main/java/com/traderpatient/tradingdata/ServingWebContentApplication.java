package com.traderpatient.tradingdata;

import com.traderpatient.tradingdata.model.DataProvider;
import com.traderpatient.tradingdata.dao.DataProviderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.List;

@SpringBootApplication
public class ServingWebContentApplication extends SpringBootServletInitializer{
    private static final Logger logger = LoggerFactory.getLogger(ServingWebContentApplication.class);

    @Autowired
    DataProviderRepository dataProviderRepository;

    public static void main(String[] args) {
        logger.info("ServingWebContentApplication.main()");
        SpringApplication.run(ServingWebContentApplication.class, args);
    }

    public ServingWebContentApplication(DataProviderRepository dataProviderRepository) {
        this.dataProviderRepository = dataProviderRepository;
        initDB();
    }

    public void initDB(){
        DataProvider yahoo = new DataProvider();
        yahoo.setName(DataProvider.YAHOO);
        yahoo.setUrl("https://fr.finance.yahoo.com/quote/");

        DataProvider finviz = new DataProvider();
        finviz.setName(DataProvider.FINVIZ);
        finviz.setUrl("https://finviz.com/quote.ashx?t=");

        List<DataProvider> dataProviderList = (List<DataProvider>) dataProviderRepository.findAll();
        if (!dataProviderList.contains(yahoo))
            dataProviderRepository.save(yahoo);

        if (!dataProviderList.contains(finviz))
            dataProviderRepository.save(finviz);
    }
}
