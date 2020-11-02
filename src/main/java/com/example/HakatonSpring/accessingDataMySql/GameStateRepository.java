package com.example.HakatonSpring.accessingDataMySql;

import com.example.HakatonSpring.model.GameStateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface GameStateRepository extends JpaRepository<GameStateModel, Integer> {

	 GameStateModel findById(int id);
	 List<GameStateModel> findByStartedAndDisconnect(boolean started, boolean disconnect);
	 GameStateModel findTopByPlayer1IdOrPlayer2Id(String player1Id, String player2Id);

}
