package io.biologeek.expenses.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.beans.RegisteredUser;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	List<Account> findByOwner(RegisteredUser user);
}
