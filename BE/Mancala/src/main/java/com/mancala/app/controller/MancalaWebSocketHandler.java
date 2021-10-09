package com.mancala.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.mancala.app.model.GameSession;
import com.mancala.app.model.PlayMessage;
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

	@MessageMapping("/{gameId}")
	public void play(@DestinationVariable String gameId, @Payload PlayMessage playMsg, SimpMessageHeaderAccessor headerAccessor) {
		log.info("here we are!");
		log.info("rq: {}", playMsg.toString());
		headerAccessor.getSessionAttributes().put("username", playMsg.getSender());
		headerAccessor.getSessionAttributes().forEach((k, v) -> {
			log.info("k: {} || v: {}", k, v);
		});
		GameSession game = null;
		if (playMsg != null) {
			// TODO gameBoard = mancalaService.play(roomId, playMsg.getUser(),
			// playMsg.getPit());
		}

		template.convertAndSend("/game/" + gameId, game);
	}

}
