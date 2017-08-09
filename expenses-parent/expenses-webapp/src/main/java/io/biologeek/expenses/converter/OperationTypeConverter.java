package io.biologeek.expenses.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.biologeek.expenses.domain.beans.operations.OperationType;

public class OperationTypeConverter {

	public static List<io.biologeek.expenses.api.beans.OperationType> convert(List<OperationType> asList) {
		return asList.stream()//
				.map(OperationTypeConverter::convert)//
				.collect(Collectors.toList());
	}
	
	
	public static io.biologeek.expenses.api.beans.OperationType convert(OperationType type) {
		return new io.biologeek.expenses.api.beans.OperationType()//
				.name(type.name())//
				.sign(type.getSign())//
				;
	}

}
