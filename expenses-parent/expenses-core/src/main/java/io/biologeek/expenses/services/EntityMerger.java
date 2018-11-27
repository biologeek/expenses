package io.biologeek.expenses.services;

import io.biologeek.expenses.domain.beans.Entity;
import io.biologeek.expenses.domain.beans.Organization;
import io.biologeek.expenses.domain.beans.Person;

public class EntityMerger implements Merger<Entity> {

	@Override
	public Entity merge(Entity stored, Entity toMerge) {
		if (stored.getId() == toMerge.getId()) {
			throw new IllegalArgumentException("entity.id.different");
		}
		if (!stored.getName().equals(toMerge.getName())) {
			stored.setName(toMerge.getName());
		}
		if (stored.getOwner().equals(toMerge.getOwner())) {
			stored.setOwner(toMerge.getOwner());
		}

		if (stored instanceof Organization) {
			mergeOrganization((Organization) stored, (Organization) toMerge);
		} else if (stored instanceof Person) {
			mergePerson((Person) stored, (Person) toMerge);
		}

		return stored;
	}

	private void mergePerson(Person stored, Person toMerge) {
		
		if (!stored.getAge().equals(toMerge.getAge())) {
			stored.setAge(toMerge.getAge());
		}
		if (!stored.getFirstName().equals(toMerge.getFirstName())) {
			stored.setFirstName(toMerge.getFirstName());
		}
		if (!stored.getLastName().equals(toMerge.getLastName())) {
			stored.setLastName(toMerge.getLastName());
		}
		if (!stored.getMailAddress().equals(toMerge.getMailAddress())) {
			stored.setMailAddress(toMerge.getMailAddress());
		}
		if (!stored.getPhoneNumber().equals(toMerge.getPhoneNumber())) {
			stored.setPhoneNumber(toMerge.getPhoneNumber());
		}

	}

	private void mergeOrganization(Organization stored, Organization toMerge) {
		

		if (!stored.getMainContact().equals(toMerge.getMainContact())) {
			stored.setMainContact(toMerge.getMainContact());
		}
		if (!stored.getSector().equals(toMerge.getSector())) {
			stored.setSector(toMerge.getSector());
		}
		
	}

}
