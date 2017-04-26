package io.biologeek.expenses.domain.beans;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

/**
 * An Entity represents every person, company, association, ... that can do
 * operations<br>
 * <br>
 * In database, every type of entity has its own table
 * 
 * @author xcaron
 *
 */
@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Entity {
	@Id@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	
	@OneToMany(mappedBy="agentEntity")
	private List<OperationAgent> agents;
	

	public List<OperationAgent> getAgents() {
		return agents;
	}

	public void setAgents(List<OperationAgent> agents) {
		this.agents = agents;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
