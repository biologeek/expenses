package io.biologeek.expenses.config.security;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;
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

import io.biologeek.expenses.domain.beans.security.AuthenticationInformation;
import io.biologeek.expenses.services.AuthenticationService;
import io.biologeek.expenses.services.RegisteredUserService;

/**
 * Authentication is done through this filter by checking both username and
 * token
 *
 */
@Component
public class SimpleTokenAuthenticationFilter implements Filter {

	@Autowired
	RegisteredUserService userService;
	@Autowired
	AuthenticationService authentService;

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		if (request.getHeader("Authorization") != null) {
			authenticateWithToken(response, chain, request);
		} else if (request.getMethod().equals("POST") && request.getRequestURL().toString().endsWith("/login")) {
			// Should not filter login endpoint
			chain.doFilter(arg0, response);
		} else {
			response.getOutputStream().write("{\"key\" : \"connection.authentication.required\"}".getBytes());
			response.setContentType("application/json");
			HttpServletResponse hsr = (HttpServletResponse) response;
			hsr.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	/**
	 * Controls access to application for users that are already logged in and have
	 * a token
	 * 
	 * @param response
	 * @param chain
	 * @param request
	 * @throws IOException
	 * @throws ServletException
	 */
	private void authenticateWithToken(ServletResponse response, FilterChain chain, HttpServletRequest request)
			throws IOException, ServletException {
		String userID = request.getHeader("user");
		String token = request.getHeader("Authorization");

		if (token != null && userID != null) {
			if (authentService.checkToken(Long.valueOf(userID), token)) {
				// User is authenticated and token is valid
				chain.doFilter(request, response);
			}
		} else {
			// Else HTTP 401 error
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "error.token.invalid");
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}