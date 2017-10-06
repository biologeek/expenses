package io.biologeek.expenses.services;

import java.util.Date;

import javax.validation.ValidationException;

import io.biologeek.expenses.domain.beans.operations.Operation;

public class OperationMerger implements Merger<Operation> {

	public Operation merge(Operation newOperation, Operation storedOperation) {
		if (storedOperation.getId() != null && storedOperation.getId() != 0 && !newOperation.getId().equals(storedOperation.getId()))
			throw new ValidationException("opertion.update.not_same_id");
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
