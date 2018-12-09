package io.biologeek.expenses.converter;

import io.biologeek.expenses.api.beans.Category;

public class CategoryToModelConverter {

	public static io.biologeek.expenses.domain.beans.Category convert(Category category) {
		if (category == null)
			return null;
		io.biologeek.expenses.domain.beans.Category result = new io.biologeek.expenses.domain.beans.Category();

		result.setCategoryPicture(category.getPicture());
		result.setId(category.getId());
		result.setLevel(category.getLevel());
		result.setName(category.getName());
		result.setNomenclature(category.getNomenclature());
		result.setCategoryPictureId(category.getPictureId());
		result.setParent(CategoryToModelConverter.convert(category.getParent()));
		return result;
	}

}
