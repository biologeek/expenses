package io.biologeek.expenses.services.business;

import java.util.Set;

import org.springframework.stereotype.Service;

import io.biologeek.expenses.beans.MultiTenantOperation;
import io.biologeek.expenses.domain.beans.operations.UsualOperation;

/**
 * This service aims at implementing business rules for
 * {@link MultiTenantOperation}s processing (adding, updating and removing of
 * such operations).
 */
@Service
public class MultiTenantOperationBusinessService extends OperationBusinessServices<MultiTenantOperation> {

	@Override
	Set<UsualOperation> processOperationAndGenerateConcrete(MultiTenantOperation operationToProcess) {
		// TODO Auto-generated method stub
		return null;
	}

}
