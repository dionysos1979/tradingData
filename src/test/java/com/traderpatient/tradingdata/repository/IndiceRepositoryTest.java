package com.traderpatient.tradingdata.repository;

import com.traderpatient.tradingdata.dao.IndiceRepository;
import com.traderpatient.tradingdata.model.Indice;
import com.traderpatient.tradingdata.model.Indices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IndiceRepositoryTest {

    @Autowired
    private IndiceRepository repository;

    private Indice objectTested;

    @BeforeEach
    public void setup() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.FRANCE);
        objectTested = new Indice("NASDAQ", "NASDAQ");
        objectTested.setName("NASDAQ");

        Indice objectSP500 = new Indice("SP500", "SP500");
        objectSP500.setName("SP500");
        repository.save(objectSP500);
    }

    // JUnit test for save Object operation
    @Test
    public void givenObject_whenSave_thenReturnSavedObject(){
        // when - action or the behaviour that we are going test
        Indice objectSaved = repository.save(objectTested);

        // then - verify the output
        assertThat(objectSaved).isNotNull();
        assertThat(objectSaved.getId()).isGreaterThan(0);
    }

    // JUnit test for get all dailyQuote operation
    @Test
    public void givenObjectList_whenFindAll_thenObjectList(){
        // given - precondition or setup

        // when -  action or the behaviour that we are going test
        List<Indice> objectList = (List<Indice>) repository.findAll();

        // then - verify the output
        assertThat(objectList).isNotNull();
        assertThat(objectList.size()).isGreaterThan(0);

    }

    // JUnit test for get Object by id operation
    @DisplayName("JUnit test for get Object by id operation")
    @Test
    public void givenObject_whenFindById_thenReturnObject(){
        repository.save(objectTested);

        // when -  action or the behaviour that we are going test
        Indice objectDB = repository.findById(objectTested.getId()).get();

        // then - verify the output
        assertThat(objectDB).isNotNull();
    }

    // JUnit test for update object operation
    @DisplayName("JUnit test for update object operation")
    @Test
    public void givenObject_whenUpdateObject_thenReturnUpdatedObject(){

        repository.save(objectTested);

        // when -  action or the behaviour that we are going test
        Indice objectDB = repository.findById(objectTested.getId()).get();
        objectDB.setName("SP500");
        Indice updatedObject =  repository.save(objectDB);

        // then - verify the output
        assertThat(updatedObject.getName().equals(Indices.SP500));
    }

    // JUnit test for delete Quote operation
    @DisplayName("JUnit test for delete Object operation")
    @Test
    public void givenObject_whenDelete_thenRemoveObject(){

        repository.save(objectTested);

        // when -  action or the behaviour that we are going test
        repository.deleteById(objectTested.getId());
        Optional<Indice> objectDeleted = repository.findById(objectTested.getId());

        // then - verify the output
        assertThat(objectDeleted).isEmpty();
    }
}