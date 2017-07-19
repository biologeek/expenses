package io.biologeek.expenses.services;

import java.util.Base64;
import java.util.Calendar;
import java.util.Base64.Decoder;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.biologeek.expenses.domain.beans.RegisteredUser;
import io.biologeek.expenses.domain.beans.security.AuthenticationInformation;
import io.biologeek.expenses.exceptions.AuthenticationException;
import io.biologeek.expenses.exceptions.MissingArgumentException;
import io.biologeek.expenses.exceptions.ValidationException;
import io.biologeek.expenses.utils.Constants;
import io.biologeek.expenses.utils.RandomGenerator;

@Service
public class AuthenticationService {

	@Autowired
	private RegisteredUserService userService;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private RandomGenerator tokenGenerator;

	@Value("${authentication.token.ttl.type}")
	private int tokenTTLType;
	@Value("${authentication.token.ttl}")
	private int tokenTTL;
	

	public boolean checkToken(long userId, String token) {
		if (userId > 0 && token != null && !token.isEmpty()) {
			RegisteredUser userFound = userService.findUserById(userId);
			if (userFound == null)
				return false;
			if (token.equals(userFound.getAuthentication().getAuthToken())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Authenticates a user with login and password provided from login page.
	 * 
	 * @param bean
	 * @return
	 * @throws AuthenticationException
	 * @throws ValidationException 
	 */
	public RegisteredUser authenticateWithLoginParameters(AuthenticationInformation bean)
			throws AuthenticationException, MissingArgumentException, ValidationException {

		if (bean.getLogin() == null || bean.getLogin().isEmpty())
			throw new MissingArgumentException("authentication.login.empty");
		if (bean.getPassword() == null || bean.getPassword().isEmpty())
			throw new MissingArgumentException("authentication.password.empty");

		RegisteredUser user = userService.getByLogin(bean.getLogin());
		if (user.getAuthentication().getLogin().equals(bean.getLogin())
				&& encoder.matches(bean.getPassword(), user.getAuthentication().getPassword())) {
			user.getAuthentication().setAuthToken(tokenGenerator.generate());
			user.getAuthentication().setTokenGenerationDate(new Date());
			userService.updateUser(user);
			return user;
		}
		throw new AuthenticationException("connection.wrong.credentials");
	}
	


	public boolean isSessionValid(RegisteredUser user) {
		if (user.getAuthentication().getAuthToken() != null && user.getAuthentication().getTokenGenerationDate() != null) {
			Calendar date = Calendar.getInstance();
			date.setTime(user.getAuthentication().getTokenGenerationDate());
			date.add(tokenTTLType, tokenTTL);
			if (date.after(new Date())) {
				return true;
			}
		}
		return false;
	}
}
