package com.mancala.app.service.impl;

import java.security.Principal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void validateUserWithGame(Principal user, String gameId) {

		String name = user.getName();
		GameSession gameSession = mancalaDao.findGameByAnyPlayer(name);

		if (gameSession == null || !gameSession.getId().equalsIgnoreCase(gameId)) {
			throw new MancalaBusinessException(StatusCodes.USER_NOT_ALLOWED_FOR_GAME);
		}
	}

	@Override
	public GameSession initiate(String user) {
		GameSession gameSession = findOrCreateGame(user);
		return gameSession;
	}

	private GameSession findOrCreateGame(String user) {
		GameSession gameSession = mancalaDao.findGameByStatus(GameStatus.PENDING);

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
	public GameBoard makeMove(String user, int pit) {
		GameSession gameSession = mancalaDao.findGameByAnyPlayer(user);
		validateGameSession(user, gameSession);
		// TODO make the move
		// TODO switch turns
		return mancalaDao.updateGameSession(gameSession).getGameBoard();
	}

	@Override
	public GameBoard check(String user) {
		GameSession gameSession = mancalaDao.findGameByAnyPlayer(user);
		validateGameSessionExistence(gameSession);
		return gameSession.getGameBoard();
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
