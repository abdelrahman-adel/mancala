package com.mancala.app.service.impl;

import com.mancala.app.model.GameBoard;
import com.mancala.app.model.GameTurn;
import com.mancala.app.service.GameBoardService;

public class GameBoardServiceImpl implements GameBoardService {

	@Override
	public GameBoard createGameBoard() {
		GameBoard board = new GameBoard();
		int[] pits = board.getPits();
		for (int i = 0; i < pits.length; i++) {
			if (i == 6 || i == 13) {
				pits[i] = 0;
			} else {
				pits[i] = 6;
			}
		}
		return board;
	}

	@Override
	public GameTurn makeMove(GameBoard board, GameTurn player, int pit) {

		int[] pits = board.getPits();

		int pitsToGo = pits[pit];
		pits[pit] = 0;

		int i = pit + 1;

		GameTurn turn = null;
		while (turn == null) {

			pitsToGo--;
			if (pitsToGo == 0) {
				if (GameTurn.P1 == player) {
					if (i == 6) {
						turn = GameTurn.P1;
					} else if (pits[i] == 0) {
						turn = GameTurn.P2;
					}
				} else {
					if (i == 13) {
						turn = GameTurn.P2;
					} else if (pits[i] == 0) {
						turn = GameTurn.P1;
					}
				}
				pitsToGo = pits[i] + 1;
				pits[i] = 0;
			} else {
				pits[i]++;
			}

			if (GameTurn.P1 == player) {
				i = i == 12 ? 0 : i + 1;
			} else {
				if (i == 5) {
					i = 7;
				} else {
					i = i == 13 ? 0 : i + 1;
				}
			}
		}

		return turn;
	}

}
