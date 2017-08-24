package io.biologeek.expenses.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import io.biologeek.expenses.beans.OperationList;
import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.domain.beans.balances.CategoryBalance;
import io.biologeek.expenses.domain.beans.balances.DailyBalance;
import io.biologeek.expenses.domain.beans.balances.FullPeriodicBalance;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.domain.beans.operations.OperationType;
import io.biologeek.expenses.domain.beans.operations.Regular;
import io.biologeek.expenses.exceptions.BusinessException;
import io.biologeek.expenses.repositories.OperationsRepository;
import io.biologeek.expenses.utils.DateTimeUnit;
import io.biologeek.expenses.utils.DateUtils;
import io.biologeek.expenses.validation.OperationValidator;

@Service
@Transactional
public class OperationService {

	@Autowired
	OperationsRepository operationsRepository;
	@Autowired
	CurrencyDelegate currrencyDelegate;
	@Autowired
	CategoryService cateoryService;

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

	public OperationList getLastOperationsForAccount(Account account, int page, Integer limit, String orderBy,
			boolean reverseOrder) {
		OperationList list = new OperationList();
		List<Operation> operations = operationsRepository.getOperationsForAccountWithLimit(account.getId(),
				new PageRequest(0, limit));
		list.setOperations(operations);
		list.setCurrentPage(page);
		list.setOperationPerPage(limit);
		list.setTotalOperations(operations.size());
		list.setTotalPages(new Double(Math.ceil(operations.size() / limit) + 1).intValue());
		return list;
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

	private List<FullPeriodicBalance> getFullBalanceForPeriod(long account, DateTimeUnit interval, Date begin, Date end,
			List<OperationType> collect, boolean withCategories, boolean separateOperations) {
		List<FullPeriodicBalance> balances = new ArrayList<>();
		List<Operation> operations = operationsRepository.getGroupedByDayOperationsForAccountByPeriod(account, begin,
				end);
		if (operations != null && operations.isEmpty())
			return new ArrayList<>();

		Map<OperationType, Set<Operation>> separatedTypes = new HashMap<>();
		if (separateOperations)
			separatedTypes = separateOperations(operations);
		else
			separatedTypes.put(OperationType.ALL, new TreeSet(operations));
		if (withCategories)
			balances.add(buildFullBalanceWithCategoryDetail(operations));
		else
			balances.add(buildFullBalanceWithoutCategoryDetail(operations, interval));

		return balances;
	}

	/**
	 * Separates operations in order to create 2 (or more) separate lists for every
	 * type of operations
	 * 
	 * @param operations
	 *            operations to sort
	 * 
	 * @return a {@link Map} with {@link OperationType}s as keys
	 */
	Map<OperationType, Set<Operation>> separateOperations(List<Operation> operations) {
		Map<OperationType, Set<Operation>> result = new HashMap<>();
		for (Operation op : operations) {
			if (!result.containsKey(op.getOperationType()))
				result.put(op.getOperationType(), new TreeSet<>());

			result.get(op.getOperationType()).add(op);
		}
		return result;
	}

	/**
	 * Method that generates a time based balance object containing data for chosen
	 * period (begin to end). <br>
	 * Scale of the balance can be managed with interval. <br>
	 * <br>
	 * For example for a daily balance of operations (meaning daily total of
	 * operations), use {@link DateTimeUnit.DAYS}.
	 * 
	 * Note that this method does not generate detail per category nor does it
	 * separate per type of operation (expenses are summed with incomes)
	 * 
	 * @param account
	 * @param interval
	 * @param begin
	 * @param end
	 * @param collect
	 * @return
	 */
	public List<FullPeriodicBalance> getPeriodicBalanceForPeriodWithoutCategoriesNorSeparatingTypes(long account, DateTimeUnit interval, Date begin,
			Date end, List<OperationType> collect) {
		return getFullBalanceForPeriod(account, interval, begin, end, collect, false, false);
	}

	/**
	 * Method that generates a time based balance object containing data for chosen
	 * period (begin to end). <br>
	 * Scale of the balance can be managed with interval. <br>
	 * <br>
	 * For example for a daily balance of operations (meaning daily total of
	 * operations), use {@link DateTimeUnit.DAYS}.
	 * 
	 * Note that this method does generate detail per category but does not
	 * separate per type of operation (expenses are summed with incomes)
	 * 
	 * @param account
	 * @param interval
	 * @param begin
	 * @param end
	 * @param collect
	 * @return
	 */
	public List<FullPeriodicBalance> getFullBalanceWithCategoriesForPeriodWithoutSeparatingTypes(long account, DateTimeUnit interval, Date begin, Date end,
			List<OperationType> collect) {
		return getFullBalanceForPeriod(account, interval, begin, end, collect, true, false);
	}

	/**
	 * Method that generates a time based balance object containing data for chosen
	 * period (begin to end). <br>
	 * Scale of the balance can be managed with interval. <br>
	 * <br>
	 * For example for a daily balance of operations (meaning daily total of
	 * operations), use {@link DateTimeUnit.DAYS}.
	 * 
	 * Note that this method does generate detail per category and
	 * separate per type of operation (expenses and incomes have different {@link FullPeriodicBalance}).
	 * Thus list contains as much {@link FullPeriodicBalance} as <i>collect</i> size.
	 * 
	 * @param account
	 * @param interval
	 * @param begin
	 * @param end
	 * @param collect
	 * @return
	 */
	public List<FullPeriodicBalance> getFullBalanceWithCategoriesForPeriodWithSeparatingTypes(long account, DateTimeUnit interval, Date begin, Date end,
			List<OperationType> collect) {
		return getFullBalanceForPeriod(account, interval, begin, end, collect, true, true);
	}

	/**
	 * Method that generates a time based balance object containing data for chosen
	 * period (begin to end). <br>
	 * Scale of the balance can be managed with interval. <br>
	 * <br>
	 * For example for a daily balance of operations (meaning daily total of
	 * operations), use {@link DateTimeUnit.DAYS}.
	 * 
	 * Note that this method does not generate detail per category but
	 * separates per type of operation (expenses and incomes have different {@link FullPeriodicBalance}).
	 * Thus list contains as much {@link FullPeriodicBalance} as <i>collect</i> size.
	 * 
	 * @param account
	 * @param interval
	 * @param begin
	 * @param end
	 * @param collect
	 * @return
	 */
	public List<FullPeriodicBalance> getFullBalanceWithoutCategoriesForPeriodWithSeparatingTypes(long account, DateTimeUnit interval, Date begin, Date end,
			List<OperationType> collect) {
		return getFullBalanceForPeriod(account, interval, begin, end, collect, false, true);
	}

	/**
	 * Builds a balance during a period
	 * 
	 * @param operations
	 * @return
	 */
	private FullPeriodicBalance buildFullBalanceWithCategoryDetail(List<Operation> operations) {
		FullPeriodicBalance fullBalance = new FullPeriodicBalance();

		DailyBalance balanceOfTheDay = new DailyBalance();

		for (Operation op : operations) {

			if (isOperationDayAlreadyBalanced(op, fullBalance, null)) {
				if (op instanceof Regular
						&& ((Regular) op).getInterval().isAnOperationOfTheDay(balanceOfTheDay.getBalanceDate())) {
					//
					op = ((Regular) op).getInstantOperation(balanceOfTheDay.getBalanceDate());

				}
				balanceOfTheDay = updateBalanceWithOperation(op, balanceOfTheDay);
				// Adds amount to category balance
				// updateCategoryBalanceWithOperation(op, balanceOfTheDay);
			} else {
				balanceOfTheDay = addOperationToNewBalance(op);
				// Adds amount to category balance
				// updateCategoryBalanceWithOperation(op, balanceOfTheDay);
			}

		}
		return fullBalance;
	}

	/**
	 * Builds a balance during a period with only a balance of sum for every
	 * day/month/year.
	 * 
	 * @param operations
	 * @param interval
	 *            the interval unit
	 * @return
	 */
	private FullPeriodicBalance buildFullBalanceWithoutCategoryDetail(List<Operation> operations,
			DateTimeUnit interval) {
		FullPeriodicBalance fullBalance = new FullPeriodicBalance();

		for (Operation op : operations) {
			DailyBalance balanceOfTheDay = new DailyBalance();

			if (isOperationDayAlreadyBalanced(op, fullBalance, interval)) {
				if (op instanceof Regular
						&& ((Regular) op).getInterval().isAnOperationOfTheDay(balanceOfTheDay.getBalanceDate())) {
					//
					op = ((Regular) op).getInstantOperation(balanceOfTheDay.getBalanceDate());

				}
				balanceOfTheDay = updateBalanceWithOperation(op, balanceOfTheDay);
			} else {
				balanceOfTheDay = addOperationToNewBalance(op);
			}

			fullBalance.getDailyBalances().add(balanceOfTheDay);

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
	private boolean isOperationDayAlreadyBalanced(Operation op, FullPeriodicBalance fullBalance,
			DateTimeUnit interval) {
		if (op == null || fullBalance.getDailyBalances() == null || fullBalance.getDailyBalances().size() == 0)
			return false;

		final Date dateOfOperation = op.getEffectiveDate();
		return fullBalance//
				.getDailyBalances()//
				.stream()//
				.anyMatch(new Predicate<DailyBalance>() {
					public boolean test(DailyBalance t) {
						switch (interval) {
						case DAYS:
							return DateUtils.areSameDate(dateOfOperation, t.getBalanceDate());
						case MONTHS:
							return DateUtils.areSameMonth(dateOfOperation, t.getBalanceDate());
						case YEARS:
							return DateUtils.areSameYear(dateOfOperation, t.getBalanceDate());
						default:
							return DateUtils.areSameDate(dateOfOperation, t.getBalanceDate());
						}
					}
				});
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

		balance.getBalanceValue().add(new BigDecimal(convertedAmount));

		return balance;
	}

	/**
	 * Manages category balance update and adds operation amount to category if it
	 * exists or creates a new category in current daily balance
	 * 
	 * @param op
	 * @param balance
	 */
	/*
	 * void updateCategoryBalanceWithOperation(Operation op, DailyBalance balance) {
	 * Entry<Category, BigDecimal> category = balance.findCategory(op); // If
	 * category is found, add amount to yet calculated amount. // Else, put new
	 * category if (category == null) {
	 * balance.getCategoryBalance().getCategories().put(op.getCategory(), new
	 * BigDecimal(op.getAmount())); } category.getValue().add(new
	 * BigDecimal(op.getAmount())); }
	 */
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

	public Operation updateOperation(Account account, Operation convert) throws BusinessException {
		convert.setAccount(account);
		return updateOperation(convert);
	}

	/**
	 * Builds a Balance with total sum earned or spent for each category defined by
	 * user. <br>
	 * <br>
	 * Balance is calculated over given period for selected operation types
	 * (expense, incomes, ...).
	 * 
	 * 
	 * @param account
	 * @param begin
	 * @param end
	 * @param operationTypes
	 * @return a balance by category for pie chart display for example
	 */
	public CategoryBalance getCategoryBalanceForAccount(Long account, Date begin, Date end,
			List<OperationType> operationTypes) {
		CategoryBalance balance = new CategoryBalance();

		Map<Long, BigDecimal> categories = operationsRepository.findOperationsForIntervalGroupedByCategories(account,
				begin, end, operationTypes);
		Map<Category, BigDecimal> categoriesWithObjects = new HashMap<>();

		List<Category> categoriesObjs = cateoryService.getCategoriesByIds(categories.keySet());
		for (Long cat : categories.keySet()) {
			for (Category catObj : categoriesObjs) {
				if (catObj.getId().equals(cat)) {
					categoriesWithObjects.put(catObj, categories.get(catObj));
				}
			}
		}
		balance.setBalanceBeginDate(begin);
		balance.setBalanceEndDate(end);
		balance.setCategories(categoriesWithObjects);
		return balance;
	}
}