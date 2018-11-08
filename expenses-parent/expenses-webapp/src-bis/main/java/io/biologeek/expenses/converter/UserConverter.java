package io.biologeek.expenses.converter;

import io.biologeek.expenses.api.beans.AuthenticationActionBean;
import io.biologeek.expenses.api.beans.CorpUser;
import io.biologeek.expenses.api.beans.Entity;
import io.biologeek.expenses.api.beans.User;
import io.biologeek.expenses.domain.beans.OperationAgent;
import io.biologeek.expenses.domain.beans.Organization;
import io.biologeek.expenses.domain.beans.Person;
import io.biologeek.expenses.domain.beans.RegisteredUser;
import io.biologeek.expenses.domain.beans.security.AuthenticationInformation;

public class UserConverter {

	/**
	 * Converts domain OperationAgent
	 * 
	 * @param long1
	 * 
	 * @param result
	 * @return
	 */
	public static CorpUser convertToCorp(Organization entity, Long long1) {
		return new CorpUser()//
				.name(entity.getName())//
				.mainContact(UserConverter.convert(entity.getMainContact(), null))//
				.id(entity.getId())//
				.isTrade(entity.isTrade());
	}

	public static User convert(RegisteredUser result) {
		return new User()//
				.accounts(AccountToApiConverter.convert(result.getAccounts()))//
				.age(result.getAge())//
				.firstName(result.getFirstName())//
				.username(result.getUsername()).id(result.getId())//
				.lastName(result.getLastName())//
				.mailAddress(result.getEmail())//
				.phoneNumber(result.getPhoneNumber())//
				.authToken(result.getAuthentication().getAuthToken());
	}

	/**
	 * API => Model
	 * 
	 * @param user
	 * @return
	 */
	public static OperationAgent toOperationAgent(Entity ent) {
		if (ent == null)
			return null;
		
		OperationAgent agent = new OperationAgent()//
				.agentId(ent.getAgentId());
		
		if (ent instanceof User)
			agent.setAgentEntity(toPerson((User) ent));
		else if (ent instanceof CorpUser)
			agent.setAgentEntity(toOrganization((CorpUser) ent));
		return agent;
	}

	private static Organization toOrganization(CorpUser ent) {
		Organization or = new Organization();
		or.setId(ent.getId());
		or.setMainContact(toPerson(ent.getMainContact()));
		return null;
	}

	private static Person toPerson(User ent) {
		Person person = new Person();
		person.setId(ent.getId());
		person.setAge(ent.getAge());
		person.setFirstName(ent.getFirstName());
		person.setLastName(ent.getLastName());
		person.setMailAddress(ent.getMailAddress());
		person.setPhoneNumber(ent.getPhoneNumber());
		return person;
	}

	public static User convert(Person result, Long agentId) {
		if (result == null)
			return null;
		return new User()//
				.age(result.getAge())//
				.firstName(result.getFirstName())//
				.id(result.getId())//
				.agentId(agentId).lastName(result.getLastName())//
				.phoneNumber(result.getPhoneNumber());//
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

	public static <T extends Entity> T convert(OperationAgent agent) {
		if (agent != null) {
			if (agent.getAgentEntity() instanceof Person)
				return (T) convert((Person) agent.getAgentEntity(), agent.getId());
			else if (agent.getAgentEntity() instanceof Organization)
				return (T) convertToCorp((Organization) agent.getAgentEntity(), agent.getId());
		}
		return null;
	}

	public static RegisteredUser convert(User user) {
		
		return new RegisteredUser()//
				.login(user.getUsername())//
				.password(user.getPassword())
				.age(user.getAge())//
				.phoneNumber(user.getPhoneNumber())//
				.email(user.getMailAddress())//
				;
	}
}
