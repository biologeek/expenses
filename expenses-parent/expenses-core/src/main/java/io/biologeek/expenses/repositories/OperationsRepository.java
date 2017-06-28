package io.biologeek.expenses.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.domain.beans.operations.OperationType;

public interface OperationsRepository extends JpaRepository<Operation, Long>{
	@Query("from Operation where account_id = :accountId order by creationDate desc")
	public List<Operation> getOperationsForAccountWithLimit(@Param("accountId") Long accountId, Pageable pager);

	@Query("from Operation where account_id = :accountId and effectiveDate >= :begin and effectiveDate <= :end order by effective_date")
	public List<Operation> getGroupedByDayOperationsForAccountByPeriod(@Param("accountId") long account, @Param("begin") Date begin, @Param("end") Date end);

	@Query("from Operation where account.id = :accountId and effectiveDate >= :begin and effectiveDate <= :end and operationType in (:operationTypes) order by effective_date")
	public List<Operation> getGroupedByDayAndPeriodAndTypeOfOperationsForAccount(@Param("accountId") long account, @Param("begin") Date begin, @Param("end") Date end, @Param("operationTypes") OperationType... operationTypes);
	
	/**
	 * 
	 */
	@Query("from Operation where account_id = :accountId and operationType IN (EXPENSE, REGULAR_EXPENSE) order by effective_date desc")
	public List<Operation> getExpensesForAccountWithLimit(@Param("accountId") Long accountId, Pageable pager);
}
