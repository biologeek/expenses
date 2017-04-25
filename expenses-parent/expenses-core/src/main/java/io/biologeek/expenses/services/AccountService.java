package io.biologeek.expenses.services;

import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.repositories.AccountRepository;

public class AccountService {

	
	AccountRepository accountRepository;
	public Account getAccount(long id) {
		return accountRepository.findOne(id);
	}

}
