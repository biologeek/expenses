package io.biologeek.expenses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.biologeek.expenses.converter.AccountToApiConverter;
import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.services.AccountService;
import io.biologeek.expenses.services.OperationService;
import io.biologeek.expenses.services.RegisteredUserService;

@Controller
@RequestMapping("/api/mobile")
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


	@RequestMapping(path = { "/account/{id}" }, method = { RequestMethod.GET })
	public ResponseEntity<io.biologeek.expenses.api.beans.Account> getAccount(@PathVariable("id") Long id) {
		Account acc = accountService.getAccount(id);
		return (ResponseEntity<io.biologeek.expenses.api.beans.Account>) ResponseEntity.ok()
				.body(AccountToApiConverter.convert(acc));

	}

}
