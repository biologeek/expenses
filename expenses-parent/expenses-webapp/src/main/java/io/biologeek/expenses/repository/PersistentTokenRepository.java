package io.biologeek.expenses.repository;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

import io.biologeek.expenses.domain.security.PersistentToken;
import io.biologeek.expenses.domain.security.User;

import java.util.List;

/**
 * Spring Data JPA repository for the PersistentToken entity.
 */
public interface PersistentTokenRepository extends JpaRepository<PersistentToken, String> {

    List<PersistentToken> findByUser(User user);

    List<PersistentToken> findByTokenDateBefore(LocalDate localDate);

}
