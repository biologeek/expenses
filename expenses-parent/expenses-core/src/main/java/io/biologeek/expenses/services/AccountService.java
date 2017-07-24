package io.biologeek.expenses.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.beans.RegisteredUser;
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

	public List<Account> getAccounts() {
		return accountRepository.findAll();
	}


	public List<Account> getAccountsForUser(RegisteredUser user) {
		return accountRepository.findByOwner(user);
	}

}
