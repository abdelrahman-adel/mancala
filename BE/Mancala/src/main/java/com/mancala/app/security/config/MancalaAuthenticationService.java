package com.mancala.app.security.config;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.mancala.app.model.User;
import com.mancala.app.repository.UserRepository;

@Component
public class MancalaAuthenticationService {

	private static final Logger log = LoggerFactory.getLogger(MancalaAuthenticationService.class);

	@Autowired
	private UserRepository customerRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String username, final String password) throws AuthenticationException {

		log.debug("Beginning authentication : {}", username);
		if (username == null || username.trim().isEmpty()) {
			log.debug("Missing username.");
			throw new AuthenticationCredentialsNotFoundException("Missing username.");
		}
		if (password == null || password.trim().isEmpty()) {
			log.debug("Missing password.");
			throw new AuthenticationCredentialsNotFoundException("Missing password.");
		}

		List<User> user = customerRepository.findByUsername(username);
		if (CollectionUtils.isEmpty(user)) {
			log.debug("Bad credentials.");
			throw new BadCredentialsException("Bad credentials.");
		}
		if (!passwordEncoder.matches(password, user.get(0).getPassword())) {
			log.debug("Bad credentials.");
			throw new BadCredentialsException("Bad credentials.");
		}

		log.debug("User authenticated : {}", username);
		return new UsernamePasswordAuthenticationToken(username, null, Collections.singleton((GrantedAuthority) () -> user.get(0).getRole()));
	}

}
