package com.mancala.app.service;

import com.mancala.app.model.GameBoard;
import com.mancala.app.model.GameSession;

public interface GameBoardService {

	GameBoard createGameBoard();

	void makeMove(GameSession gameSession, int pit);

}
