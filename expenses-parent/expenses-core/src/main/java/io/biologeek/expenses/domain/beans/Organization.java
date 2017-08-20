package io.biologeek.expenses.domain.beans;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * A {@link Organization} can be a company, association, club, ...
 * 
 * @author xcaron
 */
@javax.persistence.Entity
public class Organization extends Entity {

	/**
	 * The sector that organzation operates in
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "org_sector")
	Category sector;
	/**
	 * Whether organization makes profit out of its activity
	 */
	private boolean isTrade;
	/**
	 * Physical entity to contact in the organization
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "main_contact")
	private Person mainContact;

	public Person getMainContact() {
		return mainContact;
	}

	public void setMainContact(Person mainContact) {
		this.mainContact = mainContact;
	}

	public boolean isTrade() {
		return isTrade;
	}

	public void setTrade(boolean isTrade) {
		this.isTrade = isTrade;
	}

	public Category getSector() {
		return sector;
	}

	public void setSector(Category sector) {
		this.sector = sector;
	}

}
