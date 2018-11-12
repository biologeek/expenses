package io.biologeek.expenses.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.biologeek.expenses.domain.beans.Entity;
import io.biologeek.expenses.repositories.EntityRepository;

@Service
public class EntityService {

	@Autowired
	private EntityRepository repo;

	public List<Entity> getAllEntitiesForUser(Long userId) {
		
		return this.repo.findAllByOwnerId(userId);
	}

}
