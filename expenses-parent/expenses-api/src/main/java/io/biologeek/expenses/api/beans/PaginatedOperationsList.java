package io.biologeek.expenses.api.beans;

import java.util.List;


/**
 * A list of operations with pagination data
 */
public class PaginatedOperationsList {

	List<Operation> operations;
	int totalOperations;
	int totalPages;
	int operationPerPage;
	int currentPage;

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

	public PaginatedOperationsList operations(List<Operation> operations2) {
		this.operations = operations2;
		return this;
	}

	public PaginatedOperationsList operationsPerPage(int operationPerPage2) {
		this.operationPerPage = operationPerPage2;
		return this;
	}

	public PaginatedOperationsList totalOperations(int totalOperations2) {
		this.totalOperations = totalOperations2;
		return this;
	}

	public PaginatedOperationsList totalPages(int totalPages2) {
		this.totalPages = totalPages2;
		return this;
	}

	public PaginatedOperationsList currentPage(int currentPage2) {
		this.currentPage = currentPage2;
		return this;
	}

}
