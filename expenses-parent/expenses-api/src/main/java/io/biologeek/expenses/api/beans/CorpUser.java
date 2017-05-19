package io.biologeek.expenses.api.beans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * Corporate entity
 * 
 * @author xcaron
 *
 */
@JsonAutoDetect
public class CorpUser extends Entity {
	private Category sector;
	private boolean isTrade;
	/**
	 * Physical entity to contact in the organization
	 */
	private User mainContact;
	
	public Category getSector() {
		return sector;
	}
	public void setSector(Category sector) {
		this.sector = sector;
	}
	public boolean isTrade() {
		return isTrade;
	}
	public void setTrade(boolean isTrade) {
		this.isTrade = isTrade;
	}
	public User getMainContact() {
		return mainContact;
	}
	public void setMainContact(User mainContact) {
		this.mainContact = mainContact;
	}
	
}
