package io.biologeek.expenses.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.biologeek.expenses.domain.security.Authority;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
