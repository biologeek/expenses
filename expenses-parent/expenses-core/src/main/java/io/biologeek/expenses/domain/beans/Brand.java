package io.biologeek.expenses.domain.beans;

public class Brand extends Entity {

	
	Category brandCategory;

	public Category getBrandCategory() {
		return brandCategory;
	}

	public void setBrandCategory(Category brandCategory) {
		this.brandCategory = brandCategory;
	}
	
}
