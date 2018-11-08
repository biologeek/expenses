package io.biologeek.expenses.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="/")
public class DefaultController {

	@RequestMapping(value={"/"}, method={RequestMethod.GET})
	public ResponseEntity<Void> name() {
		return ResponseEntity.ok().build();
	}
}
