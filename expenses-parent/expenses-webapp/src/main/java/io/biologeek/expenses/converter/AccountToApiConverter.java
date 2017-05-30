package io.biologeek.expenses.converter;

import java.util.List;
import java.util.stream.Collectors;

import io.biologeek.expenses.api.beans.Account;

public class AccountToApiConverter {

	public static Account convert(io.biologeek.expenses.domain.beans.Account toConvert) {
		Account res = new Account();
		res.setCreationDate(toConvert.getCreationDate());
		res.setId(toConvert.getId());
		res.setUpdateDate(toConvert.getUpdateDate());
		res.setName(toConvert.getName());
		res.setOwner(toConvert.getOwner());
		res.setOwner(toConvert.getOwner());
		res.setNumber(toConvert.getNumber());
		return res;
	}

	public static List<Account> convert(List<io.biologeek.expenses.domain.beans.Account> accounts) {
		return accounts.stream()//
				.map(AccountToApiConverter::convert)//
				.collect(Collectors.toList());
	}

}
