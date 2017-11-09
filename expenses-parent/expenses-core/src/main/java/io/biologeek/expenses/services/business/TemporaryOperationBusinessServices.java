package io.biologeek.expenses.services.business;

import java.util.Set;

import org.springframework.stereotype.Service;

import io.biologeek.expenses.domain.beans.operations.TemporaryOperation;
import io.biologeek.expenses.domain.beans.operations.UsualOperation;

/**
 * This service aims at implementing business rules for {@link TemporaryOperation}s processing (adding, updating and removing of such operations). 
 */
@Service
public class TemporaryOperationBusinessServices extends OperationBusinessServices<TemporaryOperation> {

	@Override
	public Set<UsualOperation> processOperationAndGenerateConcrete(TemporaryOperation operationToProcess) {
		// TODO Auto-generated method stub
		return null;
	}

}
