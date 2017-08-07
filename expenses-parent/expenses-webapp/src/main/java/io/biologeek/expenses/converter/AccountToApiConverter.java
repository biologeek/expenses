package io.biologeek.expenses.converter;

import java.util.List;
import java.util.stream.Collectors;

import io.biologeek.expenses.api.beans.Account;
import io.biologeek.expenses.api.beans.User;

public class AccountToApiConverter {

	public static Account convert(io.biologeek.expenses.domain.beans.Account toConvert) {
		Account res = new Account();
		res.setCreationDate(toConvert.getCreationDate());
		res.setId(toConvert.getId());
		res.setUpdateDate(toConvert.getUpdateDate());
		res.setName(toConvert.getName());
		res.setOwner(toConvert.getOwner().getId());
		res.setNumber(toConvert.getNumber());
		return res;
	}

	private static User convert(io.biologeek.expenses.domain.beans.RegisteredUser owner) {
		return new User()//
				.firstName(owner.getFirstName())//
				.lastName(owner.getLastName())//
				.age(owner.getAge())//
				.mailAddress(owner.getEmail())//
				.phoneNumber(owner.getPhoneNumber())//
				.accounts(owner.getAccounts().stream()//
						.map(t -> AccountToApiConverter.convert(t))//
						.collect(Collectors.toList()));
	}

	public static List<Account> convert(List<io.biologeek.expenses.domain.beans.Account> accounts) {
		return accounts.stream()//
				.map(AccountToApiConverter::convert)//
				.collect(Collectors.toList());
	}


}
