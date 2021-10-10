package com.mancala.app.service;

import com.mancala.app.model.GameSession;

public interface MancalaService {

	String initiate(String user);

	GameSession validateUserWithGame(String user, String gameId);

	GameSession makeMove(GameSession gameSession, String player, int pit);

}
