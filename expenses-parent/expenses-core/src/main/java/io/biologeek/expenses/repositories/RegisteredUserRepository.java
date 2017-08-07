package io.biologeek.expenses.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.biologeek.expenses.domain.beans.RegisteredUser;

@Repository
public interface RegisteredUserRepository extends CrudRepository<RegisteredUser, Long>{
	
	public RegisteredUser findByAuthenticationLogin(String login);

}
