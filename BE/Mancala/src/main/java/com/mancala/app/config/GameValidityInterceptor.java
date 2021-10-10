package com.mancala.app.config;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import com.mancala.app.exception.MancalaBusinessException;
import com.mancala.app.exception.MancalaSystemException;
import com.mancala.app.model.StatusCodes;
import com.mancala.app.service.MancalaService;

@Component
public class GameValidityInterceptor implements ChannelInterceptor {

	private static final String GAME_ID_HEADER = "gameId";

	@Autowired
	private MancalaService mancalaService;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {

		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
		Principal user = accessor.getUser();

		if (user == null) {
			throw new AuthenticationCredentialsNotFoundException("Missing authentication.");
		}

		String gameId;
		switch (accessor.getCommand()) {
			case CONNECT:
				gameId = accessor.getFirstNativeHeader(GAME_ID_HEADER);
				if (gameId == null || gameId.trim().isEmpty()) {
					throw new MancalaBusinessException(StatusCodes.MISSING_GAME_ID);
				}
				mancalaService.validateUserWithGame(user.getName(), gameId);
				break;
			case SUBSCRIBE:
				try {
					gameId = accessor.getDestination().split("/")[2];
					if (gameId.contains("?")) {
						gameId = gameId.substring(0, gameId.indexOf('?'));
					}
				} catch (Exception ex) {
					throw new MancalaSystemException(StatusCodes.SYSTEM_MALFORMED_CHANNEL);
				}
				mancalaService.validateUserWithGame(user.getName(), gameId);
				break;
			default:
				break;
		}

		return message;
	}

}