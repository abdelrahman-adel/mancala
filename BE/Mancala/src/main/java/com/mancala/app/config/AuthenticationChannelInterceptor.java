package com.mancala.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.mancala.app.exception.MancalaBusinessException;
import com.mancala.app.model.StatusCodes;
import com.mancala.app.security.config.MancalaAuthenticationService;

@Component
public class AuthenticationChannelInterceptor implements ChannelInterceptor {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationChannelInterceptor.class);

	private static final String USERNAME_HEADER = "login";
	private static final String PASSWORD_HEADER = "passcode";
	private static final String GAME_ID_HEADER = "gameId";

	@Autowired
	private MancalaAuthenticationService mancalaAuthenticationService;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {

		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		Object principal = authentication.getPrincipal();
		String usernameee = (String) principal;

		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		log.debug("Hell yeah principal found : {} , at message type : {}", usernameee, accessor.getCommand());

		if (StompCommand.CONNECT == accessor.getCommand()) {
			String username = accessor.getFirstNativeHeader(USERNAME_HEADER);
			String password = accessor.getFirstNativeHeader(PASSWORD_HEADER);

//			UsernamePasswordAuthenticationToken user = mancalaAuthenticationService.getAuthenticatedOrFail(username, password);
			accessor.setUser(authentication);

			String gameId = accessor.getFirstNativeHeader(GAME_ID_HEADER);

			if (gameId == null || gameId.trim().isEmpty()) {
				throw new MancalaBusinessException(StatusCodes.USER_MISSING_GAME_ID);
			}

			// TODO validate username and gameId
			if (gameId.trim() == null) {
				throw new MancalaBusinessException(StatusCodes.USER_NOT_ALLOWED_FOR_GAME);
			}
		}
		return message;
	}

}