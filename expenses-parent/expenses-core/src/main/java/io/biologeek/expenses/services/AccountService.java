package io.biologeek.expenses.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.repositories.AccountRepository;

@Service
public class AccountService {

	@Autowired
	AccountRepository accountRepository;

	public Account getAccount(long id) {
		if (id > 0)
			return accountRepository.findOne(id);
		else
			return null;
	}

}
