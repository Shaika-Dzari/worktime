/**
 * Copyright © 2014 Remi Guillemette <rguillemette@n4dev.ca>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package ca.n4dev.dev.worktime.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author rguillemette
 * @since Oct 14, 2014
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	  auth.inMemoryAuthentication().withUser("user").password("123").roles("USER");
	  auth.inMemoryAuthentication().withUser("admin").password("123").roles("ADMIN");
	  auth.inMemoryAuthentication().withUser("dba").password("123").roles("DBA");
	  
	  
	}
 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
 
	  http.authorizeRequests()
	  	.antMatchers("/tlogin").access("hasRole('ROLE_ADMIN')")
	  	.antMatchers("/").anonymous()
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/dba/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_DBA')")
		.antMatchers("/time/**").access("hasRole('ROLE_ADMIN')")
		.and().formLogin();
 
	}
}
