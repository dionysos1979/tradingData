package com.traderpatient.tradingdata.dao;


import com.traderpatient.tradingdata.model.Score;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScoreRepository extends CrudRepository<Score, Integer> {
    List<Score> findAllByDate(Date date);
    List<Score> findAllByTicker(String ticker);
}
