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
import com.mancala.app.model.GameStatus;
import com.mancala.app.model.MakeMoveRq;
import com.mancala.app.model.MakeMoveRs;
import com.mancala.app.service.GameSessionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MancalaWebSocketHandler extends TextWebSocketHandler {

	@Autowired
	private GameSessionService gameSessionService;

	@Autowired
	private SimpMessagingTemplate template;

	@MessageMapping("/ready/{gameId}")
	public void ready(@DestinationVariable String gameId, SimpMessageHeaderAccessor accessor) {
		log.debug("User is ready");
		GameSession game = gameSessionService.validateUserWithGame(accessor.getUser().getName(), gameId);
		template.convertAndSend("/game/" + gameId, game);
	}

	@MessageMapping("/make-move/{gameId}")
	public void makeMove(@DestinationVariable String gameId, @Payload MakeMoveRq makeMoveRq, SimpMessageHeaderAccessor accessor) {
		log.debug("User is making a move");
		String username = accessor.getUser().getName();
		GameSession game = gameSessionService.validateUserWithGame(username, gameId);
		if (makeMoveRq == null || makeMoveRq.getPit() == null) {
			return;
		}

		game = gameSessionService.makeMove(game, username, makeMoveRq.getPit());

		MakeMoveRs makeMoveRs = new MakeMoveRs();
		makeMoveRs.setGameSession(game);
		if (GameStatus.FINISHED == game.getStatus()) {
			makeMoveRs.setMessage("Winner winner chicke dinner: " + game.getWinner());
		}
		template.convertAndSend("/game/" + gameId, makeMoveRs);
	}

}
