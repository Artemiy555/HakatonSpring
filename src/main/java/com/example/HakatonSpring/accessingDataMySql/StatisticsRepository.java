package com.example.HakatonSpring.accessingDataMySql;

import com.example.HakatonSpring.model.Games;
import com.example.HakatonSpring.model.Statistics;
import com.example.HakatonSpring.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StatisticsRepository extends CrudRepository<Statistics, Integer> {
    List<Statistics> findAllByUser(User user);

    boolean existsByGamesAndUser(Games games, User user);

    List<Statistics> findAllByGamesAndUser(Games games, User user);
}
