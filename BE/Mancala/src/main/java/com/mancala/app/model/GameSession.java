package com.mancala.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Document("game_session")
public class GameSession {

	@JsonIgnore
	@Id
	private String id;
	private String player1;
	private String player2;
	private String turn;
	private GameStatus status;
	private GameBoard gameBoard;
}
