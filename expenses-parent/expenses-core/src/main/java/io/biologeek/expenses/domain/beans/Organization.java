package io.biologeek.expenses.domain.beans;

/**
 * A {@link Organization} can be a company, assciation, club, ...
 * 
 * @author xcaron
 */
public class Organization extends Entity {
	
	/**
	 * The sector that organzation operates in
	 */
	Category sector;
	/**
	 * Whether organization makes profit out of its activity
	 */
	private boolean isTrade;
	/**
	 * Physical entity to contact in the organization
	 */
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
