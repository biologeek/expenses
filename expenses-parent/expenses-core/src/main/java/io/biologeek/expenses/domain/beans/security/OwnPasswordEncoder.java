package io.biologeek.expenses.domain.beans.security;

import java.util.Base64;

import org.springframework.security.crypto.password.PasswordEncoder;

public class OwnPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		
		byte[] src = convertToByte(rawPassword);
		return Base64.getEncoder().encodeToString(src);
	}

	private byte[] convertToByte(CharSequence rawPassword) {
		byte[] src = new byte[rawPassword.length()];
		
		for (int i = 0; i < rawPassword.length(); i++) {
			src[i] = (byte) rawPassword.charAt(i);
		}
		return src;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		byte[] rawPasswordByte = convertToByte(rawPassword);
		return Base64.getEncoder().encodeToString(rawPasswordByte).equals(encodedPassword);
	}

}
