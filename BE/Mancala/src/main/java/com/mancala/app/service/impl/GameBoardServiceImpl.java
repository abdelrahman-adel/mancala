package com.mancala.app.service.impl;

import org.springframework.stereotype.Service;

import com.mancala.app.model.GameBoard;
import com.mancala.app.model.GameSession;
import com.mancala.app.model.GameStatus;
import com.mancala.app.model.GameTurn;
import com.mancala.app.service.GameBoardService;

@Service
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
	public void makeMove(GameSession gameSession, int pit) {

		GameTurn player = gameSession.getTurn();

		int[] pits = gameSession.getGameBoard().getPits();

		int pitsToGo = pits[pit];
		pits[pit] = 0;

		int i = pit + 1;

		GameTurn turn = null;
		while (true) {

			pitsToGo--;
			pits[i]++;
			if (pitsToGo == 0) {
				if (GameTurn.P1 == player) {
					if (i == 6) {
						turn = GameTurn.P1;
					} else if (pits[i] == 1) {
						turn = GameTurn.P2;
					}
				} else {
					if (i == 13) {
						turn = GameTurn.P2;
					} else if (pits[i] == 1) {
						turn = GameTurn.P1;
					}
				}
				if (turn != null) {
					break;
				}
				pitsToGo = pits[i];
				pits[i] = 0;
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

		gameSession.setTurn(turn);
		if (isGameFinished(pits)) {
			gameSession.setStatus(GameStatus.FINISHED);
			if (pits[6] > pits[13])
				gameSession.setWinner(gameSession.getPlayer1());
			else if (pits[6] < pits[13])
				gameSession.setWinner(gameSession.getPlayer2());
			else if (pits[6] == pits[13])
				gameSession.setWinner("DRAW");
		}
	}

	private boolean isGameFinished(int[] pits) {

		int p1Zeroes = 0;
		int p2Zeroes = 0;
		for (int i = 0; i < 7; i++) {
			if (pits[i] == 0)
				p1Zeroes++;
			if (pits[i + 7] == 0)
				p2Zeroes++;
		}

		return p1Zeroes == 6 || p2Zeroes == 6 ? true : false;
	}

}
