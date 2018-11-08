package io.biologeek.expenses.converter;

import java.util.List;
import java.util.stream.Collectors;

import io.biologeek.expenses.api.beans.Category;
import io.biologeek.expenses.api.beans.Nomenclature;

public class CategoryToApiConverter {

	public static Nomenclature convert(io.biologeek.expenses.domain.beans.Category category) {
		Nomenclature result = new Nomenclature();
		while(category != null) {
			Category cat = new Category();
			cat.setId(category.getId());
			cat.setName(category.getName());
			cat.setPicture(category.getCategoryPicture());
			cat.setLevel(category.getLevel());
			cat.setNomenclature(category.getNomenclature());
			result.add(cat);
			category = category.getParent();
		}
		return result;
	}

	public static Category convertToCategory(io.biologeek.expenses.domain.beans.Category category) {
		if (category != null && category.getId() != null) {
			Category result = new Category();
			result.setId(category.getId());
			result.setName(category.getName());
			result.setPicture(category.getCategoryPicture());
			result.setLevel(category.getLevel());
			result.setParent(CategoryToApiConverter.convertToCategory(category.getParent()));
			result.setNomenclature(category.getNomenclature());
			result.setPictureId(category.getCategoryPictureId());
			return result;
		}
		return null;
	}
	

	public static List<Category> convert(List<io.biologeek.expenses.domain.beans.Category> categories) {
		return categories.stream()//
				.map(t -> CategoryToApiConverter.convertToCategory(t))//
				.collect(Collectors.toList());
	}

}
