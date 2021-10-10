package com.mancala.app.service;

import com.mancala.app.model.GameBoard;
import com.mancala.app.model.GameTurn;

public interface GameBoardService {

	GameBoard createGameBoard();

	GameTurn makeMove(GameBoard board, GameTurn player, int pit);

}
