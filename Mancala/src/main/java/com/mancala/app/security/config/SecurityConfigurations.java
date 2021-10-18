package com.mancala.app.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

@SuppressWarnings("deprecation") // NoOpPasswordEncoder is for development only
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	// @formatter:off
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
			.authorizeRequests()
				.anyRequest().authenticated().and()
			.csrf().disable()
			.cors().configurationSource(request -> {
				CorsConfiguration corsConfig = new CorsConfiguration();
				corsConfig.addAllowedOrigin("http://localhost:8080");
				corsConfig.addAllowedMethod("*");
				corsConfig.addAllowedHeader("*");
				corsConfig.setAllowCredentials(true);
				return corsConfig;
			}).and()
			.formLogin().and()
			.httpBasic().and()
			.headers().frameOptions().disable();
		
		// This is not for websocket authorization, and this should most likely not be altered.
//        http
//			.httpBasic().disable()
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//			.authorizeRequests()
//				.antMatchers("/stomp").permitAll()
//				.anyRequest().denyAll();
	}
	// @formatter:on

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

}
