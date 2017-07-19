package io.biologeek.expenses.tests;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class Tests {

	public static void main(String[] args) {
		
		
		
		System.out.println(new StandardPasswordEncoder().encode("32653265"));
	}
}
