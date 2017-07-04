package io.biologeek.expenses.converter;

import java.util.List;
import java.util.stream.Collectors;

import io.biologeek.expenses.api.beans.Category;

public class CategoryToApiConverter {

	public static Category convert(io.biologeek.expenses.domain.beans.Category category) {
		Category result = new Category();
		if (category != null) {
			result.setId(category.getId());
			result.setName(category.getName());
			result.setPicture(category.getCategoryPicture());
			result.setLevel(category.getLevel());
			result.setParent(CategoryToApiConverter.convert(category.getParent()));
			result.setNomenclature(category.getNomenclature());
			return result;
		}
		return null;
	}

	public static List<Category> convert(List<io.biologeek.expenses.domain.beans.Category> categories) {
		return categories.stream()//
				.map(t -> CategoryToApiConverter.convert(t))//
				.collect(Collectors.toList());
	}

}
