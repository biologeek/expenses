package io.biologeek.expenses.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.biologeek.expenses.domain.beans.Entity;
import io.biologeek.expenses.domain.beans.OperationAgent;
import io.biologeek.expenses.domain.beans.RegisteredUser;
import io.biologeek.expenses.repositories.EntityRepository;

@Service
public class EntityService {

	@Autowired
	private EntityRepository repo;

	@Autowired
	private Merger<Entity> entityMerger;

	@Autowired
	private RegisteredUserService userService;

	public List<Entity> getAllEntitiesForUser(Long userId) {
		return this.repo.findAllByOwnerId(userId);
	}

	public Entity update(Entity entity) {

		Entity savedEntity = this.repo.findOne(entity.getId());

		savedEntity = this.entityMerger.merge(savedEntity, entity);
		return this.repo.save(savedEntity);
	}

	public Entity save(Long userId, Entity entity) {
		
		RegisteredUser user = this.userService.findUserById(userId);
		entity.setOwner(user);
		return this.repo.save(entity);
	}

}
