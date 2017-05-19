package io.biologeek.expenses.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import io.biologeek.expenses.domain.beans.Account;
import io.biologeek.expenses.domain.beans.operations.Operation;
import io.biologeek.expenses.repositories.OperationsRepository;


@Service
@Transactional
public class OperationService {
	
	@Autowired
	OperationsRepository operationsRepository;

	public List<Operation> getLastOperationsForAccount(Account account, int limit) {
		return operationsRepository.getOperationsForAccountWithLimit(account.getId(), new PageRequest(0, limit));
	}

	/**
	 * Returns an operation based on its ID
	 * 
	 * @param id
	 * @return
	 */
	public Operation getOperationByid(Long id) {
		return operationsRepository.getOne(id);
	}
}
