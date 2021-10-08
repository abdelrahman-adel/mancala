package com.mancala.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.mancala.app.security.config.MancalaAuthenticationService;

@Component
public class AuthenticationChannelInterceptor implements ChannelInterceptor {

	private static final String USERNAME_HEADER = "login";
	private static final String PASSWORD_HEADER = "passcode";

	@Autowired
	private MancalaAuthenticationService mancalaAuthenticationService;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {

		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		if (StompCommand.CONNECT == accessor.getCommand()) {
			String username = accessor.getFirstNativeHeader(USERNAME_HEADER);
			String password = accessor.getFirstNativeHeader(PASSWORD_HEADER);

			UsernamePasswordAuthenticationToken user = mancalaAuthenticationService.getAuthenticatedOrFail(username, password);

			accessor.setUser(user);
		}
		return message;
	}

}