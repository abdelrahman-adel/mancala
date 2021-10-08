package com.mancala.app.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class GameSession {

	@JsonIgnore
	@Id
	private String id;
	private String player1;
	private String player2;
	private String turn;
	private GameStatus status;

	@OneToOne(fetch = FetchType.EAGER)
	private GameBoard gameBoard;
}
