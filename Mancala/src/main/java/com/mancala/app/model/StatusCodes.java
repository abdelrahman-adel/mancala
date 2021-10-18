package com.mancala.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCodes {

	NO_RUNNING_GAME("001", "No games are currently being held!"),
	NOT_YOUR_TURN("002", "Not your turn"),
	USER_IS_DUPLICATE("003", "User is duplicate"),
	USER_NOT_ALLOWED_FOR_GAME("004", "User does not belong to game"),
	MISSING_GAME_ID("005", "Missing game id"),
	INVALID_PIT("006", "Invalid pit"),

	SYSTEM_MALFORMED_CHANNEL("998", "Malformed channel"),
	SYSTEM_GENERIC_FAILURE("999", "Unknown failure occured");

	private String code;
	private String desc;
}
