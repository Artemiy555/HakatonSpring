package com.example.HakatonSpring.controller;

import com.example.HakatonSpring.accessingDataMySql.GameStateRepository;
import com.example.HakatonSpring.model.GameStateModel;
import com.example.HakatonSpring.model.PlayerJoinMessage;
import com.example.HakatonSpring.model.PlayerMoveMessage;
import com.example.HakatonSpring.service.WinnerTtt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;


@Controller
public class GameController {

	@Autowired
	private WinnerTtt winnerTtt;

	@Autowired
	private GameStateRepository repository;

	@MessageMapping("/move/{id}")
	@SendTo("/ttt/gamestate/{id}")
	public GameStateModel gamestate(@DestinationVariable String id, PlayerMoveMessage move) throws Exception {
		GameStateModel game = repository.findById(Integer.parseInt(id));

		String winner = game.makeMove(move.getX(), move.getY(), move.getPlayer());
		if (winner != null) {
			winnerTtt.winner(winner, game.getBet());
			System.out.println("wwwwwww "+ winner +"    "+ game.getPlayer1()+ "    "+ game.getPlayer2());
			winnerTtt.loser(game.getPlayer1().equalsIgnoreCase(winner) ? game.getPlayer2() : game.getPlayer1(), game.getBet());
		}
		repository.save(game);

		return game;
	}

	@MessageMapping("join/{id}")
	public void join(SimpMessageHeaderAccessor headerAccessor, @DestinationVariable String id, PlayerJoinMessage message) {
		GameStateModel game = repository.findById(Integer.parseInt(id));

		game.websocketJoin(message.getPlayer(), headerAccessor.getSessionId());
		repository.save(game);
	}

}
