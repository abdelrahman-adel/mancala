package com.mancala.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mancala.app.dao.GameSessionDAO;
import com.mancala.app.model.GameSession;
import com.mancala.app.model.GameStatus;
import com.mancala.app.repository.GameSessionRepository;

@Service
public class GameSessionDAOImpl implements GameSessionDAO {

	@Autowired
	private GameSessionRepository gameSessionRepository;

	@Override
	public GameSession findGameByStatusAndAnyPlayer(String user, List<GameStatus> statuses) {
		return gameSessionRepository.findGameByStatusAndPlayer1OrPlayer2(user, user, statuses);
	}

	@Override
	public List<GameSession> findGameByStatus(GameStatus status) {
		return gameSessionRepository.findByStatus(status);
	}

	@Override
	public GameSession createGameSession(GameSession gameSession) {
		return gameSessionRepository.save(gameSession);
	}

	@Override
	public GameSession updateGameSession(GameSession gameSession) {
		return gameSessionRepository.save(gameSession);
	}

}
