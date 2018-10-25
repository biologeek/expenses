package io.biologeek.expenses.api.beans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Corporate entity
 * 
 * 
 *
 */
@JsonAutoDetect
@JsonTypeName("corp")
public class CorpUser extends Entity {
	private Category sector;
	private boolean isTrade;
	/**
	 * Physical entity to contact in the organization
	 */
	private User mainContact;
	public CorpUser() {
		this.type="corp";
	}
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

	
	public CorpUser sector(Category category){
		this.sector = category;
		return this;
	}
	
	public CorpUser id(Long id){
		this.id = id;
		return this;
	}
	
	public CorpUser name(String name){
		this.name = name;
		return this;
	}
	
	public CorpUser mainContact(User name){
		this.mainContact = name;
		return this;
	}
	
	public CorpUser isTrade(boolean isTrade){
		this.isTrade = isTrade;
		return this;
	}
	
}
