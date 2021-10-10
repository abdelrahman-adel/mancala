package com.mancala.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.mancala.app.model.GameSession;
import com.mancala.app.model.MakeMoveMessage;
import com.mancala.app.service.MancalaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
//@Validated
public class MancalaWebSocketHandler extends TextWebSocketHandler {

	@Autowired
	private MancalaService mancalaService;

	@Autowired
	private SimpMessagingTemplate template;

	@MessageMapping("/ready/{gameId}")
	public void ready(@DestinationVariable String gameId, SimpMessageHeaderAccessor accessor) {
		log.debug("User is ready");
		GameSession game = mancalaService.validateUserWithGame(accessor.getUser().getName(), gameId);
		template.convertAndSend("/game/" + gameId, game);
	}

	@MessageMapping("/make-move/{gameId}")
	public void makeMove(@DestinationVariable String gameId, @Payload MakeMoveMessage makeMoveMessage, SimpMessageHeaderAccessor accessor) {
		log.debug("User makes a move");
		String username = accessor.getUser().getName();
		GameSession game = mancalaService.validateUserWithGame(username, gameId);
		if (makeMoveMessage != null) {
			game = mancalaService.makeMove(game, username, makeMoveMessage.getPit());
		}

		template.convertAndSend("/game/" + gameId, game);
	}

}
