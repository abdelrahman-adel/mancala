package com.mancala.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mancala.app.dao.GameSessionDAO;
import com.mancala.app.model.GameBoard;
import com.mancala.app.model.GameSession;
import com.mancala.app.model.GameStatus;
import com.mancala.app.model.GameTurn;
import com.mancala.app.model.InitiateRs;

@SpringBootTest
class GameSessionServiceTest {

	@Autowired
	private GameSessionService gameSessionService;

	@MockBean
	private GameSessionDAO gameSessionDAO;

	@Test
	void testInitiate_UserHasGame() {
		GameSession game = createMockGameSession(GameTurn.P1);
		when(gameSessionDAO.findGameByStatusAndAnyPlayer(anyString(), anyList())).thenReturn(game);

		InitiateRs initiateRs = gameSessionService.initiate("player");

		assertEquals(game.getId(), initiateRs.getGameId());
	}

	@Test
	void testInitiate_NewGame() {
		GameSession game = createMockGameSession(GameTurn.P1);
		List<GameSession> games = new ArrayList<>();
		games.add(game);
		when(gameSessionDAO.findGameByStatus(any())).thenReturn(games);

		InitiateRs initiateRs = gameSessionService.initiate("player");

		assertEquals(game.getId(), initiateRs.getGameId());
	}

	@Test
	void testValidateUserWithGame(String user, String gameId) {
		GameSession game = gameSessionService.validateUserWithGame(user, gameId);
		fail("Not yet implemented");
	}

	@Test
	void testMakeMove(GameSession gameSession, String player, int pit) {
		GameSession game = gameSessionService.makeMove(gameSession, player, pit);
		fail("Not yet implemented");
	}

	private GameSession createMockGameSession(GameTurn turn) {
		GameSession game = new GameSession();
		game.setId("gameId");
		game.setPlayer1("player1");
		game.setPlayer2("player2");
		game.setTurn(turn);
		game.setStatus(GameStatus.IN_PROGRESS);

		GameBoard board = new GameBoard();
		int[] pits = board.getPits();
		for (int i = 0; i < pits.length; i++) {
			if (i == 6 || i == 13) {
				pits[i] = 0;
			} else {
				pits[i] = 6;
			}
		}
		game.setGameBoard(board);

		return game;
	}

}
