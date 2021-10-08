package com.mancala.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class GameBoard {

	@JsonIgnore
	private long id;
	private String[] pits = new String[14];
}
