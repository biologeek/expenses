package io.biologeek.expenses.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.biologeek.expenses.domain.beans.operations.Expense;

@Repository
public interface ExpensesRepository extends JpaRepository<Expense, Long>{
	@Query("from Expense where accountId = :accountId order by creationDate desc")
	public List<Expense> getExpensesForAccountWithLimit(Long accountId, int limit, Pageable pager);

}
