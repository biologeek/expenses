package io.biologeek.expenses.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.beans.Balance;
import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.domain.beans.balances.DailyBalance;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.domain.beans.operations.OperationType;
import io.biologeek.expenses.repositories.OperationsRepository;

@Service
@Transactional
public class OperationService {

	@Autowired
	OperationsRepository operationsRepository;
	@Autowired
	CurrencyDelegate currrencyDelegate;

	public List<Operation> getLastOperationsForAccount(Account account, int limit) {
		return operationsRepository.getOperationsForAccountWithLimit(account.getId(), new PageRequest(0, limit));
	}

	/**
	 * Returns an operation based on its ID
	 * 
	 * @param id
	 * @return
	 */
	public Operation getOperationByid(Long id) {
		return operationsRepository.getOne(id);
	}

	public List<? extends Balance> getOperationsForPeriod(long account, Date begin, Date end,
			List<OperationType> collect) {

		List<Operation> operations = operationsRepository.getGroupedByDayOperationsForAccountByPeriod(account, begin,
				end);
		if (operations != null && operations.isEmpty())
			return Collections.emptyList();

		return buildDailyBalanceWithCategoryDetail(operations);
	}

	/**
	 * Returns a list of daily balances 
	 * @param operations
	 * @return
	 */
	private List<? extends Balance> buildDailyBalanceWithCategoryDetail(List<Operation> operations) {
		List<DailyBalance> result = new ArrayList<>();

		DailyBalance balanceOfTheDay = new DailyBalance();

		for (Operation op : operations) {
			if (balanceOfTheDay.equals(new DailyBalance())) {
				balanceOfTheDay = addOperationToNewBalance(op);
			} else {
				balanceOfTheDay = updateBalanceWithOperation(op, balanceOfTheDay);
			}

		}
		return null;
	}

	private DailyBalance addOperationToNewBalance(Operation op) {
		DailyBalance res = new DailyBalance();
		res.setBalanceDate(op.getEffectiveDate());
		res.setBalanceValue(new BigDecimal(op.getAmount()));
		res.setBalanceCurrency(op.getCurrency());
		Map<Category, BigDecimal> categories = new HashMap<>();
		categories.put(op.getCategory(), new BigDecimal(op.getAmount()));

		res.setCategories(categories);

		return res;
	}

	/**
	 * Builds balance per category by adding operations to balance. <br>
	 * <br>
	 * If the category to which belongs the operation exists in the balance,
	 * method will update balance of the category. Else it will create it
	 * 
	 * @param op
	 * @param balance
	 * @return
	 */
	private DailyBalance updateBalanceWithOperation(Operation op, DailyBalance balance) {
		if (!op.getEffectiveDate().equals(balance.getBalanceDate())) {
			balance = new DailyBalance();
		}

		Double convertedAmount = op.getAmount();
		if (!balance.getBalanceCurrency().equals(op.getCurrency())) {
			convertedAmount = convertToBalanceCurrency(op.getAmount(), op.getCurrency(), balance.getBalanceCurrency());
		}

		balance.getBalanceValue().add(new BigDecimal(convertedAmount));
		Entry<Category, BigDecimal> category = balance.findCategory(op);
		// If category is found, add amount to yet calculated amount.
		// Else, put new category
		if (category == null) {
			balance.getCategories().put(op.getCategory(), new BigDecimal(op.getAmount()));
		}
		category.getValue().add(new BigDecimal(op.getAmount()));

		return balance;
	}

	/**
	 * Converts an operation or whatever amount to the good currency
	 * 
	 * @param amount
	 * @param currency
	 * @param balanceCurrency
	 * @return
	 */
	private Double convertToBalanceCurrency(Double amount, Currency currency, Currency balanceCurrency) {
		currrencyDelegate.convert(amount, currency, balanceCurrency);
		return amount;
	}
}
