import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import io.biologeek.expenses.api.beans.Expense;
import io.biologeek.expenses.converter.ExpenseToApiConverter;
import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.services.AccountService;
import io.biologeek.expenses.services.ExpensesService;

@Controller
@RequestMapping("/mobile")
public class AndroidExpensesController {
	
	@Autowired
	ExpensesService expensesService;
	@Autowired
	AccountService accountService;
	
	@RequestMapping(path={"/expenses/{account}"}, method={RequestMethod.GET})
	public ResponseEntity<List<Expense>> getLastExpenses(@PathVariable("account") long accountId, @RequestParam("limit") int limit){
		List<io.biologeek.expenses.domain.beans.operations.Expense> result = null;
		Account account = accountService.getAccount(accountId);
		
		if (account == null){
			return new ResponseEntity<List<Expense>>(HttpStatus.NOT_FOUND);
		}
		result = expensesService.getLastExpensesForAccount(account, limit);
		if (result.isEmpty())
			return new ResponseEntity<List<Expense>>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<>(ExpenseToApiConverter.convert(result), HttpStatus.OK);
	}

}
