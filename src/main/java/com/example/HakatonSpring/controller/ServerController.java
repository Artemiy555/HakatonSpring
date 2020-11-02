package com.example.HakatonSpring.controller;

import com.example.HakatonSpring.accessingDataMySql.GameStateRepository;
import com.example.HakatonSpring.model.AvailableGame;
import com.example.HakatonSpring.model.GameStateModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@RestController
public class ServerController {

	@Autowired
	private GameStateRepository repository;

	@Autowired
	private SimpMessagingTemplate template;

	private static final Logger logger = LoggerFactory.getLogger(ServerController.class);

	@RequestMapping(method = RequestMethod.GET, value = "/ttt/games")
	public List<AvailableGame> getGames() {
		List<AvailableGame> available = new ArrayList<AvailableGame>();
		List<GameStateModel> games = repository.findByStartedAndDisconnect(false, false);

		for(GameStateModel game : games) {
			available.add(new AvailableGame(game.id, game.name, game.getBet()));
		}

		return available;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/ttt/game")
	public GameStateModel createGame(
			@RequestParam(value = "player") String player,
			@RequestParam(value = "name", defaultValue="A TicTacToe Game") String name,
			@RequestParam(value = "bet", defaultValue="10") int bet){

		logger.info("-----------------------------------"+player);

		GameStateModel game = new GameStateModel(player, name,bet);
		repository.save(game);

		return game;
	}

	// Join
	@RequestMapping(method = RequestMethod.PATCH, value = "/ttt/game", params = {"id", "player"})
	public GameStateModel joinGame(
			@RequestParam(value = "id") String id,
			@RequestParam(value = "player") String player
			) {

		GameStateModel game = repository.findById(Integer.parseInt(id));

		if (!game.started && !game.disconnect) {
			game.join(player);

			repository.save(game);
			updateGameState(id, game);

			return game;
		}

		// return null if third player is trying to join or player left
		return null;
	}

	// Disconnect
	@RequestMapping(method = RequestMethod.PATCH, value = "/ttt/game", params = {"id", "player", "disconnect"})
	public GameStateModel disconnectGame(
			@RequestParam(value = "id") String id,
			@RequestParam(value = "player") String player,
			@RequestParam(value = "disconnect") boolean disconnect) {

		GameStateModel game = repository.findById(Integer.parseInt(id));

		if (disconnect) {
			game.disconnect(player);
			repository.save(game);

			updateGameState(id, game);

			return game;
		}

		return null;
	}

	// Rematch
	@RequestMapping(method = RequestMethod.PATCH, value = "/ttt/game", params = {"id", "player", "rematch"})
	public GameStateModel rematchGame(
			@RequestParam(value = "id") String id,
			@RequestParam(value = "player") String player,
			@RequestParam(value = "rematch") boolean rematch) {

		GameStateModel game = repository.findById(Integer.parseInt(id));

		if (rematch) {
			game.rematch(player);

			repository.save(game);
			updateGameState(id, game);

			return game;
		}

		return null;
	}

	// push new game state to websocket
	private void updateGameState(String id, GameStateModel game) {
		template.convertAndSend("/ttt/gamestate/" + id, game);
	}

}
