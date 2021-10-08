package com.mancala.app.service;

import com.mancala.app.model.GameBoard;
import com.mancala.app.model.GameSession;

public interface MancalaService {

	GameSession initiate(String user);

	GameBoard makeMove(String user, int pit);

	GameBoard check(String user);

}
