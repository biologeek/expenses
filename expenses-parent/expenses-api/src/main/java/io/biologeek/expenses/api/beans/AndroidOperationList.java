package io.biologeek.expenses.api.beans;

import java.math.BigDecimal;
import java.util.ArrayList;

import io.biologeek.expenses.api.beans.AndroidOperationList.OperationListMember;

public class AndroidOperationList extends ArrayList<OperationListMember> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8796436382817931409L;

	public class OperationListMember {

		private long id;
		private OperationType operationType;
		private String description;
		private String currency;
		private BigDecimal amount;
		private int categoryPicture;

		public int getCategoryPicture() {
			return categoryPicture;
		}

		public void setCategoryPicture(int categoryPicture) {
			this.categoryPicture = categoryPicture;
		}

		public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

		public OperationType getOperationType() {
			return operationType;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public void setOperationType(OperationType operationType) {
			this.operationType = operationType;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}
	}
}
