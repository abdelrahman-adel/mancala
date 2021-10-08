package com.mancala.app.security.config;

//@Configuration
public class SecurityConfigurations {// extends WebSecurityConfigurerAdapter {

	// @formatter:off
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		http.authorizeRequests()
//				.mvcMatchers("/h2-console/**").permitAll()
//				.mvcMatchers("/mancala/**").authenticated()
//				.anyRequest().authenticated().and()
//		.csrf().disable()
//		.cors().configurationSource(new CorsConfigurationSource() {
//			@Override
//			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//				CorsConfiguration corsConfig = new CorsConfiguration();
//				corsConfig.addAllowedOrigin("http://localhost:8080");
//				corsConfig.addAllowedMethod("*");
//				corsConfig.addAllowedHeader("*");
//				corsConfig.setAllowCredentials(true);
//				return corsConfig;
//			}
//		}).and().formLogin().and().httpBasic().and().headers().frameOptions().disable();
//	}
//	// @formatter:on
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}

}
