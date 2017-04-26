package io.biologeek.expenses.domain.beans;

import javax.persistence.Entity;

import io.biologeek.expenses.domain.beans.operations.Operation;

/**
 * A {@link Receiver} is, within an {@link Operation} the agent that receives
 * the fruit of the operation. Money, object, ...
 * 
 * @author xcaron
 *
 */
@Entity
public class Receiver extends OperationAgent {

}
