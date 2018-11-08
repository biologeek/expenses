package io.biologeek.expenses.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.security.User;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	List<Account> findByOwner(User user);
}
