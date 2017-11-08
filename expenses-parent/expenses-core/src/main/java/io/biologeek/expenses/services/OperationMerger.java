package io.biologeek.expenses.services;

import java.util.Date;

import javax.validation.ValidationException;

import io.biologeek.expenses.domain.beans.operations.Operation;

public class OperationMerger implements Merger<Operation> {

	/**
	 * Merges new operation to JPA entity. If operation is not modifiable, it is simply not modified.
	 * 
	 * @param newOperation updated operation to merge to stored operation
	 * @param storedOperation currently stored operation to be updated
	 * @return newly updated operation
	 */
	public Operation merge(Operation newOperation, Operation storedOperation) {
		if (storedOperation.getId() != null && storedOperation.getId() != 0 && !newOperation.getId().equals(storedOperation.getId()))
			throw new ValidationException("operation.update.not_same_id");
		
		// Do not modify unmodifiable operation
		if (!newOperation.isModifiable())
			return storedOperation;
		
		
		storedOperation.amount(newOperation.getAmount())//
				.currency(newOperation.getCurrency())//
				.emitter(newOperation.getEmitter())//
				.beneficiary(newOperation.getBeneficiary())//
				.version(storedOperation.getVersion() + 1)//
				.operationType(newOperation.getOperationType())
				.updateDate(new Date())//
				.description(newOperation.getDescription())//
				.category(newOperation.getCategory())//
				.effectiveDate(newOperation.getEffectiveDate())//
				.updateDate(newOperation.getUpdateDate())//
				.creationDate(newOperation.getCreationDate());
		return storedOperation;
	}

}
