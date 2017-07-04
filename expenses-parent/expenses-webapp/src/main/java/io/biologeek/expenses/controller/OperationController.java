package io.biologeek.expenses.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.biologeek.expenses.api.beans.OperationType;
import io.biologeek.expenses.converter.OperationToApiConverter.OperationTypeConverter;

@RestController
@RequestMapping("/operation")
public class OperationController {

	@RequestMapping(method = RequestMethod.GET, path = "/types")
	public ResponseEntity<List<OperationType>> getTypes() {
		return new ResponseEntity<>(
				OperationTypeConverter
						.convert(Arrays.asList(io.biologeek.expenses.domain.beans.operations.OperationType.values())),
				HttpStatus.OK);
	}

}
