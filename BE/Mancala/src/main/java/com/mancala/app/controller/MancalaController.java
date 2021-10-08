package com.mancala.app.controller;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mancala.app.model.GameSession;
import com.mancala.app.service.MancalaService;

@RestController
@Validated
public class MancalaController {

	@Autowired
	private MancalaService mancalaService;

	@PostMapping("/mancala/initiate")
	public String initiate(@RequestParam("user") @NotBlank String user) {
//		return mancalaService.initiate(user);
		return "game12345";
	}

}
