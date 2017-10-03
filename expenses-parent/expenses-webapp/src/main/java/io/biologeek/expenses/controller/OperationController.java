package io.biologeek.expenses.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.biologeek.expenses.api.beans.OperationType;

@RestController
@RequestMapping("/operation")
public class OperationController extends ExceptionWrappedRestController{

	@RequestMapping(method = RequestMethod.GET, path = "/types")
	public ResponseEntity<List<OperationType>> getTypes() {
		return new ResponseEntity<>(
				Arrays.asList(OperationType.values()),
				HttpStatus.OK);
	}

}
