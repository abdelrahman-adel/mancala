package com.mancala.app.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.mancala.app.config.AuthenticationChannelInterceptor;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class MancalaWebSocketAuthenticationConfig implements WebSocketMessageBrokerConfigurer {

	@Autowired
	private AuthenticationChannelInterceptor authenticationChannelInterceptor;

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(authenticationChannelInterceptor);
	}

}