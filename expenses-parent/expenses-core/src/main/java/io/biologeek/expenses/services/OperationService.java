package io.biologeek.expenses.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import io.biologeek.expenses.beans.OperationList;
import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.domain.beans.balances.BalanceUnit;
import io.biologeek.expenses.domain.beans.balances.CategoryBalance;
import io.biologeek.expenses.domain.beans.balances.FullPeriodicBalance;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.domain.beans.operations.OperationType;
import io.biologeek.expenses.domain.beans.operations.Regular;
import io.biologeek.expenses.domain.beans.operations.RegularOperation;
import io.biologeek.expenses.exceptions.BusinessException;
import io.biologeek.expenses.exceptions.ValidationException;
import io.biologeek.expenses.repositories.OperationsRepository;
import io.biologeek.expenses.services.business.OperationBusinessServices;
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
	
	@Autowired
	OperationBusinessServices<RegularOperation> regularOperationBizServices;

	/**
	 * Validates operations and stores in datasource
	 * 
	 * @param operation
	 * @return
	 * @throws BusinessException
	 */
	public Operation addOperationToAccount(Operation operation) throws ValidationException, BusinessException {
		if (operation == null)
			return null;
		if (operation.getId() != null) {
			if (!operation.getId().equals(0L))
				return this.updateOperation(operation);
		}
		OperationValidator.validateOperation(operation);

		return operationsRepository.save(operation);

	}

	/**
	 * Updates an operation based on its ID. Operation is validated before merging and updating
	 * @param operation
	 * @return
	 * @throws BusinessException
	 * @throws ValidationException
	 */
	public Operation updateOperation(Operation operation) throws BusinessException, ValidationException {
		OperationValidator.validateOperation(operation);

		if (operation.getId() != null) {
			Operation storedOperation = operationsRepository.getOne(operation.getId());

			storedOperation = new OperationMerger().merge(operation, storedOperation);
			
			return operationsRepository.save(storedOperation);
		}
		throw new ValidationException("operation.update.id_null");
	}

	public OperationList getLastOperationsForAccount(Account account, int page, Integer limit, String orderBy,
			boolean reverseOrder) {
		OperationList list = new OperationList();
		List<Operation> operations = operationsRepository.getOperationsForAccountWithLimit(account.getId(),
				new PageRequest(0, limit));

		int totalOperations = operationsRepository.countOperationsByAccount(account);
		list.setOperations(operations);
		list.setCurrentPage(page);
		list.setOperationPerPage(limit);
		list.setTotalOperations(totalOperations);
		list.setTotalPages(new Double(Math.ceil(totalOperations / limit)).intValue());
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

	/**
	 * Generic method with all necesary options to generate a List of
	 * {@link FullPeriodicBalance}s
	 * 
	 * @param account
	 *            user account ID
	 * @param interval
	 *            type of unit for balance (balance per day, per month, ...)
	 * @param begin
	 *            beginning of calculation period
	 * @param end
	 *            end of calculation period
	 * @param collect
	 *            operation types to collect in balance
	 * @param withCategories
	 *            include balance per category for every period unit
	 * @param separateOperations
	 *            Will generate separate balances for every {@link OperationType}
	 * 
	 * @return a list of {@link FullPeriodicBalance}
	 */
	List<FullPeriodicBalance> getFullBalanceForPeriod(long account, DateTimeUnit interval, Date begin, Date end,
			List<OperationType> collect, boolean withCategories, boolean separateOperations) {
		List<FullPeriodicBalance> balances = new ArrayList<>();
		SortedSet<Operation> operations = operationsRepository.getGroupedByDayOperationsForAccountByPeriod(account,
				begin, end);
		if (operations != null && operations.isEmpty())
			return new ArrayList<>();

		Map<OperationType, Set<Operation>> separatedTypes = new HashMap<>();
		if (separateOperations)
			separatedTypes = separateOperations(operations);
		else
			separatedTypes.put(OperationType.ALL, new TreeSet<>(operations));
		if (withCategories)
			balances.add(buildFullBalanceWithCategoryDetail(operations));
		else
			balances.add(buildFullBalanceWithoutCategoryDetail(operations, interval, begin, end));

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
	Map<OperationType, Set<Operation>> separateOperations(SortedSet<Operation> operations) {
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
	public List<FullPeriodicBalance> getPeriodicBalanceForPeriodWithoutCategoriesNorSeparatingTypes(long account,
			DateTimeUnit interval, Date begin, Date end, List<OperationType> collect) {
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
	 * Note that this method does generate detail per category but does not separate
	 * per type of operation (expenses are summed with incomes)
	 * 
	 * @param account
	 * @param interval
	 * @param begin
	 * @param end
	 * @param collect
	 * @return
	 */
	public List<FullPeriodicBalance> getFullBalanceWithCategoriesForPeriodWithoutSeparatingTypes(long account,
			DateTimeUnit interval, Date begin, Date end, List<OperationType> collect) {
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
	 * Note that this method does generate detail per category and separate per type
	 * of operation (expenses and incomes have different
	 * {@link FullPeriodicBalance}). Thus list contains as much
	 * {@link FullPeriodicBalance} as <i>collect</i> size.
	 * 
	 * @param account
	 * @param interval
	 * @param begin
	 * @param end
	 * @param collect
	 * @return
	 */
	public List<FullPeriodicBalance> getFullBalanceWithCategoriesForPeriodWithSeparatingTypes(long account,
			DateTimeUnit interval, Date begin, Date end, List<OperationType> collect) {
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
	 * Note that this method does not generate detail per category but separates per
	 * type of operation (expenses and incomes have different
	 * {@link FullPeriodicBalance}). Thus list contains as much
	 * {@link FullPeriodicBalance} as <i>collect</i> size.
	 * 
	 * @param account
	 * @param interval
	 * @param begin
	 * @param end
	 * @param collect
	 * @return
	 */
	public List<FullPeriodicBalance> getFullBalanceWithoutCategoriesForPeriodWithSeparatingTypes(long account,
			DateTimeUnit interval, Date begin, Date end, List<OperationType> collect) {
		return getFullBalanceForPeriod(account, interval, begin, end, collect, false, true);
	}

	/**
	 * Builds a balance during a period
	 * 
	 * @param operations
	 * @return
	 */
	private FullPeriodicBalance buildFullBalanceWithCategoryDetail(SortedSet<Operation> operations) {
		FullPeriodicBalance fullBalance = new FullPeriodicBalance();

		BalanceUnit balanceOfTheDay = new BalanceUnit();

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
	 * @param end
	 * @param begin
	 * @return
	 */
	private FullPeriodicBalance buildFullBalanceWithoutCategoryDetail(SortedSet<Operation> operations,
			DateTimeUnit interval, Date begin, Date end) {
		FullPeriodicBalance fullBalance = new FullPeriodicBalance();

		Calendar incrementingCal = Calendar.getInstance();
		incrementingCal.setTime(begin);

		fullBalance.setBegin(begin);
		fullBalance.setEnd(end);

		while (incrementingCal.getTime().before(end)) {

			Calendar nextCal = (Calendar) incrementingCal.clone();
			nextCal.add(DateTimeUnit.getCalendarUnit(interval), 1);

			fullBalance.getDailyBalances()
					.add(buildBalanceForUnitOfTime(operations, incrementingCal.getTime(), nextCal.getTime()));

			incrementingCal = nextCal;
		}
		return fullBalance;
	}

	/**
	 * Builds a unit balance (generally a daily balance) from the list of operations
	 * given in parameter.
	 * 
	 * It filters operations to keep only those respecting constrait defined by
	 * periodBegin and periodEnd
	 * 
	 * @param operations
	 *            a list of operations
	 * @param periodBegin
	 *            beginning datetime of the balance
	 * @param periodEnd
	 *            end datetime of the balance
	 * @return
	 */
	BalanceUnit buildBalanceForUnitOfTime(SortedSet<Operation> operations, Date periodBegin, Date periodEnd) {

		BalanceUnit unit = new BalanceUnit();
		unit.computeBalanceDate(periodBegin, periodEnd);
		List<Operation> stream = operations.stream().filter(new Predicate<Operation>() {
			@Override
			public boolean test(Operation arg0) {
				return (arg0.getEffectiveDate().before(periodEnd) || arg0.getEffectiveDate().equals(periodEnd))
						&& (arg0.getEffectiveDate().after(periodBegin) || arg0.getEffectiveDate().equals(periodBegin));
			}
		}).collect(Collectors.toList());
		// Convert to correct unit and add amount to balance value
		for (Operation t : stream) {
			t.amount(convertToBalanceCurrency(t.getAmount().doubleValue(), t.getCurrency(), unit.getBalanceCurrency()))//
					.currency(unit.getBalanceCurrency());
			unit.setBalanceValue(unit.getBalanceValue().add(BigDecimal.valueOf(t.getAmount())));
		}

		return unit;
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
				.anyMatch(new Predicate<BalanceUnit>() {
					public boolean test(BalanceUnit t) {
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
	 * Creates a new {@link BalanceUnit} and adds operation to it
	 * 
	 * @param op
	 *            the operation to add
	 * @return a {@link BalanceUnit} for the day of the operation
	 */
	BalanceUnit addOperationToNewBalance(Operation op) {
		BalanceUnit res = new BalanceUnit();
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
	BalanceUnit updateBalanceWithOperation(Operation op, BalanceUnit balance) {
		if (!op.getEffectiveDate().equals(balance.getBalanceDate())) {
			balance = new BalanceUnit();
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
		return currrencyDelegate.convert(amount, currency, balanceCurrency);
	}

	public Operation updateOperation(Account account, Operation convert) throws BusinessException, ValidationException {
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

	public void removeOperation(long account, long operation) {
		operationsRepository.delete(operation);
	}
}