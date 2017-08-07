package io.biologeek.expenses.converter;

import io.biologeek.expenses.api.beans.AuthenticationActionBean;
import io.biologeek.expenses.api.beans.User;
import io.biologeek.expenses.domain.beans.OperationAgent;
import io.biologeek.expenses.domain.beans.RegisteredUser;
import io.biologeek.expenses.domain.beans.security.AuthenticationInformation;

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
				.authToken(result.getAuthentication().getAuthToken());
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
