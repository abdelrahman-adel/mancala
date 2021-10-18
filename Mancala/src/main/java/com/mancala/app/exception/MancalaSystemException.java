package com.mancala.app.exception;

import com.mancala.app.model.StatusCodes;

import lombok.Getter;

@Getter
public class MancalaSystemException extends MancalaInternalException {

	private static final long serialVersionUID = -6306785195998807332L;

	public MancalaSystemException() {
		super(StatusCodes.SYSTEM_GENERIC_FAILURE);
	}

	public MancalaSystemException(StatusCodes statusCode) {
		super(statusCode);
	}
}
