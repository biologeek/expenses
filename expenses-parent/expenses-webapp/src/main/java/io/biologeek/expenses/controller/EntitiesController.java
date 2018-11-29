package io.biologeek.expenses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.biologeek.expenses.api.beans.Entity;
import io.biologeek.expenses.converter.UserConverter;
import io.biologeek.expenses.services.EntityService;

@RestController
@RequestMapping("/api/entities")
public class EntitiesController {

	@Autowired
	private EntityService entitiesService;
	@Autowired
	private UserConverter entitiesConverter;

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Entity>> getEntitiesForUser(@PathVariable("userId") Long userId) {
		List<Entity> result = entitiesConverter.convert(entitiesService.getAllEntitiesForUser(userId));
		if (result.size() > 0) {
			return new ResponseEntity<List<Entity>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Entity>>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/user/{userId}")
	public ResponseEntity<Entity> saveEntity(@PathVariable("userId") Long userId, @RequestBody Entity entity) {
		return new ResponseEntity<>(
				this.entitiesConverter.convert(this.entitiesService.save(userId, this.entitiesConverter.toModel(entity))),
				HttpStatus.CREATED);
	}
}
