package io.biologeek.expenses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biologeek.expenses.api.beans.User;
import io.biologeek.expenses.converter.UserConverter;
import io.biologeek.expenses.domain.beans.RegisteredUser;
import io.biologeek.expenses.services.RegisteredUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	private RegisteredUserService userService;

	@GetMapping(path="/{id}")
	public User getUser(@PathVariable("id") Long id){
		RegisteredUser result = null;
		if (id > 0){
			result = userService.findUserById(id);
		}
		return UserConverter.convert(result);
	}

}
