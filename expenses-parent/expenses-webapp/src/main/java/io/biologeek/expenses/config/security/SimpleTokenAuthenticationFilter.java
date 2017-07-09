package io.biologeek.expenses.config.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.biologeek.expenses.domain.beans.RegisteredUser;
import io.biologeek.expenses.services.AuthenticationService;
import io.biologeek.expenses.services.RegisteredUserService;

/**
 * Authentication is done through this filter by checking both username and
 * token
 *
 */
@Component
public class SimpleTokenAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	RegisteredUserService userService;
	@Autowired
	AuthenticationService authentService;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;

		String token = req.getHeader("Authorization");
		String userID = req.getHeader("user");

		if (token != null) {
			RegisteredUser userFound = userService.findUserById(Long.valueOf(userID));
			if (userFound != null && token.equals(userFound.getAuthToken())) {
				chain.doFilter(request, response);
			}
		} else {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "error.token.wrong");
		}
	}

	protected boolean shouldNotFilter(HttpServletRequest request) {
		if (request.getServletPath().contains("/login")//
				|| request.getServletPath().contains("/logout"))
			return true;
		return false;
	}

}