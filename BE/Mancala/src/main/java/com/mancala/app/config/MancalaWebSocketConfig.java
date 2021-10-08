package com.mancala.app.config;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.UriComponentsBuilder;

import com.mancala.app.exception.MancalaBusinessException;
import com.mancala.app.model.StatusCodes;

@Configuration
@EnableWebSocketMessageBroker
public class MancalaWebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Autowired
	private MyHandshakeHandler myHandshakeHandler;
	@Autowired
	private MyHandshakeInterceptor myHandshakeInterceptor;

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/mancala").setHandshakeHandler(myHandshakeHandler).addInterceptors(myHandshakeInterceptor).withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/game");
		registry.setApplicationDestinationPrefixes("/make-move");
	}

	@Bean
	public DefaultHandshakeHandler defaultHandshakeHandler() {
		return new DefaultHandshakeHandler();
	}

}

@Service
class MyHandshakeInterceptor implements HandshakeInterceptor {

	private static final String GAME_ID = "gameId";
	private static final Logger log = LoggerFactory.getLogger(MyHandshakeInterceptor.class);

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

		log.info("Handshake began: RQ: {}", request);
		log.info("Handshake began: RQ URL: {}", request.getURI().toString());
		log.info("Handshake began: RQ Principal: {}", request.getPrincipal());

		MultiValueMap<String, String> queryParams = UriComponentsBuilder.fromUri(request.getURI()).build().getQueryParams();
		List<String> gameIdVals = queryParams.get(GAME_ID);
		if (gameIdVals != null && gameIdVals.size() == 1) {
			gameIdVals.get(0);
			// TODO check if the game belongs to him or not
			log.info("Handshake successful: {}", gameIdVals.get(0));
			return true;
		}
		log.info("Handshake failed");
		throw new MancalaBusinessException(StatusCodes.USER_NOT_ALLOWED_FOR_GAME);
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
		// TODO Auto-generated method stub

	}

}
