package io.biologeek.expenses.services;

import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.domain.beans.balances.DailyBalance;
import io.biologeek.expenses.domain.beans.balances.FullPeriodicBalance;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.domain.beans.operations.OperationType;
import io.biologeek.expenses.domain.beans.operations.Regular;
import io.biologeek.expenses.repositories.OperationsRepository;
import io.biologeek.expenses.utils.DateUtils;
import io.biologeek.expenses.validation.OperationValidator;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;

import javax.transaction.Transactional;

@Service
@Transactional
public class OperationService {

	@Autowired
	OperationsRepository operationsRepository;
	@Autowired
	CurrencyDelegate currrencyDelegate;
	
	/**
	 * Validates operations and stores in datasource
	 * @param operation
	 * @return
	 */
	public Operation addOperationToAccount(Operation operation){
		if (operation == null)
			return null;
		if (operation.getId() != null)
			return this.updateOperation(operation);
		
		OperationValidator.validateOperation(operation);
		
		return operationsRepository.persist(operation);
		
	}


	public Operation updateOperation(Operation operation) {
		OperationValidator.validateOperation(operation);
		
		if (operation.getId() != null){
			Operation storedOperation = operationsRepository.getOne(operation.getId());
			
			if (storedOperation == null){
				new OperationMerger().merge(operation, storedOperation);
			}
		}
		return null;
	}


	public List<Operation> getLastOperationsForAccount(Account account,
			int limit) {
		return operationsRepository.getOperationsForAccountWithLimit(
				account.getId(), new PageRequest(0, limit));
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

	
	public FullPeriodicBalance getFullBalanceForPeriod(long account, Date begin,
			Date end, List<OperationType> collect, boolean withCategories) {

		List<Operation> operations = operationsRepository
				.getGroupedByDayOperationsForAccountByPeriod(account, begin,
						end);
		if (operations != null && operations.isEmpty())
			return new FullPeriodicBalance();

		if (withCategories)
			return buildFullBalanceWithCategoryDetail(operations);	
		else 
			return buildFullBalanceWithoutCategoryDetail(operations);	
			
	}
	public FullPeriodicBalance getFullBalanceForPeriod(long account, Date begin,
			Date end, List<OperationType> collect) {
	}

	/**
	 * Builds a balance during a period
	 * 
	 * @param operations
	 * @return
	 */
	private FullPeriodicBalance buildFullBalanceWithCategoryDetail(
			List<Operation> operations) {
		FullPeriodicBalance fullBalance = new FullPeriodicBalance();

		DailyBalance balanceOfTheDay = new DailyBalance();

		for (Operation op : operations) {

			if (isOperationDayAlreadyBalanced(op, fullBalance)) {
				if (op instanceof Regular
						&& ((Regular) op).getInterval()
								.isAnOperationOfTheDay(
										balanceOfTheDay.getBalanceDate())) {
					//
					op = ((Regular) op)
							.getInstantOperation(balanceOfTheDay
									.getBalanceDate());

				}
				balanceOfTheDay = updateBalanceWithOperation(op,
						balanceOfTheDay);
				// Adds amount to category balance
				updateCategoryBalanceWithOperation(op, balanceOfTheDay);
			} else {
				balanceOfTheDay = addOperationToNewBalance(op);
				// Adds amount to category balance
				updateCategoryBalanceWithOperation(op, balanceOfTheDay);
			}

		}
		return null;
	}
	
	/**
	 * Builds a balance during a period
	 * 
	 * @param operations
	 * @return
	 */
	private FullPeriodicBalance buildFullBalanceWithoutCategoryDetail(
			List<Operation> operations) {
		FullPeriodicBalance fullBalance= new FullPeriodicBalance();

		for (Operation op : operations) {
			DailyBalance balanceOfTheDay = new DailyBalance();

			if (isOperationDayAlreadyBalanced(op, fullBalance)) {
				if (op instanceof Regular
						&& ((Regular) op).getInterval()
								.isAnOperationOfTheDay(
										balanceOfTheDay.getBalanceDate())) {
					//
					op = ((Regular) op)
							.getInstantOperation(balanceOfTheDay
									.getBalanceDate());

				}
				balanceOfTheDay = updateBalanceWithOperation(op,
						balanceOfTheDay);
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
	private boolean isOperationDayAlreadyBalanced(Operation op,FullPeriodicBalance fullBalance) {
		if (op == null || fullBalance.getDailyBalances() == null
				|| fullBalance.getDailyBalances().size() == 0)
			return false;

		final Date dateOfOperation = op.getEffectiveDate();
		return fullBalance//
				.getDailyBalances()//
				.stream()//
				.anyMatch(new Predicate<DailyBalance>() {

					public boolean test(DailyBalance t) {
						return DateUtils.areSameDate(dateOfOperation,
								t.getBalanceDate());
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
	 * If the category to which belongs the operation exists in the balance,
	 * method will update balance of the category. Else it will create it
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
			convertedAmount = convertToBalanceCurrency(op.getAmount(),
					op.getCurrency(), balance.getBalanceCurrency());
		}

		balance.getBalanceValue().add(new BigDecimal(convertedAmount));

		return balance;
	}

	/**
	 * Manages category balance update and adds operation amount to category if
	 * it exists or creates a new category in current daily balance
	 * 
	 * @param op
	 * @param balance
	 */
	void updateCategoryBalanceWithOperation(Operation op, DailyBalance balance) {
		Entry<Category, BigDecimal> category = balance.findCategory(op);
		// If category is found, add amount to yet calculated amount.
		// Else, put new category
		if (category == null) {
			balance.getCategoryBalance().getCategories().put(op.getCategory(),
					new BigDecimal(op.getAmount()));
		}
		category.getValue().add(new BigDecimal(op.getAmount()));
	}

	/**
	 * Converts an operation or whatever amount to the good currency
	 * 
	 * @param amount
	 * @param currency
	 * @param balanceCurrency
	 * @return
	 */
	private Double convertToBalanceCurrency(Double amount, Currency currency,
			Currency balanceCurrency) {
		currrencyDelegate.convert(amount, currency, balanceCurrency);
		return amount;
	}
}
