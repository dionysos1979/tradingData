package com.traderpatient.tradingdata.repository;

import com.traderpatient.tradingdata.dao.Polygon_QuoteRepository;
import com.traderpatient.tradingdata.model.Polygon_Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class Polygon_QuoteRepositoryTest {

    @Autowired
    private Polygon_QuoteRepository repository;

    private Polygon_Quote objectTested;

    @BeforeEach
    public void setup() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.FRANCE);
        objectTested = new Polygon_Quote();
        objectTested.setDate(formatter.parse("2023-01-31"));
        objectTested.setCotationId(2222);
        objectTested.setDate(new Date());
        objectTested.setClose(198.50);
        objectTested.setHigh(199.50);
        objectTested.setLow(152.50);
        objectTested.setNumberTransactions(654684d);
        objectTested.setOpen(190.50);
        objectTested.setTicker("MSFT");
        objectTested.setTimeStamp(15000d);
        objectTested.setV(165498400d);
        objectTested.setVolumeWAP(3652023d);
    }

    // JUnit test for save Quote operation
    @Test
    public void givenQuoteObject_whenSave_thenReturnSavedQuote(){
        // when - action or the behaviour that we are going test
        Polygon_Quote quoteSaved = repository.save(objectTested);

        // then - verify the output
        assertThat(quoteSaved).isNotNull();
        assertThat(quoteSaved.getId()).isGreaterThan(0);
    }

    // JUnit test for get all dailyQuote operation
    @Test
    public void givenQuoteList_whenFindAll_thenQuoteList(){
        // given - precondition or setup

        // when -  action or the behaviour that we are going test
        List<Polygon_Quote> quoteList = (List<Polygon_Quote>) repository.findAll();

        // then - verify the output
        assertThat(quoteList).isNotNull();
        assertThat(quoteList.size()).isGreaterThan(0);

    }

    // JUnit test for get Quote by id operation
    @DisplayName("JUnit test for get Quote by id operation")
    @Test
    public void givenQuoteObject_whenFindById_thenReturnQuoteObject(){
        repository.save(objectTested);

        // when -  action or the behaviour that we are going test
        Polygon_Quote quoteDB = repository.findById(objectTested.getId()).get();

        // then - verify the output
        assertThat(quoteDB).isNotNull();
    }

    // JUnit test for update Quote operation
    @DisplayName("JUnit test for update Quote operation")
    @Test
    public void givenQuoteObject_whenUpdateQuote_thenReturnUpdatedQuote(){

        repository.save(objectTested);

        // when -  action or the behaviour that we are going test
        Polygon_Quote quoteDB = repository.findById(objectTested.getId()).get();
        quoteDB.setTicker("AAPL");
        Polygon_Quote updatedQuote =  repository.save(quoteDB);

        // then - verify the output
        assertThat(updatedQuote.getTicker().equals("AAPL"));
    }

    // JUnit test for delete Quote operation
    @DisplayName("JUnit test for delete Quote operation")
    @Test
    public void givenQuoteObject_whenDelete_thenRemoveQuote(){

        repository.save(objectTested);

        // when -  action or the behaviour that we are going test
        repository.deleteById(objectTested.getId());
        Optional<Polygon_Quote> quoteDeleted = repository.findById(objectTested.getId());

        // then - verify the output
        assertThat(quoteDeleted).isEmpty();
    }
}