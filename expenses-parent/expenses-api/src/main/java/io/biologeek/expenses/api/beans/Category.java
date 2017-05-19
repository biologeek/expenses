package io.biologeek.expenses.api.beans;

import java.util.List;
import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Category {
	
	private Long id;

	private String name;
	private List<Category> subCategories;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Category> getSubCategories() {
		return subCategories;
	}
	public void setSubCategories(List<Category> subCategories) {
		this.subCategories = subCategories;
	}
	
	public Category getSubCategoryByName(final String name){
		return getSubCategories().stream().filter(new Predicate<Category>() {

			public boolean test(Category t) {
				return t.getName().equals(name);
			}
		}).findFirst().get();
	}


}
