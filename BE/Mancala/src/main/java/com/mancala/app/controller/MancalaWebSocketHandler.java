package com.mancala.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.mancala.app.model.GameBoard;
import com.mancala.app.model.PlayMessage;
import com.mancala.app.service.MancalaService;

@Controller
//@Validated
public class MancalaWebSocketHandler extends TextWebSocketHandler {

	private static final Logger log = LoggerFactory.getLogger(MancalaWebSocketHandler.class);

	@Autowired
	private MancalaService mancalaService;

	@Autowired
	private SimpMessagingTemplate template;

	@SendToUser
	public String tttt() {
		return "";
	}

	@MessageMapping("/{gameId}")
	public void play(@DestinationVariable String gameId, @Payload PlayMessage playMsg, SimpMessageHeaderAccessor headerAccessor) {
		log.info("here we are!");
		log.info("rq: {}", playMsg.toString());
		headerAccessor.getSessionAttributes().put("username", playMsg.getSender());
		headerAccessor.getSessionAttributes().forEach((k, v) -> {
			log.info("k: {} || v: {}", k, v);
		});
		GameBoard gameBoard = null;
		if (playMsg != null) {
			// TODO gameBoard = mancalaService.play(roomId, playMsg.getUser(),
			// playMsg.getPit());
			gameBoard = new GameBoard();
			gameBoard.setId(1);
			gameBoard.getPits()[0] = "5";
		}

		template.convertAndSend("/game/" + gameId, gameBoard);
	}

}
