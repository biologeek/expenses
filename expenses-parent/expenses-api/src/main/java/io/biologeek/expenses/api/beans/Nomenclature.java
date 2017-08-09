package io.biologeek.expenses.api.beans;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Nomenclature {
	
	List<Category> categories;

	public Nomenclature() {
		categories = new ArrayList<Category>();
	}
	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public void add(Category cat) {
		this.categories.add(cat);
	}
}
