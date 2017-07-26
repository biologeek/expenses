package io.biologeek.expenses.services;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import io.biologeek.expenses.beans.TimeUnit;
import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.domain.beans.balances.DailyBalances.DailyBalance;
import io.biologeek.expenses.domain.beans.balances.CategoryBalance;
import io.biologeek.expenses.domain.beans.balances.DailyBalances;
import io.biologeek.expenses.domain.beans.balances.FullPeriodicBalance;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.domain.beans.operations.OperationType;
import io.biologeek.expenses.domain.beans.operations.Regular;
import io.biologeek.expenses.exceptions.BusinessException;
import io.biologeek.expenses.exceptions.TechnicalException;
import io.biologeek.expenses.repositories.OperationsRepository;
import io.biologeek.expenses.utils.DateUtils;
import io.biologeek.expenses.validation.OperationValidator;

@Service
@Transactional
public class OperationService {

	@Autowired
	OperationsRepository operationsRepository;
	@Autowired
	CurrencyDelegate currrencyDelegate;

	/**
	 * Validates operations and stores in datasource
	 * 
	 * @param operation
	 * @return
	 * @throws BusinessException
	 */
	public Operation addOperationToAccount(Operation operation) throws BusinessException {
		if (operation == null)
			return null;
		if (operation.getId() != null)
			return this.updateOperation(operation);

		OperationValidator.validateOperation(operation);

		return operationsRepository.save(operation);

	}

	public Operation updateOperation(Operation operation) throws BusinessException {
		OperationValidator.validateOperation(operation);

		if (operation.getId() != null) {
			Operation storedOperation = operationsRepository.getOne(operation.getId());

			if (storedOperation == null) {
				new OperationMerger().merge(operation, storedOperation);
			}
		}
		return null;
	}

	public List<Operation> getLastOperationsForAccount(Account account, int page, Integer limit, String orderBy,
			boolean reverseOrder) {

		String orderByField = "creationDate";

		if ("date".equals(orderBy))
			orderByField = "creationDate";

		return operationsRepository.getOperationsForAccountWithLimit(account.getId(), new PageRequest(page, limit),
				orderByField+" "+(reverseOrder ? "desc" : "asc"));
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

	/**
	 * Method to call to retrieve eligible operations against criteria passed in
	 * parameters
	 * 
	 * @param account
	 * @param unitConstant
	 * @param begin
	 * @param end
	 * @param collect
	 * @param withCategories
	 * @return
	 */
	public DailyBalances getDailyBalancesForPeriod(long account, String unitConstant, Date begin, Date end,
			List<OperationType> collect) {

		List<Operation> operations = searchEligibleOperations(account, begin, end, collect);

		return buildDailyBalances(operations);
	}

	/**
	 * Method to call to retrieve eligible operations against criteria passed in
	 * parameters
	 * 
	 * @param account
	 * @param unitConstant
	 * @param begin
	 * @param end
	 * @param collect
	 * @param withCategories
	 * @return
	 */
	public CategoryBalance getCategoryBalancesForPeriod(long account, String unitConstant, Date begin, Date end,
			List<OperationType> collect) {

		List<Operation> operations = searchEligibleOperations(account, begin, end, collect);

		return buildCategoryBalanceForPeriodAndTypes(operations, null);
	}

	/**
	 * Returns a full balance
	 * 
	 * @param account
	 * @param unitConstant
	 * @param begin
	 * @param end
	 * @param collect
	 * @param withCategories
	 * @return
	 */
	public FullPeriodicBalance getFullBalanceForPeriod(long account, String unitConstant, Date begin, Date end,
			List<OperationType> collect, boolean withCategories) {

		List<Operation> operations = searchEligibleOperations(account, begin, end, collect);

		if (operations != null && operations.isEmpty())
			return new FullPeriodicBalance();

		if (withCategories)
			return buildFullBalanceWithCategoryDetail(operations);
		else
			return new FullPeriodicBalance()//
					.dailyBalances(this.buildDailyBalances(operations));

	}

	/**
	 * Returns raw list of results from datasource
	 * 
	 * @param account
	 * @param begin
	 * @param end
	 * @param collect
	 * @return
	 */
	private List<Operation> searchEligibleOperations(long account, Date begin, Date end, List<OperationType> collect) {
		List<Operation> operations = operationsRepository.getGroupedByDayAndPeriodAndTypeOfOperationsForAccount(account,
				begin, end, (OperationType[]) collect.toArray());
		return operations;
	}

	public FullPeriodicBalance getFullBalanceForPeriod(long account, String unitConstant, Date begin, Date end,
			List<OperationType> collect) {
		return getFullBalanceForPeriod(account, unitConstant, begin, end, collect, true);
	}

	/**
	 * Builds a balanced with a repartition by category. You can also filter by type
	 * 
	 * @param operations
	 * @param types
	 * @return
	 */
	CategoryBalance buildCategoryBalanceForPeriodAndTypes(List<Operation> operations, OperationType... types) {
		CategoryBalance result = new CategoryBalance();

		for (Operation op : operations) {
			if (result.getCategories().containsKey(op.getCategory())
					&& (Arrays.asList(types).contains(op.getOperationType()) || types == null)) {
				BigDecimal entry = result.getCategories().get(op.getCategory());
				entry.add(new BigDecimal(op.getAmount()));
			}
		}

		return result;
	}

	/**
	 * Builds a balance during a period
	 * 
	 * @param operations
	 * @return
	 */
	FullPeriodicBalance buildFullBalanceWithCategoryDetail(List<Operation> operations) {
		FullPeriodicBalance fullBalance = new FullPeriodicBalance();

		DailyBalance balanceOfTheDay = new DailyBalance();

		for (Operation op : operations) {
			if (isOperationDayAlreadyBalanced(op, fullBalance)) {
				balanceOfTheDay = getBalanceOfTheDay(op, fullBalance);
				if (op instanceof Regular
						&& ((Regular) op).getInterval().isAnOperationOfTheDay(balanceOfTheDay.getBalanceDate())) {
					//
					op = ((Regular) op).getInstantOperation(balanceOfTheDay.getBalanceDate());
				}
				balanceOfTheDay = updateBalanceWithOperation(op, balanceOfTheDay);
				// Adds amount to category balance
				updateCategoryBalanceWithOperation(op, balanceOfTheDay);
			} else {
				balanceOfTheDay = addOperationToNewBalance(op);
				// Adds amount to category balance
				updateCategoryBalanceWithOperation(op, balanceOfTheDay);
				fullBalance.getDailyBalances().add(balanceOfTheDay);
			}

		}
		return fullBalance;
	}

	/**
	 * Builds a balance during a period
	 * 
	 * @param operations
	 * @return
	 */
	public DailyBalances buildDailyBalances(List<Operation> operations) {
		DailyBalances fullBalance = new DailyBalances();

		for (Operation op : operations) {
			DailyBalance balanceOfTheDay = new DailyBalance();

			if (isOperationDayAlreadyBalanced(op, fullBalance)) {
				if (op instanceof Regular
						&& ((Regular) op).getInterval().isAnOperationOfTheDay(balanceOfTheDay.getBalanceDate())) {
					//
					op = ((Regular) op).getInstantOperation(balanceOfTheDay.getBalanceDate());

				}
				balanceOfTheDay = updateBalanceWithOperation(op, balanceOfTheDay);
			} else {
				balanceOfTheDay = addOperationToNewBalance(op);
			}
			fullBalance.add(balanceOfTheDay);
		}
		return fullBalance;
	}

	/**
	 * Checks if the day of the operation has already a non-null balance
	 * 
	 * @param op
	 * @param result
	 * @return
	 */
	private boolean isOperationDayAlreadyBalanced(Operation op, DailyBalances fullBalance) {
		if (op == null || fullBalance == null || fullBalance.size() == 0)
			return false;

		final Date dateOfOperation = op.getEffectiveDate();
		return fullBalance.stream()//
				.anyMatch(new Predicate<DailyBalance>() {
					public boolean test(DailyBalance t) {
						return DateUtils.areSameDate(dateOfOperation, t.getBalanceDate());
					}
				});
	}

	private boolean isOperationDayAlreadyBalanced(Operation op, FullPeriodicBalance fullBalance) {
		return isOperationDayAlreadyBalanced(op, fullBalance.getDailyBalances());
	}

	private DailyBalance getBalanceOfTheDay(Operation op, FullPeriodicBalance fullBalance) {
		if (op == null || fullBalance.getDailyBalances() == null || fullBalance.getDailyBalances().size() == 0)
			return null;

		final Date dateOfOperation = op.getEffectiveDate();
		return fullBalance//
				.getDailyBalances()//
				.stream()//
				.filter(new Predicate<DailyBalance>() {
					public boolean test(DailyBalance t) {
						return DateUtils.areSameDate(dateOfOperation, t.getBalanceDate());
					}
				}).findFirst().get();
	}

	/**
	 * Creates a new {@link DailyBalance} and adds operation to it
	 * 
	 * @param op
	 *            the operation to add
	 * @return a {@link DailyBalance} for the day of the operation
	 */
	DailyBalance addOperationToNewBalance(Operation op) {
		DailyBalance res = new DailyBalance();
		res.setBalanceDate(op.getEffectiveDate());
		res.setBalanceValue(new BigDecimal(op.getAmount()));
		res.setBalanceCurrency(op.getCurrency());

		return res;
	}

	/**
	 * Builds balance per category by adding operations to balance. <br>
	 * <br>
	 * If the category to which belongs the operation exists in the balance, method
	 * will update balance of the category. Else it will create it
	 * 
	 * @param op
	 * @param balance
	 * @return
	 */
	DailyBalance updateBalanceWithOperation(Operation op, DailyBalance balance) {
		if (!op.getEffectiveDate().equals(balance.getBalanceDate())) {
			balance = new DailyBalance();
		}

		Double convertedAmount = op.getAmount();
		if (!balance.getBalanceCurrency().equals(op.getCurrency())) {
			convertedAmount = convertToBalanceCurrency(op.getAmount(), op.getCurrency(), balance.getBalanceCurrency());
		}

		BigDecimal currentValue = balance.getBalanceValue();
		balance.setBalanceValue(currentValue.add(new BigDecimal(convertedAmount)));

		return balance;
	}

	/**
	 * Manages category balance update and adds operation amount to category if it
	 * exists or creates a new category in current daily balance
	 * 
	 * @param op
	 * @param balance
	 */
	void updateCategoryBalanceWithOperation(Operation op, DailyBalance balance) {
		Entry<Category, BigDecimal> category = balance.findCategory(op);
		// If category is found, add amount to yet calculated amount.
		// Else, put new category
		if (category == null) {
			balance.getCategoryBalance().getCategories().put(op.getCategory(), new BigDecimal(op.getAmount()));
		} else {
			category.getValue().add(new BigDecimal(op.getAmount()));
		}
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
	public Operation addExpenseToAccount(Account account, Operation expense)
			throws TechnicalException, BusinessException {
		Operation result = null;
		if (expense.getAccount() == null && account != null) {
			expense.setAccount(account);
		}
		if (expense.getId() != null) {
			throw new BusinessException("expense.id_not_null");
		}

		OperationValidator.validateOperation(expense);

		try {
			result = operationsRepository.save(expense);
		} catch (RuntimeException e) {
			throw new TechnicalException("technical.save_error");
		}
		return result;
	}

	public Operation editExpenseForAccount(Account account, Operation expense)
			throws TechnicalException, BusinessException {
		if (expense.getId() == null)
			return addExpenseToAccount(account, expense);

		Operation persistedExpense = this.getExpenseByid(expense.getId());
		mergeExpenses(expense, persistedExpense);
		OperationValidator.validateOperation(expense);

		return expense;

	}

	/**
	 * Merges a modified expense with the one stored in database
	 * 
	 * @param expense
	 * @param persistedExpense
	 * @throws BusinessException
	 */
	private void mergeExpenses(Operation expense, Operation persistedExpense) throws BusinessException {
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
	public Operation getExpenseByid(Long id) {
		return operationsRepository.getOne(id);
	}
}
