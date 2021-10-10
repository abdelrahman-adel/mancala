package com.mancala.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.mancala.app.dao.GameSessionDAO;
import com.mancala.app.exception.MancalaBusinessException;
import com.mancala.app.model.GameBoard;
import com.mancala.app.model.GameSession;
import com.mancala.app.model.GameStatus;
import com.mancala.app.model.StatusCodes;
import com.mancala.app.service.MancalaService;

@Service
public class MancalaServiceImpl implements MancalaService {

	@Autowired
	private GameSessionDAO mancalaDao;

	@Override
	public String initiate(String user) {
		// find user IN_PROGRESS or PENDING game for disconnected users
		GameSession gameSession = findUserPendingOrInprogressGames(user);

		if (gameSession == null) {
			// user doesn't have a running game, then engage him in a one
			gameSession = findOrCreatePendingGame(user);
		}

		return gameSession.getId();
	}

	private GameSession findUserPendingOrInprogressGames(String user) {
		List<GameStatus> statuses = new ArrayList<>();
		statuses.add(GameStatus.IN_PROGRESS);
		statuses.add(GameStatus.PENDING);
		return mancalaDao.findGameByStatusAndAnyPlayer(user, statuses);
	}

	private GameSession findOrCreatePendingGame(String user) {
		List<GameSession> gameSessions = mancalaDao.findGameByStatus(GameStatus.PENDING);
		GameSession gameSession = CollectionUtils.isEmpty(gameSessions) ? null : gameSessions.get(0);

		if (gameSession == null) {
			gameSession = new GameSession();
			gameSession.setStatus(GameStatus.PENDING);
			String gameId = UUID.randomUUID().toString().replace("-", "");
			gameSession.setId(gameId);
			gameSession.setPlayer1(user);
			gameSession.setTurn(user);
			gameSession.setGameBoard(createGameBoard());

			mancalaDao.createGameSession(gameSession);
		} else {
			gameSession.setPlayer2(user);
			gameSession.setStatus(GameStatus.IN_PROGRESS);
			mancalaDao.updateGameSession(gameSession);
		}
		return gameSession;
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

	@Override
	public GameSession validateUserWithGame(String user, String gameId) {

		List<GameStatus> statuses = new ArrayList<>();
		statuses.add(GameStatus.IN_PROGRESS);
		statuses.add(GameStatus.PENDING);
		GameSession gameSession = mancalaDao.findGameByStatusAndAnyPlayer(user, statuses);

		if (gameSession == null || !gameSession.getId().equalsIgnoreCase(gameId)) {
			throw new MancalaBusinessException(StatusCodes.USER_NOT_ALLOWED_FOR_GAME);
		}

		return gameSession;
	}

	@Override
	public GameSession makeMove(GameSession gameSession, String player, int pit) {
		validateMove(gameSession, player, pit);
		// TODO make the move
		// TODO switch turns and update the DB
		return mancalaDao.updateGameSession(gameSession);
	}

	private void validateMove(GameSession gameSession, String player, int pit) {
		if (!player.equals(gameSession.getTurn())) {
			throw new MancalaBusinessException(StatusCodes.NOT_YOUR_TURN);
		}
		if (player.equals(gameSession.getPlayer1()) && (pit < 0 || pit > 5)) {
			throw new MancalaBusinessException(StatusCodes.INVALID_PIT);
		}
		if (player.equals(gameSession.getPlayer2()) && (pit < 7 || pit > 12)) {
			throw new MancalaBusinessException(StatusCodes.INVALID_PIT);
		}
	}

}
