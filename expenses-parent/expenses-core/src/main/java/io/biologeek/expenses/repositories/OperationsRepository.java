package io.biologeek.expenses.repositories;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.beans.Category;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.domain.beans.operations.OperationType;

@Repository
public interface OperationsRepository extends JpaRepository<Operation, Long>{
	@Query("select op from Operation op where op.account.id = :accountId order by op.creationDate desc")
	public List<Operation> getOperationsForAccountWithLimit(@Param("accountId") Long accountId, Pageable pager);

	@Query("from Operation where account_id = :accountId and effectiveDate >= :begin and effectiveDate <= :end order by effectiveDate asc")
	public SortedSet<Operation> getGroupedByDayOperationsForAccountByPeriod(@Param("accountId") long account, @Param("begin") Date begin, @Param("end") Date end);

	@Query("from Operation where account_id = :accountId and operationType IN (EXPENSE, REGULAR_EXPENSE) order by effectiveDate")
	public List<Operation> getExpensesForAccountWithLimit(@Param("accountId") Long accountId, Pageable pager);

	@Query("select category.id, sum(amount) from Operation where account_id = :accountId and effectiveDate >= :begin "
			+ "and effectiveDate <= :end and operationType in (:opTypes) group by category")
	public Map<Long, BigDecimal> findOperationsForIntervalGroupedByCategories(//
			  @Param("accountId") Long account//
			, @Param("begin") Date begin //
			, @Param("end") Date end //
			, @Param("opTypes") List<OperationType> operationTypes);

	public int countOperationsByAccount(Account id);

	
	
}