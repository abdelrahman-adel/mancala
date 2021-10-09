package com.mancala.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class MancalaWebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Autowired
	private GameValidityInterceptor gameValidityInterceptor;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/mancala").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// These are endpoints the client can subscribes to.
		registry.enableSimpleBroker("/game");

		// Message received with one of those below destinationPrefixes will be
		// automatically router to controllers @MessageMapping
		registry.setApplicationDestinationPrefixes("/make-move");
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(gameValidityInterceptor);
	}
}
