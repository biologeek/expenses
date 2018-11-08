package io.biologeek.expenses.services;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import io.biologeek.expenses.domain.beans.RegisteredUser;
import io.biologeek.expenses.domain.beans.security.AuthenticationInformation;
import io.biologeek.expenses.exceptions.AuthenticationException;
import io.biologeek.expenses.exceptions.MissingArgumentException;
import io.biologeek.expenses.exceptions.ValidationException;
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
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	

	public boolean checkToken(String token) {
		String[] strArr = decodeBase64Token(token).split(":");
		if (strArr.length == 2) {
			RegisteredUser user = this.userService.getByLogin(strArr[0]);
			
			if (encoder.matches(strArr[1], user.getAuthentication().getPassword())) {
				return true;
			} else {
				return false;
			}
		}
		
		return false;
	}

	private String decodeBase64Token(String src) {		
		return new String(Base64Utils.decodeFromString(src));
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
		
		logger.fine("Got user from database : "+user);
		if (user != null && user.getAuthentication().getLogin().equals(bean.getLogin())
				&& encoder.matches(bean.getPassword(), user.getAuthentication().getPassword())) {

			user.getAuthentication().setAuthToken(tokenGenerator.generate());
			logger.fine("Logging in successful. Token : "+user.getAuthentication().getAuthToken());
			user.getAuthentication().setTokenGenerationDate(new Date());
			user.getAuthentication().setPassword(encoder.encode(bean.getPassword()));
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
