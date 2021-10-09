package com.mancala.app.security.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.mancala.app.model.User;
import com.mancala.app.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MancalaAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserRepository customerRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		log.debug("Beginning authentication : {} with password {}", username, password);
		if (username == null || username.trim().isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException("Missing username.");
		}
		if (password == null || password.trim().isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException("Missing password.");
		}

		List<User> user = customerRepository.findByUsername(username);
		if (CollectionUtils.isEmpty(user)) {
			throw new BadCredentialsException("Bad credentials.");
		}
		if (!passwordEncoder.matches(password, user.get(0).getPassword())) {
			throw new BadCredentialsException("Bad credentials.");
		}

		log.debug("User authenticated : {}", username);
		return new UsernamePasswordAuthenticationToken(username, null, Collections.singleton((GrantedAuthority) () -> user.get(0).getRole()));

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
