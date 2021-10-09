package com.mancala.app.service;

import java.security.Principal;

import com.mancala.app.model.GameBoard;
import com.mancala.app.model.GameSession;

public interface MancalaService {

	String initiate(String user);

	GameSession validateUserWithGame(Principal user, String gameId);

	GameBoard makeMove(String user, int pit);

}
