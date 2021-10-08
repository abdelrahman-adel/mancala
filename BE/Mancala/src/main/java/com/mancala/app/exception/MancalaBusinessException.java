package com.mancala.app.exception;

import com.mancala.app.model.StatusCodes;

import lombok.Getter;

@Getter
public class MancalaBusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2053469177656520580L;

	private StatusCodes statusCode;

	public MancalaBusinessException() {
		super(StatusCodes.SYSTEM_GENERIC_FAILURE.getDesc());
		this.statusCode = StatusCodes.SYSTEM_GENERIC_FAILURE;
	}

	public MancalaBusinessException(StatusCodes statusCode) {
		super(statusCode.getDesc());
		this.statusCode = statusCode;
	}
}
