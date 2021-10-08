package com.mancala.app.dao;

import com.mancala.app.model.GameSession;
import com.mancala.app.model.GameStatus;

public interface GameSessionDAO {

	GameSession findGameByAnyPlayer(String user);

	GameSession updateGameSession(GameSession gameSession);

	GameSession createGameSession(GameSession gameSession);

	GameSession findGameByStatus(GameStatus status);

}
