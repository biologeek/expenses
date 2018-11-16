package io.biologeek.expenses.config.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
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
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;

		HttpServletResponse response = (HttpServletResponse) arg1;
		List<Cookie> cookies = request.getCookies() == null ? new ArrayList<>() : Arrays.asList(request.getCookies());

		boolean hasAuthorization = cookies.stream().anyMatch(new Predicate<Cookie>() {
			@Override
			public boolean test(Cookie t) {
				return t.getName().equals("token");
			}
		});
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.addHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,x-access-token,Content-Type,Authorization,Accept");
		response.addHeader("Access-Control-Expose-Headers", "Origin,X-Requested-With,x-access-token,Content-Type,Authorization,Accept");
		response.addHeader("Access-Control-Max-Age", "3600");
		if ("OPTIONS".equals(request.getMethod())) {
			chain.doFilter(arg0, response);
		} else if (request.getMethod().equals("POST") && (request.getRequestURL().toString().endsWith("/user/register")
				|| request.getRequestURL().toString().endsWith("/user/login"))) {
			// Should not filter registration endpoint
			chain.doFilter(arg0, response);
		} else {
			if (request.getHeader("Authorization") != null || hasAuthorization) {
				authenticateWithToken(response, chain, request);
			} else {
				response.getOutputStream().write("{\"key\" : \"connection.authentication.required\"}".getBytes());
				response.setContentType("application/json");
				HttpServletResponse hsr = (HttpServletResponse) response;
				hsr.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
	}

	/**
	 * Controls access to application for users that are already logged in and have
	 * a token.
	 * 
	 * Searches among headers then cookies
	 * 
	 * @param response
	 * @param chain
	 * @param request
	 * @throws IOException
	 * @throws ServletException
	 */
	private void authenticateWithToken(ServletResponse response, FilterChain chain, HttpServletRequest request)
			throws IOException, ServletException {

		String token = request.getHeader("Authorization");
		if (token != null) {
			try {
				String cleanToken = token.split(" ")[1];
				if (authentService.checkToken(cleanToken)) {
					// User is authenticated and token is valid
					chain.doFilter(request, response);
				} else {
					// Else HTTP 401 error
					((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
							"error.token.invalid");
				}
			} catch (Exception e) {
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "error.token.invalid");
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