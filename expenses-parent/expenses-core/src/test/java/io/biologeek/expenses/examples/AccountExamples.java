package io.biologeek.expenses.examples;

import java.util.Date;

import io.biologeek.expenses.domain.beans.Account;

public class AccountExamples {

	
	public static Account aPhysicalPersonAccount() {
		return new Account()//
				.id(1L)//
				.creationDate(new Date())//
				.name("My Account")//
				.number(1L)//
				.owner(UserExamples.aPhysicalUser());
	}
}
