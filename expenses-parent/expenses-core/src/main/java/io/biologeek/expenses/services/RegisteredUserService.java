package io.biologeek.expenses.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.biologeek.expenses.domain.beans.RegisteredUser;
import io.biologeek.expenses.repositories.RegisteredUserRepository;

@Service
public class RegisteredUserService {

	
	@Autowired
	RegisteredUserRepository repo;
	
	
	public RegisteredUser getByLogin(String login) {
		return repo.findByLogin(login);
	}
}
