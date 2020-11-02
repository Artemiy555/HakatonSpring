package com.example.HakatonSpring.accessingDataMySql;


import com.example.HakatonSpring.model.Games;
import com.example.HakatonSpring.model.History;
import com.example.HakatonSpring.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HistoryRepository extends CrudRepository<History, Integer> {
    List<History> findAllByUser(User user);
    List<History> findAllByGamesAndUser(Games games, User user);
    boolean existsByGamesAndUser(Games games, User user);
}
