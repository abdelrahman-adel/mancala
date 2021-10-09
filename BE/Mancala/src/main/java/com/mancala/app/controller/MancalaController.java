package com.mancala.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mancala.app.service.MancalaService;

@RestController
public class MancalaController {

	@Autowired
	private MancalaService mancalaService;

	@PostMapping("/mancala/initiate")
	public String initiate() {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		return mancalaService.initiate(user);
	}

}
