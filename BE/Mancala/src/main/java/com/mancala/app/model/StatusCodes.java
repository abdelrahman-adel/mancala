package com.mancala.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCodes {

	USER_NO_RUNNING_GAME("001", "No games are currently being held!"),
	USER_NOT_YOUR_TURN("002", "Not your turn"),
	USER_IS_DUPLICATE("003", "User is duplicate"),
	USER_NOT_ALLOWED_FOR_GAME("004", "User does not belong to game"),
	USER_MISSING_GAME_ID("005", "Missing game id"),
	SYSTEM_GENERIC_FAILURE("999", "Unknown failure occured");

	private String code;
	private String desc;

}
