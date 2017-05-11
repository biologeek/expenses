package io.biologeek.expenses.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.beans.operations.Expense;
import io.biologeek.expenses.exceptions.BusinessException;
import io.biologeek.expenses.exceptions.TechnicalException;
import io.biologeek.expenses.repositories.ExpensesRepository;
import io.biologeek.expenses.validation.ExpenseValidator;

@Service
@Transactional
public class ExpensesService {
	@Autowired
	ExpensesRepository expensesRepository;

	public List<Expense> getLastExpensesForAccount(Account account, int limit) {
		return expensesRepository.getExpensesForAccountWithLimit(account.getId(), new PageRequest(0, limit));
	}

	/**
	 * Check and save an expense
	 * 
	 * @param account
	 *            the account attached to the expense
	 * @param expense
	 * @return
	 * @throws TechnicalException
	 * @throws BusinessException
	 */
	public Expense addExpenseToAccount(Account account, Expense expense) throws TechnicalException, BusinessException {
		Expense result = null;
		if (expense.getAccount() == null && account != null) {
			expense.setAccount(account);
		}
		if (expense.getId() != null) {
			throw new BusinessException("expense.id_not_null");
		}

		ExpenseValidator.validateExpense(expense);

		try {
			result = expensesRepository.save(expense);
		} catch (RuntimeException e) {
			throw new TechnicalException("technical.save_error");
		}
		return result;
	}

	public Expense editExpenseForAccount(Account account, Expense expense)
			throws TechnicalException, BusinessException {
		if (expense.getId() == null)
			return addExpenseToAccount(account, expense);

		Expense persistedExpense = this.getExpenseByid(expense.getId());
		mergeExpenses(expense, persistedExpense);
		ExpenseValidator.validateExpense(expense);

		return expense;

	}

	/**
	 * Merges a modified expense with the one stored in database
	 * 
	 * @param expense
	 * @param persistedExpense
	 * @throws BusinessException
	 */
	private void mergeExpenses(Expense expense, Expense persistedExpense) throws BusinessException {
		if (!expense.getId().equals(persistedExpense.getId()))
			throw new BusinessException("merge.different.id");

		if (!expense.getAccount().equals(persistedExpense.getAccount()))
			expense.setAccount(persistedExpense.getAccount());

		if (expense.getAmount() != persistedExpense.getAmount())
			expense.setAmount(persistedExpense.getAmount());

		if (!expense.getBeneficiary().equals(persistedExpense.getBeneficiary()))
			expense.setBeneficiary(persistedExpense.getBeneficiary());

		if (!expense.getEmitter().equals(persistedExpense.getEmitter()))
			expense.setEmitter(persistedExpense.getEmitter());

		if (!expense.getCurrency().equals(persistedExpense.getCurrency()))
			expense.setCurrency(persistedExpense.getCurrency());

		if (!expense.getCategory().equals(persistedExpense.getCategory()))
			expense.setCategory(persistedExpense.getCategory());
	}

	/**
	 * Returns an expense based on its ID
	 * 
	 * @param id
	 * @return
	 */
	public Expense getExpenseByid(Long id) {
		return expensesRepository.getOne(id);
	}
}
