package io.biologeek.expenses.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import io.biologeek.expenses.domain.beans.RegisteredUser;
import io.biologeek.expenses.services.RegisteredUserService;


public class CustomUserDetailsService {

	@Autowired
	RegisteredUserService service;

	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		RegisteredUser user = null;
		if (!username.isEmpty()) {
			user = service.getByLogin(username);
			if (user != null) {
				return user;
			}
		}
		return null;
	}
}
