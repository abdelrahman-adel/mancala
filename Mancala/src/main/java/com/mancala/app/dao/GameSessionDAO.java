package com.mancala.app.dao;

import java.util.List;

import com.mancala.app.model.GameSession;
import com.mancala.app.model.GameStatus;

public interface GameSessionDAO {

	GameSession findGameByStatusAndAnyPlayer(String user, List<GameStatus> statuses);

	List<GameSession> findGameByStatus(GameStatus status);

	GameSession updateGameSession(GameSession gameSession);

	GameSession createGameSession(GameSession gameSession);

}
