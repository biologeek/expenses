package io.biologeek.expenses.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.biologeek.expenses.domain.beans.Entity;

@Repository
public interface EntityRepository extends JpaRepository<Entity, Long>{

	List<Entity> findAllByOwnerId(Long userId);

}
