package io.biologeek.expenses.services.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import io.biologeek.expenses.domain.beans.Interval;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.domain.beans.operations.OperationType;
import io.biologeek.expenses.domain.beans.operations.RegularOperation;
import io.biologeek.expenses.domain.beans.operations.UsualOperation;

/**
 * This service aims at implementing business rules for {@link RegularOperation}s processing (adding, updating and removing of such operations). 
 */
@Service
public class RegularOperationBusinessService extends OperationBusinessServices<RegularOperation> {

	@Override
	public	Set<UsualOperation> processOperationAndGenerateConcrete(RegularOperation operationToProcess) {
		Set<UsualOperation> operations = new HashSet<>(0);
		operationToProcess.setChildrenOperations(new ArrayList<>(operations));
		Interval interval = operationToProcess.getInterval();
		Date date = interval.getFirstDate();
		Date now = new Date();
		while (date.before(interval.getLastDate())){
			if (date.after(now)){
				Operation op = new UsualOperation(operationToProcess);
				op.setEffectiveDate(date);
				op.setParentOperation(operationToProcess);
				
			}
		}
		return null;
	}

}
