package io.biologeek.expenses.services;

import java.util.Base64;
import java.util.Base64.Decoder;

import org.springframework.stereotype.Service;

import io.biologeek.expenses.domain.beans.security.AuthenticationInformation;
import io.biologeek.expenses.utils.Constants;

@Service
public class AuthenticationService {

	public AuthenticationInformation decryptToken(String encodedString) {
		 
		Decoder base64 = Base64.getDecoder();
		byte[] decodedByteString = base64.decode(encodedString);
		String[] decodedString = new String(decodedByteString).split(Constants.TOKEN_SEPARATOR);
		
		return null;
	}
	
	

}
