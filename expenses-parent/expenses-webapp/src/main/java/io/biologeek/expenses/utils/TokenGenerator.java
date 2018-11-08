package io.biologeek.expenses.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class TokenGenerator implements RandomGenerator {

	@Override
	public String generate() {
		return UUID.randomUUID().toString();
	}

}
