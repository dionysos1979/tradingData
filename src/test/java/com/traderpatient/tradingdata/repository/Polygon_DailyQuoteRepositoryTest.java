package com.traderpatient.tradingdata.repository;

import com.traderpatient.tradingdata.dao.Polygon_DailyQuoteRepository;
import com.traderpatient.tradingdata.model.Polygon_DailyQuote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class Polygon_DailyQuoteRepositoryTest {

    @Autowired
    private Polygon_DailyQuoteRepository dailyQuoteRepository;

    private Polygon_DailyQuote dailyQuote;

    @BeforeEach
    public void setup(){
        dailyQuote = new Polygon_DailyQuote();
        dailyQuote.setMaj(new Date());
        dailyQuote.setStatus("OK");
        dailyQuote.setRequest_id("rzvlkjnzemijncvmijnvazmijnavv");
        dailyQuote.setResultsCount(15000);
    }

    // JUnit test for save dailyQuote operation
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        // when - action or the behaviour that we are going test
        Polygon_DailyQuote dailyQuoteSaved = dailyQuoteRepository.save(dailyQuote);

        // then - verify the output
        assertThat(dailyQuoteSaved).isNotNull();
        assertThat(dailyQuoteSaved.getCotationId()).isGreaterThan(0);
    }

    // JUnit test for get all dailyQuote operation
    @Test
    public void givenDailyQuoteList_whenFindAll_thenDailyQuotesList(){
        // given - precondition or setup

        // when -  action or the behaviour that we are going test
        List<Polygon_DailyQuote> dailyQuoteList = (List<Polygon_DailyQuote>) dailyQuoteRepository.findAll();

        // then - verify the output
        assertThat(dailyQuoteList).isNotNull();
        assertThat(dailyQuoteList.size()).isEqualTo(8);

    }

    // JUnit test for get employee by id operation
    @DisplayName("JUnit test for get employee by id operation")
    @Test
    public void givenDailyQuoteObject_whenFindById_thenReturnDailyQuoteObject(){

        dailyQuoteRepository.save(dailyQuote);

        // when -  action or the behaviour that we are going test
        Polygon_DailyQuote dailyQuoteDB = dailyQuoteRepository.findById(dailyQuote.getCotationId()).get();

        // then - verify the output
        assertThat(dailyQuoteDB).isNotNull();
    }

    // JUnit test for update DailyQuote operation
    @DisplayName("JUnit test for update employee operation")
    @Test
    public void givenDailyQuoteObject_whenUpdateDailyQuote_thenReturnUpdatedDailyQuote(){

        dailyQuoteRepository.save(dailyQuote);

        // when -  action or the behaviour that we are going test
        Polygon_DailyQuote dailyQuoteDB = dailyQuoteRepository.findById(dailyQuote.getCotationId()).get();
        dailyQuoteDB.setStatus("KO !!!");
        dailyQuoteDB.setResultsCount(1000);
        Polygon_DailyQuote updatedDailyQuote1 =  dailyQuoteRepository.save(dailyQuoteDB);

        // then - verify the output
        assertThat(updatedDailyQuote1.getStatus()).isEqualTo("KO !!!");
        assertThat(updatedDailyQuote1.getResultsCount()).isEqualTo(1000);
    }

    // JUnit test for delete DailyQuote operation
    @DisplayName("JUnit test for delete DailyQuote operation")
    @Test
    public void givenDailyQuoteObject_whenDelete_thenRemoveDailyQuote(){

        dailyQuoteRepository.save(dailyQuote);

        // when -  action or the behaviour that we are going test
        dailyQuoteRepository.deleteById(dailyQuote.getCotationId());
        Optional<Polygon_DailyQuote> dailyQuoteDeleted = dailyQuoteRepository.findById(dailyQuote.getCotationId());

        // then - verify the output
        assertThat(dailyQuoteDeleted).isEmpty();
    }

    // JUnit test for get all dailyQuote operation
    @Test
    public void findAllByDate() throws ParseException {
        // given - precondition or setup
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.FRANCE);
        Date date = formatter.parse("2023-01-20");

        // when -  action or the behaviour that we are going test
        List<Polygon_DailyQuote> dailyQuoteList = (List<Polygon_DailyQuote>) dailyQuoteRepository.findAllByDate(date);

        // then - verify the output
        assertThat(dailyQuoteList).isNotNull();
        assertThat(dailyQuoteList.size()).isEqualTo(1);

        Date date2 = formatter.parse("2023-01-13");

        // when -  action or the behaviour that we are going test
        List<Polygon_DailyQuote> dailyQuoteListKO = (List<Polygon_DailyQuote>) dailyQuoteRepository.findAllByDate(date2);

        // then - verify the output
        assertThat(dailyQuoteListKO).isNotNull();
        assertThat(dailyQuoteListKO.size()).isEqualTo(1);
        Polygon_DailyQuote polygon_dailyQuote = dailyQuoteListKO.get(0);
        assertThat(polygon_dailyQuote.getResultsCount().equals(0));

        Date date21 = formatter.parse("2023-01-21");

        // when -  action or the behaviour that we are going test
        List<Polygon_DailyQuote> dailyQuoteListdate21 = (List<Polygon_DailyQuote>) dailyQuoteRepository.findAllByDate(date21);

        // then - verify the output
        assertThat(dailyQuoteListdate21).isNotNull();
        assertThat(dailyQuoteListdate21.size()).isEqualTo(0);

    }
}