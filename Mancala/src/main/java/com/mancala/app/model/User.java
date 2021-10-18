package com.mancala.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("user")
public class User {

	@Id
	private Long id;
	private String username;
	private String password;
	private String role;
}
