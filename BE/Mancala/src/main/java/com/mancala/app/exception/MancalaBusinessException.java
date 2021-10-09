package com.mancala.app.exception;

import com.mancala.app.model.StatusCodes;

import lombok.Getter;

@Getter
public class MancalaBusinessException extends MancalaInternalException {

	private static final long serialVersionUID = 2053469177656520580L;

	public MancalaBusinessException(StatusCodes statusCode) {
		super(statusCode);
	}
}
