package io.biologeek.expenses.config.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.biologeek.expenses.domain.beans.RegisteredUser;
import io.biologeek.expenses.services.RegisteredUserService;


public class CustomAuthenticationProvider {

	private static final String AUTH_HEADER_NAME = null;
	RegisteredUserService userService;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (String) authentication.getName();
		String password = (String) authentication.getCredentials();
		RegisteredUser user = userService.getByLogin(username);

		final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		final HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
		final String token = request.getHeader(AUTH_HEADER_NAME);

		if (user != null && user.getPassword().equals(password)) {
			return new UsernamePasswordAuthenticationToken(username, password);
		}
		return null;

	}

	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
