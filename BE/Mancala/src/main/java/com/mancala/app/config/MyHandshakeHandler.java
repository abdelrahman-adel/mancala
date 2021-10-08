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
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class MyHandshakeHandler implements HandshakeHandler {

	private static final String GAME_ID = "gameId";
	private static final Logger log = LoggerFactory.getLogger(MyHandshakeHandler.class);

	@Bean
	public DefaultHandshakeHandler defaultHandshakeHandler() {
		return new DefaultHandshakeHandler();
	}

	@Autowired
	private DefaultHandshakeHandler defaultHandshakeHandler;

	@Override
	public boolean doHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws HandshakeFailureException {

		return validateUserGameId(request) && defaultHandshakeHandler.doHandshake(request, response, wsHandler, attributes);
	}

	private boolean validateUserGameId(ServerHttpRequest request) {
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
		return false;
	}
}