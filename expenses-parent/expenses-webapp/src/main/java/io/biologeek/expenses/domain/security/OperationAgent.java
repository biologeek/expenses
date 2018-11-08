package io.biologeek.expenses.domain.security;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.biologeek.expenses.domain.beans.operations.Operation;

/**
 * An {@link OperationAgent} is a protagonist within an {@link Operation}. <br>
 * <br>
 * In many cases an Operation references an {@link OperationAgent} and a
 * {@link OperationAgent}. <br>
 * <br>
 * Inheritance strategy makes that all operation agents are stored in a single
 * table, as one can be an OperationAgent or a receiver or whatever in an
 * operation and have a different role in another
 * 
 * 
 *
 */
@javax.persistence.Entity
public class OperationAgent {

	@Id
	@GeneratedValue
	private Long id;
	/**
	 * The entity attached to this agent
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "agentEntity")
	private Entity agentEntity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Entity getAgentEntity() {
		return agentEntity;
	}

	public void setAgentEntity(Entity agentEntity) {
		this.agentEntity = agentEntity;
	}

	public OperationAgent agentEntity(Entity user) {
		this.setAgentEntity(user);
		return this;
	}

	public OperationAgent agentId(Long agentId) {
		this.id = agentId;
		return this;
	}

}
