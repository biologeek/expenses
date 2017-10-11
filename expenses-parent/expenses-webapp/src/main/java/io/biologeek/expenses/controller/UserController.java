package io.biologeek.expenses.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biologeek.expenses.api.beans.AuthenticationActionBean;
import io.biologeek.expenses.api.beans.User;
import io.biologeek.expenses.converter.AccountToApiConverter;
import io.biologeek.expenses.converter.UserConverter;
import io.biologeek.expenses.domain.beans.RegisteredUser;
import io.biologeek.expenses.exceptions.AuthenticationException;
import io.biologeek.expenses.exceptions.MissingArgumentException;
import io.biologeek.expenses.exceptions.ValidationException;
import io.biologeek.expenses.services.AccountService;
import io.biologeek.expenses.services.AuthenticationService;
import io.biologeek.expenses.services.RegisteredUserService;

@RestController
@RequestMapping("/user")
public class UserController extends ExceptionWrappedRestController {

	@Autowired
	private RegisteredUserService userService;
	@Autowired
	private AuthenticationService authentService;
	@Autowired
	private AccountService accountService;
	private Logger logger = Logger.getLogger(UserController.class.getName());

	@GetMapping(path = "/{id}")
	public User getUser(@PathVariable("id") Long id) {
		RegisteredUser result = null;
		if (id > 0) {
			result = userService.findUserById(id);
		}
		return UserConverter.convert(result);
	}

	@PostMapping(path = "/login")
	public ResponseEntity<? extends Object> loginUser(@RequestBody AuthenticationActionBean bean, HttpServletResponse resp) {
		RegisteredUser user = null;
		
		logger.info("Logging in user " + bean.getLogin());
		try {
			if ((user = authentService.authenticateWithLoginParameters(UserConverter.toModel(bean))) != null) {
				resp.addCookie(new Cookie("token", user.getAuthentication().getAuthToken()));
				resp.addCookie(new Cookie("user", String.valueOf(user.getId())));
				ResponseEntity<User> response = new ResponseEntity<>(UserConverter.convert(user), HttpStatus.OK);
				
				return response;
			}
		} catch (AuthenticationException e) {
			/*
			 * Here when the authentication fails, we'll put a 403 error code, different
			 * from 401 error code that will be used when a user tries to access a resource
			 * that he's not allowed to.
			 */
			return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON).body(wrapException(e));
		} catch (MissingArgumentException | ValidationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(wrapException(e));
		}
		return null;

	}
	
	
	@GetMapping(path = "/{user}/accounts")
	public ResponseEntity<? extends Object> listAccounts(@PathVariable("user") Long userId){
		List<io.biologeek.expenses.domain.beans.Account> accounts = null;
		if(userId != null && userId > 0) {
			RegisteredUser user = userService.findUserById(userId);
			accounts = accountService.getAccountsForUser(user);
		}		
		return ResponseEntity.ok(AccountToApiConverter.convert(accounts));
	}

	public RegisteredUserService getUserService() {
		return userService;
	}

	public void setUserService(RegisteredUserService userService) {
		this.userService = userService;
	}

	public AuthenticationService getAuthentService() {
		return authentService;
	}

	public void setAuthentService(AuthenticationService authentService) {
		this.authentService = authentService;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

}
