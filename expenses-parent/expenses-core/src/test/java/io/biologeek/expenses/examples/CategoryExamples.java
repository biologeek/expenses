package io.biologeek.expenses.examples;

import io.biologeek.expenses.domain.beans.Category;

public class CategoryExamples {

	public static Category aHomeCategory() {
		Category cat = new Category();
		cat.setId(1L);
		cat.setLevel(0);
		cat.setName("Home");
		cat.setParent(null);
		return cat;
	}

}
