package com.mancala.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mancala.app.model.GameBoard;
import com.mancala.app.model.GameSession;
import com.mancala.app.model.GameStatus;
import com.mancala.app.model.GameTurn;

@SpringBootTest
class GameBoardServiceTest {

	@Autowired
	private GameBoardService gameBoardService;

	@Test
	void testCreateGameBoard() {
		GameBoard gameBoard = gameBoardService.createGameBoard();
		assertEquals(createGameBoard(), gameBoard);
	}

	@Test
	void testMakeMove_Pit4() {
		GameSession game = createMockGameSession(GameTurn.P1);
		gameBoardService.makeMove(game, 4);

		assertEquals(createMockGameSessionPit4(), game);
	}

	private GameBoard createGameBoard() {
		GameBoard board = new GameBoard();
		int[] pits = board.getPits();
		for (int i = 0; i < pits.length; i++) {
			if (i == 6 || i == 13) {
				pits[i] = 0;
			} else {
				pits[i] = 6;
			}
		}
		return board;
	}

	private GameSession createMockGameSession(GameTurn turn) {
		GameSession game = new GameSession();
		game.setId("gameId");
		game.setPlayer1("player1");
		game.setPlayer2("player2");
		game.setTurn(turn);
		game.setStatus(GameStatus.IN_PROGRESS);
		game.setGameBoard(createGameBoard());
		return game;
	}

	private GameSession createMockGameSessionPit4() {
		GameSession game = createMockGameSession(GameTurn.P2);
		int[] pits = game.getGameBoard().getPits();
		pits[0] = 7;
		pits[1] = 7;
		pits[2] = 7;
		pits[3] = 7;
		pits[4] = 1;
		pits[5] = 7;
		pits[6] = 1;
		pits[7] = 7;
		pits[8] = 7;
		pits[9] = 7;
		pits[10] = 0;
		pits[11] = 7;
		pits[12] = 7;
		pits[13] = 0;
		return game;
	}

}
