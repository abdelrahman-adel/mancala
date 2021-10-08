package com.mancala.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class GameBoard {

	@JsonIgnore
	@Id
	@GeneratedValue
	private long id;
	private String[] pits = new String[14];
}
