package io.biologeek.expenses.services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.biologeek.expenses.domain.beans.RegisteredUser;
import io.biologeek.expenses.domain.beans.security.AuthenticationInformation;
import io.biologeek.expenses.exceptions.ValidationException;
import io.biologeek.expenses.repositories.RegisteredUserRepository;
import io.biologeek.expenses.utils.Constants;

@Service
public class RegisteredUserService {

	Logger logger = LoggerFactory.getLogger(RegisteredUser.class);
	@Autowired
	RegisteredUserRepository repo;

	@Autowired
	PasswordEncoder encoder;

	public RegisteredUser getByLogin(String login) {
		return repo.findByAuthenticationLogin(login);
	}

	public RegisteredUser updateUser(RegisteredUser updatedUser) throws ValidationException {
		RegisteredUser savedUser = repo.findOne(updatedUser.getId());

		if (savedUser != null) {
			mergeUser(updatedUser, savedUser);
		}

		repo.save(updatedUser);
		return savedUser;
	}

	private RegisteredUser mergeUser(RegisteredUser user, RegisteredUser savedUser) throws ValidationException {
		if (!savedUser.getLogin().equals(user.getLogin())) {
			throw new ValidationException("error.login.different");
		}
		if (savedUser.getAuthentication() == null) {
			throw new ValidationException("error.no_login_info");
		}
		if (!savedUser.getPassword().equals(user.getPassword())) {
			logger.info("Changed password for user {}", user.getLogin());
			savedUser.getAuthentication().setPassword(user.getPassword());
		}
		if (!savedUser.getAuthentication().getAuthToken().equals(user.getAuthentication().getAuthToken())) {
			logger.info("Update token for user {}", user.getLogin());
			savedUser.getAuthentication().setAuthToken(user.getAuthentication().getAuthToken());
		}
		savedUser.setActive(user.isActive());
		if (user.getAge() != null) {
			if (user.getAge() > 0 && user.getAge() < 200)
				savedUser.setAge(user.getAge());
			else
				throw new ValidationException("error.age.invalid");
		}
		savedUser.setEmail(validateAndReturnEmail(user.getEmail()));
		savedUser.setFirstName(user.getFirstName());
		savedUser.setLastName(user.getLastName());
		savedUser.setPhoneNumber(user.getPhoneNumber());

		return savedUser;
	}

	private String validateAndReturnEmail(String email) throws ValidationException {
		if (email != null) {
			Pattern pattern = Pattern.compile(Constants.EMAIL_PATTERN);

			Matcher matcher = pattern.matcher(email);
			if (matcher.matches())
				return email;
			throw new ValidationException("error.email.incorrect");
		}
		return null;
	}

	public RegisteredUser findUserById(Long id) {
		if (id != null && id > 0)
			return repo.findOne(id);
		return null;
	}

}
