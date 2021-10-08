package com.mancala.app.dao.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mancala.app.dao.GameSessionDAO;
import com.mancala.app.model.GameSession;
import com.mancala.app.model.GameStatus;
import com.mancala.app.repository.GameSessionRepository;

@Service
public class GameSessionDAOImpl implements GameSessionDAO {

	private GameSessionRepository gameSessionRepository;

	@Override
	public GameSession findGameByAnyPlayer(String user) {
		List<GameSession> games = gameSessionRepository.findByPlayer1OrPlayer2(user, user);
		return games != null ? games.get(0) : null;
	}

	@Override
	public GameSession createGameSession(GameSession gameSession) {
		return gameSessionRepository.save(gameSession);
	}

	@Override
	public GameSession updateGameSession(GameSession gameSession) {
		return gameSessionRepository.save(gameSession);
	}

	@Override
	public GameSession findGameByStatus(GameStatus status) {
		// TODO Auto-generated method stub
		return null;
	}

}
