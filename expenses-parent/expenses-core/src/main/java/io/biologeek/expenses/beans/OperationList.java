package io.biologeek.expenses.beans;

import java.util.ArrayList;
import java.util.List;

import io.biologeek.expenses.domain.beans.operations.Operation;

/**
 * A list of operations with pagination data
 */
public class OperationList {

	List<Operation> operations;
	int totalOperations;
	int totalPages;
	int operationPerPage;
	int currentPage;

	public OperationList() {
		operations = new ArrayList<>();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public int getTotalOperations() {
		return totalOperations;
	}

	public void setTotalOperations(int totalOperations) {
		this.totalOperations = totalOperations;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getOperationPerPage() {
		return operationPerPage;
	}

	public void setOperationPerPage(int operationPerPage) {
		this.operationPerPage = operationPerPage;
	}

}
