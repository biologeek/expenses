package io.biologeek.expenses.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.h2.util.New;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.biologeek.expenses.api.beans.Operation;
import io.biologeek.expenses.api.beans.OperationType;
import io.biologeek.expenses.api.beans.PaginatedOperationsList;
import io.biologeek.expenses.api.beans.RegularOperation;
import io.biologeek.expenses.api.beans.TemporaryOperation;
import io.biologeek.expenses.beans.OperationList;
import io.biologeek.expenses.converter.OperationToApiConverter;
import io.biologeek.expenses.converter.OperationToModelConverter;
import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.exceptions.BusinessException;
import io.biologeek.expenses.exceptions.ValidationException;
import io.biologeek.expenses.services.AccountService;
import io.biologeek.expenses.services.OperationService;

@RestController
@RequestMapping("/api/operation")
public class OperationController extends ExceptionWrappedRestController {

	@Autowired
	private OperationService operationService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private OperationToModelConverter operationToModelConverter;
	@Autowired
	OperationToApiConverter operationToApiConverter;

	@RequestMapping(method = RequestMethod.GET, path = "/types")
	public ResponseEntity<List<OperationType>> getTypes() {
		return new ResponseEntity<>(Arrays.asList(OperationType.values()), HttpStatus.OK);
	}

	@RequestMapping(path = { "/account/{account}/operations", "/account/{account}/operation",
			"/account/{account}/operations/page/{page}" }, method = { RequestMethod.GET })
	public ResponseEntity<PaginatedOperationsList> getLastOperations(//
			@PathVariable("account") long accountId, //
			@PathVariable(value = "page", required = false) Integer page, //
			@RequestParam(value = "limit", required = false) Integer limit, //
			@RequestParam(value = "orderBy", required = false) String orderBy, //
			@RequestParam(value = "reverse", required = false) boolean reverseOrder)//
	{

		if (page == null)
			page = 1;

		OperationList result = null;
		if (limit == null || limit.equals(Integer.valueOf(0)))
			limit = 20;
		Account account = accountService.getAccount(accountId);

		if (account == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		result = operationService.getLastOperationsForAccount(account, page, limit, orderBy, reverseOrder);
		if (result.getOperations().isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(operationToApiConverter.convert(result), HttpStatus.OK);
	}

	@RequestMapping(path = { "/account/{account}/operation" }, method = { RequestMethod.POST })
	public ResponseEntity<Operation> addOperation(@PathVariable("account") long accountId,
			@RequestBody Operation expense) throws ExceptionWrapper, ValidationException, BusinessException {
		io.biologeek.expenses.domain.beans.operations.Operation result = null;
		Account account = accountService.getAccount(accountId);
		if (account == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		io.biologeek.expenses.domain.beans.operations.Operation op = null;
		if (expense.getType() != null && expense.getType().isRegular())
			op = operationToModelConverter.convert((RegularOperation) expense);
		else if (expense.getType() != null && expense.getType().isTemporary()) {
			op = operationToModelConverter.convert((TemporaryOperation) expense);
		} else {
			op = operationToModelConverter.convert(expense);
		}
		op.setAccount(account);
		result = operationService.addOperationToAccount(op);

		return new ResponseEntity<>(operationToApiConverter.convert(result), HttpStatus.CREATED);
	}

	@RequestMapping(path = { "/account/{account}/operation" }, method = { RequestMethod.PUT })
	public ResponseEntity<Operation> editExpense(@PathVariable("account") long accountId,
			@RequestBody Operation expense) throws ExceptionWrapper {
		io.biologeek.expenses.domain.beans.operations.Operation result = null;
		if (!expense.isModifiable())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		Account account = accountService.getAccount(accountId);

		if (account == null) {
			return new ResponseEntity<Operation>(HttpStatus.NOT_FOUND);
		}

		try {
			result = operationService.updateOperation(account, operationToModelConverter.convert(expense));
		} catch (Exception e) {
			throw new ExceptionWrapper(e);
		}
		return new ResponseEntity<>(operationToApiConverter.convert(result), HttpStatus.OK);
	}

	@RequestMapping(path = { "/account/{account}/operation/{operation}" }, method = { RequestMethod.DELETE })
	public ResponseEntity<Void> deleteOperation(@PathVariable("account") long account,
			@PathVariable("operation") long operation) throws ExceptionWrapper {
		io.biologeek.expenses.domain.beans.operations.Operation result = null;

		try {
			operationService.removeOperation(account, operation);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			throw new ExceptionWrapper(e);
		}
	}

	@RequestMapping(path = { "/operation/{id}" }, method = { RequestMethod.GET })
	public ResponseEntity<? extends Operation> getOperation(@PathVariable("id") long operationId) {
		io.biologeek.expenses.domain.beans.operations.Operation op = operationService.getOperationByid(operationId);
		return new ResponseEntity(operationToApiConverter.convert(op, new Operation()), HttpStatus.OK);
	}
	@RequestMapping(path = { "/operation/types" }, method = { RequestMethod.GET })
	public ResponseEntity<OperationType[]> getOperationTypes() {
		return new ResponseEntity<>(OperationType.values(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/")
	public ResponseEntity<Operation> addOperation(@RequestBody Operation body)
			throws ValidationException, BusinessException {
		return new ResponseEntity<>(
				operationToApiConverter
						.convert(operationService.addOperationToAccount(operationToModelConverter.convert(body))),
				HttpStatus.OK);
	}

}
