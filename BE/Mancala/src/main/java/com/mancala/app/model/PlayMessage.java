package com.mancala.app.model;

import lombok.Data;

@Data
public class PlayMessage {

	private String user;
	private Integer pit;

	private String type;
	private String content;
	private String sender;
}
