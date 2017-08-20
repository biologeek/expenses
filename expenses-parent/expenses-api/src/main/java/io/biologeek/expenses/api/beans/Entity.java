package io.biologeek.expenses.api.beans;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public abstract class Entity {
	protected Long id;
	protected String name;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
