package io.biologeek.expenses.services;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.beans.operations.Expense;
import io.biologeek.expenses.repositories.ExpensesRepository;

@Service
public class ExpensesService {
	ExpensesRepository expensesRepository;

	public List<Expense> getLastExpensesForAccount(Account account, int limit) {
		return expensesRepository.getExpensesForAccountWithLimit(account.getAccountId(), limit,
				new PageRequest(0, limit));
	}

}
