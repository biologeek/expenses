package io.biologeek.expenses.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.biologeek.expenses.domain.beans.operations.Expense;
import io.biologeek.expenses.domain.beans.operations.Operation;

public interface OperationsRepository extends JpaRepository<Expense, Long>{
	@Query("from Operation where account_id = :accountId order by creationDate desc")
	public List<Operation> getOperationsForAccountWithLimit(@Param("accountId") Long accountId, Pageable pager);
}
