package io.biologeek.expenses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.biologeek.expenses.api.beans.Operation;
import io.biologeek.expenses.api.beans.PaginatedOperationsList;
import io.biologeek.expenses.api.beans.RegularOperation;
import io.biologeek.expenses.api.beans.TemporaryOperation;
import io.biologeek.expenses.beans.OperationList;
import io.biologeek.expenses.converter.AccountToApiConverter;
import io.biologeek.expenses.converter.OperationToApiConverter;
import io.biologeek.expenses.converter.OperationToModelConverter;
import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.beans.RegisteredUser;
import io.biologeek.expenses.services.AccountService;
import io.biologeek.expenses.services.OperationService;
import io.biologeek.expenses.services.RegisteredUserService;

@Controller
@RequestMapping("/mobile")
public class AndroidExpensesController extends ExceptionWrappedRestController {

	@Autowired
	OperationService opService;
	@Autowired
	AccountService accountService;
	
	@Autowired
	RegisteredUserService registeredUserService;

	@RequestMapping(path = { "/" }, method = { RequestMethod.GET })
	public ResponseEntity<Void> voidmethod() {
		return ResponseEntity.ok().build();
	}

	@RequestMapping(path = { "/account/{account}/operations", "/account/{account}/operation",
			"/account/{account}/operations/page/{page}" }, method = { RequestMethod.GET })
	public ResponseEntity<PaginatedOperationsList> getLastOperations(//
			@PathVariable("account") long accountId, //
 			@PathVariable(value = "page", required = false) Integer page, //
			@RequestParam(value = "limit", required = false) Integer limit,//
			@RequestParam(value = "orderBy", required = false) String orderBy,//
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
		result = opService.getLastOperationsForAccount(account, page, limit, orderBy, reverseOrder);
		if (result.getOperations().isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(OperationToApiConverter.convert(result), HttpStatus.OK);
	}

	@RequestMapping(path = { "/account/{account}/operation" }, method = { RequestMethod.POST })
	public ResponseEntity<Operation> addOperation(@PathVariable("account") long accountId,
			@RequestBody Operation expense) throws ExceptionWrapper {
		io.biologeek.expenses.domain.beans.operations.Operation result = null;
		Account account = accountService.getAccount(accountId);
		if (account == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		try {
			io.biologeek.expenses.domain.beans.operations.Operation op = null; 
			if (expense.getType().isRegular())
				OperationToModelConverter.convert((RegularOperation) expense);
			else if (expense.getType().isTemporary()) {
				OperationToModelConverter.convert((TemporaryOperation) expense);
			} else {
				OperationToModelConverter.convert(expense);
			}
			op.setAccount(account);
			result = opService.addOperationToAccount(op);
		} catch (Exception e) {
			throw new ExceptionWrapper(e);
		}
		return new ResponseEntity<>(OperationToApiConverter.convert(result), HttpStatus.CREATED);
	}

	@RequestMapping(path = { "/account/{account}/operation" }, method = { RequestMethod.PUT })
	public ResponseEntity<Operation> editExpense(@PathVariable("account") long accountId, @RequestBody Operation expense) throws ExceptionWrapper {
		io.biologeek.expenses.domain.beans.operations.Operation result = null;
		Account account = accountService.getAccount(accountId);

		if (account == null) {
			return new ResponseEntity<Operation>(HttpStatus.NOT_FOUND);
		}

		try {
			result = opService.updateOperation(account, OperationToModelConverter.convert(expense));
		} catch (Exception e) {
			throw new ExceptionWrapper(e);
		}
		return new ResponseEntity<>(OperationToApiConverter.convert(result), HttpStatus.OK);
	}

	@RequestMapping(path = { "/account/{account}/operation/{operation}" }, method = { RequestMethod.DELETE })
	public ResponseEntity<Void> deleteOperation(@PathVariable("account") long account, @PathVariable("operation") long operation) throws ExceptionWrapper {
		io.biologeek.expenses.domain.beans.operations.Operation result = null;
		
		try {
			opService.removeOperation(account, operation);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			throw new ExceptionWrapper(e);
		}
	}

	@RequestMapping(path = { "/operation/{id}" }, method = { RequestMethod.GET })
	public ResponseEntity<? extends Operation> getOperation(@PathVariable("id") long operationId) {
		io.biologeek.expenses.domain.beans.operations.Operation op = opService.getOperationByid(operationId);
		return new ResponseEntity(OperationToApiConverter.convert(op, new Operation()), HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(path = { "/account/{id}" }, method = { RequestMethod.GET })
	public ResponseEntity<io.biologeek.expenses.api.beans.Account> getAccount(@PathVariable("id") Long id) {
		Account acc = accountService.getAccount(id);
		return (ResponseEntity<io.biologeek.expenses.api.beans.Account>) ResponseEntity.ok().body(AccountToApiConverter.convert(acc));
		
		
	}
}
