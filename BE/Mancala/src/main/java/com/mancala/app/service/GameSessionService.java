package com.mancala.app.service;

import com.mancala.app.model.GameSession;
import com.mancala.app.model.InitiateRs;

public interface GameSessionService {

	InitiateRs initiate(String user);

	GameSession validateUserWithGame(String user, String gameId);

	GameSession makeMove(GameSession gameSession, String player, int pit);

}
