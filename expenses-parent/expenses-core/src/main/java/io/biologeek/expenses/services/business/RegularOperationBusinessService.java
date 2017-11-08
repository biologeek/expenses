package io.biologeek.expenses.services.business;

import java.util.Set;

import org.springframework.stereotype.Service;

import io.biologeek.expenses.domain.beans.operations.RegularOperation;
import io.biologeek.expenses.domain.beans.operations.UsualOperation;

/**
 * This service aims at implementing business rules for {@link RegularOperation}s processing (adding, updating and removing of such operations). 
 */
@Service
public class RegularOperationBusinessService extends OperationBusinessServices<RegularOperation> {

	@Override
	Set<UsualOperation> processOperationAndGenerateConcrete(RegularOperation operationToProcess) {
		// TODO Auto-generated method stub
		return null;
	}

}
