package io.biologeek.expenses.domain.beans.balances;

import io.biologeek.expenses.domain.beans.Category;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CategoryBalance {
	private Date balanceDate;
	private Map<Category, BigDecimal> categories;
	
	public CategoryBalance() {
		super();
		categories = new HashMap<>();
	}

	public Date getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
	}

	public Map<Category, BigDecimal> getCategories() {
		return categories;
	}

	public void setCategories(Map<Category, BigDecimal> categories) {
		this.categories = categories;
	}

}
