package com.mancala.app.service;

import java.security.Principal;

import com.mancala.app.model.GameBoard;
import com.mancala.app.model.GameSession;

public interface MancalaService {

	void validateUserWithGame(Principal user, String gameId);

	GameSession initiate(String user);

	GameBoard makeMove(String user, int pit);

	GameBoard check(String user);

}
