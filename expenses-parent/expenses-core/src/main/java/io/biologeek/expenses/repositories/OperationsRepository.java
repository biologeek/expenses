package io.biologeek.expenses.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.biologeek.expenses.domain.beans.operations.Operation;

public interface OperationsRepository extends JpaRepository<Operation, Long>{
	@Query("from Operation where account_id = :accountId order by creationDate desc")
	public List<Operation> getOperationsForAccountWithLimit(@Param("accountId") Long accountId, Pageable pager);

	@Query("from Operation where account_id = :accountId and begin >= :begin and end <= :end order by effective_date")
	public List<Operation> getGroupedByDayOperationsForAccountByPeriod(long account, Date begin, Date end);
	
	/**
	 * 
	 */
	@Query("from Operation where account_id = :accountId and operationType IN (EXPENSE, REGULAR_EXPENSE) order by effective_date desc")
	public List<Operation> getExpensesForAccountWithLimit(@Param("accountId") Long accountId, Pageable pager);
}
