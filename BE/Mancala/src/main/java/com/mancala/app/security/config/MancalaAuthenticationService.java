package com.mancala.app.security.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.mancala.app.repository.UserRepository;

@Component
public class MancalaAuthenticationService {

	@Autowired
	private UserRepository customerRepository;

	public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String username, final String password) throws AuthenticationException {

		if (username == null || username.trim().isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException("Missing username.");
		}
		if (password == null || password.trim().isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException("Missing password.");
		}
		if (customerRepository.findByUsernameAndPassword(username, password) == null) {
			throw new BadCredentialsException("Bad credentials");
		}

		return new UsernamePasswordAuthenticationToken(username, null, Collections.singleton((GrantedAuthority) () -> "USER"));
	}

}
