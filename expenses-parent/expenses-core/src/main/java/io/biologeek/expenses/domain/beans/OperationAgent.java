package io.biologeek.expenses.domain.beans;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.biologeek.expenses.domain.beans.operations.Operation;

/**
 * An {@link OperationAgent} is a protagonist within an {@link Operation}. <br>
 * <br>
 * In many cases an Operation references an {@link Emitter} and a
 * {@link Receiver}. <br>
 * <br>
 * Inheritance strategy makes that all operation agents are stored in a single
 * table, as one can be an emitter or a receiver or whatever in an operation and
 * have a different role in another
 * 
 * @author xcaron
 *
 */
@javax.persistence.Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class OperationAgent {

	@Id
	@GeneratedValue
	private Long id;
	/**
	 * The entity attached to this agent
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agentEntity")
	private Entity agentEntity;

	
	public Entity getAgentEntity() {
		return agentEntity;
	}

	public void setAgentEntity(Entity agentEntity) {
		this.agentEntity = agentEntity;
	}

}
