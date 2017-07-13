package io.biologeek.expenses.converter;

import io.biologeek.expenses.api.beans.User;
import io.biologeek.expenses.domain.beans.OperationAgent;
import io.biologeek.expenses.domain.beans.RegisteredUser;
import io.biologeek.expenses.domain.beans.OperationAgent;

public class UserConverter {

	public static User convert(OperationAgent beneficiary) {
		// TODO Auto-generated method stub
		return null;
	}

	public static User convert(RegisteredUser result) {
		
		return new User()//
				.accounts(AccountToApiConverter.convert(result.getAccounts()))//
				.age(result.getAge())//
				.firstName(result.getFirstName())//
				.id(result.getId())//
				.lastName(result.getLastName())//
				.mailAddress(result.getEmail())//
				.phoneNumber(result.getPhoneNumber())//
				.authToken(result.getAuthToken());
	}

}
