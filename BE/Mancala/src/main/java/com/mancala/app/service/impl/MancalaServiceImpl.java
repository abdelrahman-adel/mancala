package com.mancala.app.service.impl;

import java.security.Principal;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameSession validateUserWithGame(Principal user, String gameId) {

		List<GameStatus> statuses = new ArrayList<>();
		statuses.add(GameStatus.IN_PROGRESS);
		statuses.add(GameStatus.PENDING);
		GameSession gameSession = mancalaDao.findGameByStatusAndAnyPlayer(user.getName(), statuses);

		if (gameSession == null || !gameSession.getId().equalsIgnoreCase(gameId)) {
			throw new MancalaBusinessException(StatusCodes.USER_NOT_ALLOWED_FOR_GAME);
		}

		return gameSession;
	}

	@Override
	public GameBoard makeMove(String user, int pit) {
		GameSession gameSession = null;// mancalaDao.findGameByStatusAndAnyPlayer(user);
		validateGameSession(user, gameSession);
		// TODO make the move
		// TODO switch turns and update the DB
		return mancalaDao.updateGameSession(gameSession).getGameBoard();
	}

	private void validateGameSession(String user, GameSession gameSession) {
		validateGameSessionExistence(gameSession);
		if (!user.equals(gameSession.getTurn())) {
			throw new MancalaBusinessException(StatusCodes.NOT_YOUR_TURN);
		}
	}

	private void validateGameSessionExistence(GameSession gameSession) {
		if (gameSession == null) {
			throw new MancalaBusinessException(StatusCodes.NO_RUNNING_GAME);
		}
	}

}
