package com.mancala.app.exception;

import com.mancala.app.model.StatusCodes;

import lombok.Getter;

@Getter
public class MancalaInternalException extends RuntimeException {

	private static final long serialVersionUID = 8436420540110195290L;

	protected StatusCodes statusCode;

	protected MancalaInternalException() {
		super(StatusCodes.SYSTEM_GENERIC_FAILURE.getDesc());
		this.statusCode = StatusCodes.SYSTEM_GENERIC_FAILURE;
	}

	protected MancalaInternalException(StatusCodes statusCode) {
		super(statusCode.getDesc());
		this.statusCode = statusCode;
	}
}
