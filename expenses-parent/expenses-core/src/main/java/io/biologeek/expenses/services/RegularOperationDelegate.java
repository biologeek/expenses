package io.biologeek.expenses.services;

import java.util.Calendar;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.domain.beans.operations.RegularOperation;
import io.biologeek.expenses.domain.beans.operations.UsualOperation;
import io.biologeek.expenses.utils.DateUtils;

@Service
public class RegularOperationDelegate {

	/**
	 * Builds concrete operations for a RegularOperation <br>
	 * <br>
	 * This method generates a set of {@link UsualOperation} that reflect the
	 * periodicity of the regular operation. <br>
	 * <br>
	 * They are stored in the transient field
	 * {@link RegularOperation}.concreteOperations
	 * 
	 * @param regOp the {@link RegularOperation} to populate
	 * @return populated {@link RegularOperation}
	 */
	public RegularOperation buildUsualOperationTree(RegularOperation regOp) {

		SortedSet<UsualOperation> set = new TreeSet<>();
		Calendar currentDate = Calendar.getInstance();

		currentDate.setTime(regOp.getInterval().getFirstDate());
		while (currentDate.getTime().before(regOp.getInterval().getLastDate()) || currentDate.getTime().equals(regOp.getInterval().getLastDate())) {
			Operation current = (Operation) regOp.clone();
			current.setEffectiveDate(currentDate.getTime());
			set.add(new UsualOperation((Operation) current));
			currentDate.add(regOp.getInterval().getUnit(), regOp.getInterval().getInterval());
		}

		regOp.setConcreteOperations(set);
		return regOp;
	}

}
