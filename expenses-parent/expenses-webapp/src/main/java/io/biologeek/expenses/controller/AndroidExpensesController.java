package io.biologeek.expenses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.biologeek.expenses.api.beans.Expense;
import io.biologeek.expenses.converter.ExpenseToApiConverter;
import io.biologeek.expenses.converter.ExpenseToModelConverter;
import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.exceptions.BusinessException;
import io.biologeek.expenses.exceptions.TechnicalException;
import io.biologeek.expenses.services.AccountService;
import io.biologeek.expenses.services.ExpensesService;

@Controller
@RequestMapping("/mobile")
public class AndroidExpensesController {

	@Autowired
	ExpensesService expensesService;
	@Autowired
	AccountService accountService;

	@RequestMapping(path = { "/account/{account}/expense" }, method = { RequestMethod.GET })
	public ResponseEntity<List<Expense>> getLastExpenses(@PathVariable("account") long accountId,
			@RequestParam("limit") int limit) {
		List<io.biologeek.expenses.domain.beans.operations.Expense> result = null;
		Account account = accountService.getAccount(accountId);

		if (account == null) {
			return new ResponseEntity<List<Expense>>(HttpStatus.NOT_FOUND);
		}
		result = expensesService.getLastExpensesForAccount(account, limit);
		if (result.isEmpty())
			return new ResponseEntity<List<Expense>>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(ExpenseToApiConverter.convert(result), HttpStatus.OK);
	}

	@RequestMapping(path = { "/account/{account}/expense" }, method = { RequestMethod.POST })
	public ResponseEntity<Expense> addExpense(@PathVariable("account") long accountId, @RequestBody Expense expense) {
		io.biologeek.expenses.domain.beans.operations.Expense result = null;
		Account account = accountService.getAccount(accountId);

		if (account == null) {
			return new ResponseEntity<Expense>(HttpStatus.NOT_FOUND);
		}

		try {
			result = expensesService.addExpenseToAccount(account, ExpenseToModelConverter.convert(expense));
		} catch (TechnicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(ExpenseToApiConverter.convert(result), HttpStatus.CREATED);
	}

	@RequestMapping(path = { "/account/{account}/expense/{id}" }, method = { RequestMethod.PUT })
	public ResponseEntity<Expense> addExpense(@PathVariable("account") long accountId,
			@PathVariable("id") long expenseId, @RequestBody Expense expense) {
		io.biologeek.expenses.domain.beans.operations.Expense result = null;
		Account account = accountService.getAccount(accountId);

		if (account == null) {
			return new ResponseEntity<Expense>(HttpStatus.NOT_FOUND);
		}

		try {
			result = expensesService.editExpenseForAccount(account, ExpenseToModelConverter.convert(expense));
		} catch (TechnicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(ExpenseToApiConverter.convert(result), HttpStatus.OK);
	}

}
