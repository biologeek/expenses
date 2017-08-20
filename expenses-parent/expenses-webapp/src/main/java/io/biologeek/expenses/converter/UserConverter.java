package io.biologeek.expenses.converter;

import io.biologeek.expenses.api.beans.AuthenticationActionBean;
import io.biologeek.expenses.api.beans.CorpUser;
import io.biologeek.expenses.api.beans.User;
import io.biologeek.expenses.domain.beans.OperationAgent;
import io.biologeek.expenses.domain.beans.Organization;
import io.biologeek.expenses.domain.beans.Person;
import io.biologeek.expenses.domain.beans.RegisteredUser;
import io.biologeek.expenses.domain.beans.security.AuthenticationInformation;

public class UserConverter {

	/**
	 * Converts domain OperationAgent
	 * @param result
	 * @return
	 */
	public static CorpUser convertToCorp(Organization entity) {
		return new CorpUser()//
				.name(entity.getName())//
				.mainContact(UserConverter.convert(entity.getMainContact()))//
				.id(entity.getId())//
				.isTrade(entity.isTrade());
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
				.authToken(result.getAuthentication().getAuthToken());
	}
	
	
	public static User convert(Person result) {

		return new User()//
				.age(result.getAge())//
				.firstName(result.getFirstName())//
				.id(result.getId())//
				.lastName(result.getLastName())//
				.phoneNumber(result.getPhoneNumber())//
	}

	public static AuthenticationInformation toModel(AuthenticationActionBean bean) {
		AuthenticationInformation info = new AuthenticationInformation();

		info.setAuthToken(bean.getToken());
		info.setLogin(bean.getLogin());
		info.setPassword(bean.getPassword());

		return info;
	}

	public static AuthenticationActionBean toApi(AuthenticationInformation bean) {
		AuthenticationActionBean info = new AuthenticationActionBean();

		info.setToken(bean.getAuthToken());
		info.setLogin(bean.getLogin());
		info.setPassword(bean.getPassword());

		return info;
	}
}
