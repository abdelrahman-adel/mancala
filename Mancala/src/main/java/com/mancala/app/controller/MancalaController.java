package com.mancala.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mancala.app.model.InitiateRs;
import com.mancala.app.service.GameSessionService;

@RestController
public class MancalaController {

	@Autowired
	private GameSessionService mancalaService;

	@PostMapping("/mancala/initiate")
	public InitiateRs initiate() {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		return mancalaService.initiate(user);
	}

}
