package io.biologeek.expenses.tests;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import io.biologeek.expenses.domain.beans.security.OwnPasswordEncoder;

public class Tests {

	public static void main(String[] args) {
		System.out.println(new OwnPasswordEncoder().encode("32653265"));
		
	}
}
