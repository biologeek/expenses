package io.biologeek.expenses.domain.beans.operations;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("U")
public class UsualOperation extends Operation{

	public UsualOperation(Operation operation) {
		super();
		beneficiary = operation.getBeneficiary();
		emitter = operation.getEmitter();
		account = operation.getAccount();
		amount = operation.getAmount();
		category = operation.getCategory();
		operationType = operation.getOperationType();
		creationDate = operation.getCreationDate();
		updateDate = operation.getUpdateDate();
		version = operation.getVersion();
		effectiveDate = operation.getEffectiveDate();
		description = operation.getDescription();
		currency = operation.getCurrency();
	}
	
	public UsualOperation() {
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * UsualOperations are classified by their effective date. 
	 * An operation that is after this should be after as classification is chronological
	 */
	public int compareTo(Operation o) {
		if (o.getEffectiveDate().equals(this.getEffectiveDate()))
			return 0;
		else if (o.getEffectiveDate().after(this.getEffectiveDate()))
			return -1;
		else 
			return 1;
	}

}
