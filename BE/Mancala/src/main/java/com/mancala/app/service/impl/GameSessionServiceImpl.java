package com.mancala.app.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.mancala.app.dao.GameSessionDAO;
import com.mancala.app.exception.MancalaBusinessException;
import com.mancala.app.model.GameSession;
import com.mancala.app.model.GameStatus;
import com.mancala.app.model.GameTurn;
import com.mancala.app.model.StatusCodes;
import com.mancala.app.service.GameBoardService;
import com.mancala.app.service.GameSessionService;

@Service
public class GameSessionServiceImpl implements GameSessionService {

	@Autowired
	private GameBoardService gameBoardService;

	@Autowired
	private GameSessionDAO gameSessionDAO;

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

	@Override
	public GameSession validateUserWithGame(String user, String gameId) {

		List<GameStatus> statuses = new ArrayList<>();
		statuses.add(GameStatus.IN_PROGRESS);
		statuses.add(GameStatus.PENDING);
		GameSession gameSession = gameSessionDAO.findGameByStatusAndAnyPlayer(user, statuses);

		if (gameSession == null || !gameSession.getId().equalsIgnoreCase(gameId)) {
			throw new MancalaBusinessException(StatusCodes.USER_NOT_ALLOWED_FOR_GAME);
		}

		return gameSession;
	}

	@Override
	public GameSession makeMove(GameSession gameSession, String player, int pit) {

		validateMove(gameSession, player, pit);
		GameTurn newTurn = gameBoardService.makeMove(gameSession.getGameBoard(), gameSession.getTurn(), pit);

		gameSession.setTurn(newTurn);
		return gameSessionDAO.updateGameSession(gameSession);
	}

	private GameSession findUserPendingOrInprogressGames(String user) {
		List<GameStatus> statuses = new ArrayList<>();
		statuses.add(GameStatus.IN_PROGRESS);
		statuses.add(GameStatus.PENDING);
		return gameSessionDAO.findGameByStatusAndAnyPlayer(user, statuses);
	}

	private GameSession findOrCreatePendingGame(String user) {
		List<GameSession> gameSessions = gameSessionDAO.findGameByStatus(GameStatus.PENDING);
		GameSession gameSession = CollectionUtils.isEmpty(gameSessions) ? null : gameSessions.get(0);

		if (gameSession == null) {
			gameSession = new GameSession();
			gameSession.setStatus(GameStatus.PENDING);
			String gameId = UUID.randomUUID().toString().replace("-", "");
			gameSession.setId(gameId);
			gameSession.setPlayer1(user);
			gameSession.setGameBoard(gameBoardService.createGameBoard());

			gameSessionDAO.createGameSession(gameSession);
		} else {
			gameSession.setPlayer2(user);
			gameSession.setStatus(GameStatus.IN_PROGRESS);
			gameSessionDAO.updateGameSession(gameSession);
		}
		return gameSession;
	}

	private void validateMove(GameSession gameSession, String player, int pit) {

		switch (gameSession.getTurn()) {
			case P1:
				if (!player.equals(gameSession.getPlayer1())) {
					throw new MancalaBusinessException(StatusCodes.NOT_YOUR_TURN);
				}
				break;
			case P2:
				if (!player.equals(gameSession.getPlayer2())) {
					throw new MancalaBusinessException(StatusCodes.NOT_YOUR_TURN);
				}
				break;
		}
		if (player.equals(gameSession.getPlayer1()) && (pit < 0 || pit > 5)) {
			throw new MancalaBusinessException(StatusCodes.INVALID_PIT);
		}
		if (player.equals(gameSession.getPlayer2()) && (pit < 7 || pit > 12)) {
			throw new MancalaBusinessException(StatusCodes.INVALID_PIT);
		}
	}

}
